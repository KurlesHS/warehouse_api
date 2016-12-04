package ru.prgmt.warehouse.implementation.command;

import ru.prgmt.warehouse.application.Page;
import ru.prgmt.warehouse.application.Paging;
import ru.prgmt.warehouse.application.result.CommandResult;
import ru.prgmt.warehouse.application.result.ErrorResult;
import ru.prgmt.warehouse.application.result.PagedResult;
import ru.prgmt.warehouse.implementation.*;
import ru.prgmt.warehouse.implementation.record.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddCommand implements ICommand {
    private final IRepository repository;
    private Map<String, IRecordCreator> recordCreators = new HashMap<>();


    public AddCommand(IRepository repository) {
        this.repository = repository;
        addCreator(new MonitorRecordCreator());
        addCreator(new ScannerRecordCreator());
        addCreator(new PrinterRecordCreator());
    }

    @Override
    public String name() {
        return "add";
    }

    @Override
    public String description() {
        return "добавление инвертаря";
    }

    @Override
    public CommandResult executeCommand(Map<String, String> parameters, Paging paging) {
        String type = parameters.get("type");
        if (type == null) {
            return new ErrorResult("Не указан тип инвертаря");
        }
        IRecordCreator creator = recordCreators.get(type);
        if (creator != null) {
            Record record;
            try {
                record = creator.create(parameters);
            } catch (InventoryParamsException e) {
                return new ErrorResult(e.getMessage());
            }
            /*
            if (repository.hasRecord(record.getSku())) {
                System.out.println("Запись с таким SKU уже существует, перезаписываю");
            }
            */
            repository.addOrUpdateRecord(record);
            /*
            System.out.println(String.format("Добавлен инвентарь: %s", record.toString()));
            */
            /* успешное выполнение команды - пустой PagedResult */
            return new PagedResult(new Page<>(new ArrayList<>(), null, 0));
        } else {
            return new ErrorResult(String.format("add: неизвестный тип инвертаря: '%s'", type));
        }
    }

    private void addCreator(IRecordCreator creator) {
        recordCreators.put(creator.name(), creator);
    }
}
