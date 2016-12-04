package ru.prgmt.warehouse.console;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class ParametersParser {

    static class InventoryParamsException extends Exception {
        InventoryParamsException(String message) {
            super(message);
        }

    }

    static Map<String, String> parseArguments(String args[]) throws InventoryParamsException {
        HashMap<String, String> params = new HashMap<>();
        String option = "";
        for (int i = 1; i < args.length; ++i) {
            if (i % 2 == 1) {
                /* нечётные - названия параметра */
                option = args[i];
                if (option.length() < 3 || !option.startsWith("--")) {
                    /* не может быть меньше 3х символов и должна начинаться с '--' */
                    throw new InventoryParamsException(
                            String.format("Неожиданное имя параметра: '%s'", option));
                }
                option = option.substring(2);
            } else {
                /* чётные - значения */
                params.put(option, args[i]);
            }
        }
        return params;
    }
}
