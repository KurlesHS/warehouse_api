package ru.prgmt.warehouse.implementation;

import ru.prgmt.warehouse.implementation.record.Record;

import java.util.Collection;

public interface IRepository {
    boolean addOrUpdateRecord(Record record);
    boolean deleteRecord(int sku);
    boolean hasRecord(int sku);
    Collection<Record> getAllRecords();
    boolean removeAllRecords();
}
