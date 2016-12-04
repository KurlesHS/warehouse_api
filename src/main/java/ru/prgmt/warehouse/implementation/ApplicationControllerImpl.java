package ru.prgmt.warehouse.implementation;

import ru.prgmt.warehouse.application.ApplicationController;
import ru.prgmt.warehouse.application.CommandNotFoundException;
import ru.prgmt.warehouse.application.Paging;
import ru.prgmt.warehouse.application.result.CommandResult;
import ru.prgmt.warehouse.implementation.command.AddCommand;
import ru.prgmt.warehouse.implementation.command.DeleteCommand;
import ru.prgmt.warehouse.implementation.command.ICommand;
import ru.prgmt.warehouse.implementation.command.ListCommand;

import java.util.HashMap;
import java.util.Map;

public class ApplicationControllerImpl implements ApplicationController {
    private HashMap<String, ICommand> commands = new HashMap<>();

    public ApplicationControllerImpl(IRepository repository) {
        addCommand(new AddCommand(repository));
        addCommand(new DeleteCommand(repository));
        addCommand(new ListCommand(repository));
    }

    @Override
    public Map<String, String> getAvailableCommands() {
        Map<String, String> result = new HashMap<>();
        for (ICommand cmd: commands.values()) {
            result.put(cmd.name(), cmd.description());
        }
        return result;
    }

    @Override
    public CommandResult executeCommand(String name, Map<String, String> parameters, Paging paging) throws CommandNotFoundException {

        ICommand cmd = commands.get(name);
        if (cmd == null) {
            throw new CommandNotFoundException(name);
        }
        return cmd.executeCommand(parameters, paging);
    }

    private void addCommand(ICommand command) {
        commands.put(command.name(), command);
    }

}
