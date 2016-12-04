package ru.prgmt.warehouse.implementation.record;

import ru.prgmt.warehouse.implementation.InventoryParamsException;
import ru.prgmt.warehouse.implementation.record.Record;

import java.util.Map;

public interface IRecordCreator {
    Record create(Map<String, String> params) throws InventoryParamsException;
    String name();
}
