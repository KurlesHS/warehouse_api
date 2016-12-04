package ru.prgmt.warehouse.implementation;

public class MissingParamException extends Exception {

    private String paramName;

    MissingParamException(String paramName) {
        super(String.format("отсутствует обязательный параметр '--%s'", paramName));
        this.paramName = paramName;
    }

    String getParamName() {
        return paramName;
    }
}
