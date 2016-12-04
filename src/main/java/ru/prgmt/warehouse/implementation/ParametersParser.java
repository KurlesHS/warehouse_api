package ru.prgmt.warehouse.implementation;

import ru.prgmt.warehouse.implementation.record.Record;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class ParametersParser {
    private final static String DATE_PARAM = "date";
    private final static String SKU_PARAM = "sku";
    private final static String NAME_PARAM = "name";
    private final static String QUANTITY_PARAM = "quantity";

    public static int getSku(Map<String, String> params) throws InventoryParamsException, MissingParamException {
        String skuStr = params.get(SKU_PARAM);
        if (skuStr == null) {
            throw new MissingParamException(SKU_PARAM);
        }
        try {
            int sku = Integer.parseInt(skuStr);
            if (sku >= 0) {
                return sku;
            }
            throw new InventoryParamsException(String.format("параметр %s содержит отрицательное значение", SKU_PARAM));
        } catch (NumberFormatException e) {
            throw new InventoryParamsException(String.format("параметр %s содержит не числовое значение", SKU_PARAM));
        }
    }

    private static Date getDate(Map<String, String> params) throws InventoryParamsException, MissingParamException {
        String skuDate = params.get(DATE_PARAM);
        if (skuDate == null) {
            throw new MissingParamException(DATE_PARAM);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return dateFormat.parse(skuDate);
        } catch (ParseException e) {
            throw new InventoryParamsException(String.format("не верный параметр %s ('%s'). Должен быть в формате {дд.мм.гггг}", DATE_PARAM, skuDate));
        }
    }

    public static void fillCommonParams(Record record, Map<String, String> params) throws InventoryParamsException, MissingParamsException {
        ArrayList<String> missingParams = new ArrayList<>();
        try {
            record.setSku(getSku(params));
        } catch (MissingParamException e) {
            missingParams.add(e.getParamName());
        }
        try {
            record.setDate(getDate(params));
        } catch (MissingParamException e) {
            missingParams.add(e.getParamName());
        }
        String name = params.get(NAME_PARAM);
        if (name == null) {
            missingParams.add(NAME_PARAM);
        }
        record.setName(name);
        String quantityStr = params.get(QUANTITY_PARAM);
        if (quantityStr != null) {
            try {
                int quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    throw new InventoryParamsException(String.format("параметр %s содержит нулевое или отрицательное значение", QUANTITY_PARAM));
                }
                record.setQuantity(quantity);
            } catch (NumberFormatException e) {
                throw new InventoryParamsException(String.format("параметр %s содержит не числовое значение", QUANTITY_PARAM));
            }
        }
        if (missingParams.size() > 0) {
            throw new MissingParamsException(missingParams);
        }
    }
}
