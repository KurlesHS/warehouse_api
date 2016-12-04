package ru.prgmt.warehouse.implementation;

import java.util.Collection;

public class MissingParamsException extends Exception {
    private Collection<String> paramsName;

     MissingParamsException(Collection<String> paramsName) {
        super(String.format("Не указаны обязательные параметры: %s", StringHelpers.join(", ", paramsName)));
        this.paramsName = paramsName;
    }

    public Collection<String> getParamsNames() {
        return paramsName;
    }
}
