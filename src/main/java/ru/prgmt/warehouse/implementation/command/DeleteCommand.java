package ru.prgmt.warehouse.implementation.command;

import ru.prgmt.warehouse.application.Page;
import ru.prgmt.warehouse.application.Paging;
import ru.prgmt.warehouse.application.result.CommandResult;
import ru.prgmt.warehouse.application.result.ErrorResult;
import ru.prgmt.warehouse.application.result.PagedResult;
import ru.prgmt.warehouse.application.result.PromptResult;
import ru.prgmt.warehouse.implementation.IRepository;
import ru.prgmt.warehouse.implementation.InventoryParamsException;
import ru.prgmt.warehouse.implementation.MissingParamException;
import ru.prgmt.warehouse.implementation.ParametersParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class DeleteCommand implements ICommand{
    private static final String CLEAR_PARAM = "clear";

    private final IRepository repository;
    public DeleteCommand(IRepository repository) {
        this.repository = repository;
    }
    @Override
    public String name() {
        return "delete";
    }

    @Override
    public String description() {
        return "удаление инвертаря";
    }

    @Override
    public CommandResult executeCommand(Map<String, String> parameters, Paging paging) {
        String clearParam = parameters.get(CLEAR_PARAM);
        if (clearParam != null) {
            /* передан параметр clean */
            List<String> positiveAnswers = Arrays.asList("y", "yes", "да", "д");
            if (positiveAnswers.contains(clearParam)) {
                repository.removeAllRecords();
            }
        } else {
            try {
                int sku = ParametersParser.getSku(parameters);
                repository.deleteRecord(sku);
            } catch (InventoryParamsException e) {
                return new ErrorResult(e.getMessage());
            } catch (MissingParamException e) {
                return new PromptResult(CLEAR_PARAM, "Удалить все записи?", "y/N");
            }
        }
        /* успешное выполнение команды - пустой PagedResult */
        return new PagedResult(new Page<>(new ArrayList<>(), null, 0));
    }
}
