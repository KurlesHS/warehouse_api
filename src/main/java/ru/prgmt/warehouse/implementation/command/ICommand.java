package ru.prgmt.warehouse.implementation.command;

import ru.prgmt.warehouse.application.Paging;
import ru.prgmt.warehouse.application.result.CommandResult;

import java.util.Map;

public interface ICommand {
    String name();
    String description();
    CommandResult executeCommand(Map<String, String> parameters, Paging paging);

}
