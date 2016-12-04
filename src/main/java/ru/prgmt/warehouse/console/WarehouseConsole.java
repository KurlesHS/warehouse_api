package ru.prgmt.warehouse.console;


import ru.prgmt.warehouse.implementation.ApplicationControllerImpl;
import ru.prgmt.warehouse.implementation.FileRepository;

public class WarehouseConsole {
    public static void main(String[] args) {
        FileRepository repository = new FileRepository();
        ApplicationControllerImpl applicationController = new ApplicationControllerImpl(repository);
        ConsoleHandler handler = new ConsoleHandler(applicationController);
        handler.process(args);
        /* запись на диск только перед выходом из приложения */
        repository.updateDatabase();
    }
}
