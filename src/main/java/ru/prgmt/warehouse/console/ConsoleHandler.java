package ru.prgmt.warehouse.console;


import ru.prgmt.warehouse.application.ApplicationController;
import ru.prgmt.warehouse.application.CommandNotFoundException;
import ru.prgmt.warehouse.application.Page;
import ru.prgmt.warehouse.application.Paging;
import ru.prgmt.warehouse.application.result.CommandResult;
import ru.prgmt.warehouse.application.result.ErrorResult;
import ru.prgmt.warehouse.application.result.PagedResult;
import ru.prgmt.warehouse.application.result.PromptResult;
import ru.prgmt.warehouse.implementation.displayResult.DisplayResult;

import java.util.Map;
import java.util.Scanner;

class ConsoleHandler {
    private final ApplicationController controller;

    ConsoleHandler(ApplicationController applicationController) {
        controller = applicationController;
    }

    void process(String[] args) {
        if (args.length < 1) {
            // не указана команда
            System.out.println("Слишком мало параметров");
            return;
        }
        String commandName = args[0];
        Map<String, String> parameters;
        try {
            parameters = ParametersParser.parseArguments(args);
        } catch (ParametersParser.InventoryParamsException e) {
            System.out.println(e.getMessage());
            return;
        }
        executeCommand(commandName, parameters, getPaging(parameters));
    }

    private Paging getPaging(Map<String, String> parameters) {

        Paging p = null;
        try {
            int pageNum = Integer.parseInt(parameters.get("page_num"));
            int pageSize = Integer.parseInt(parameters.get("page_size"));
            String field = parameters.get("sort_prop");
            int sortAsc = Integer.parseInt(parameters.get("sort_asc"));
            if (field != null) {
                // System.out.println("paging " + pageNum + " " + pageSize + " " + field + " " + sortAsc);
                p = new Paging(pageNum, pageSize, field, sortAsc != 0);
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return p;
    }

    private void executeCommand(String name, Map<String, String> parameters, Paging paging) {
        try {
            CommandResult result = controller.executeCommand(name, parameters, paging);
            if (result instanceof ErrorResult) {
                handleErrorResult((ErrorResult) result);
            } else if (result instanceof PromptResult) {
                handlePromptResult((PromptResult) result, name, parameters, paging);
            } else if (result instanceof PagedResult) {
                handlePagedResult((PagedResult) result);
            }
        } catch (CommandNotFoundException e) {
            System.out.println(String.format("Неизвестная команда '%s'", name));
        }
    }

    private void handleErrorResult(ErrorResult result) {
        System.out.println(result.getMessage());
    }

    private void handlePagedResult(PagedResult result) {
        Page<?> page = result.getResult();
        if (page.getContent().size() > 0) {
            System.out.println("Список оборудования:");
            for (Object item : page.getContent()) {
                if (item instanceof DisplayResult) {
                    DisplayResult record = (DisplayResult) item;
                    System.out.println(String.format("   %d - %s", record.getSku(), record.toString()));
                }
            }
        }
        // так как пустой PagedResult может означать как пустой список оборудования, так и успешное выполнение
        // команды, оставим данный кейс без вывода.
    }

    private void handlePromptResult(PromptResult result, String name, Map<String, String> parameters, Paging paging) {
        // выводим сообщение запроса и подсказку
        System.out.print(String.format("%s (%s)", result.getPrompt(), result.getValueHint()));
        Scanner sc = new Scanner(System.in);
        // получаем значение от пользователя
        String line = sc.nextLine().toLowerCase();
        // заносим его в параметры
        parameters.put(result.getParameterKey(), line);
        // и повторно выполняем запрос
        executeCommand(name, parameters, paging);
    }
}
