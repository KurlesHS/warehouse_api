package ru.prgmt.warehouse.implementation;

import ru.prgmt.warehouse.implementation.record.Record;

import java.io.*;
import java.util.*;

/**
 * База данных.
 * В качестве хранилища используется обычный HashMap, по этому для релизации ее потокобезопасной
 * достаточно поместить все операции, затрагивающие наш HashMap в synchronized блок.
 * Для уменьшения нагрузки на диск все изменения БД просиходят в памяти, для фискации изменений на диске необходимо
 * вызвать FileRepository.updateDatabase() перед выходом их программы
 */
public class FileRepository implements IRepository {
    private static final String FILENAME = "database.dat";
    private HashMap<Integer, Record> fakeBd = new HashMap<>();
    private boolean isNeedUpdateDatabase = false;

    public FileRepository() {
        readDatabase();
    }

    /* stackoverflow says that it is safe */
    @SuppressWarnings("unchecked")
    private void readDatabase() {
        synchronized (this) {
            try (FileInputStream stream = new FileInputStream(FILENAME)) {
                ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(stream));
                fakeBd = (HashMap<Integer, Record>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
            /* считаем, что у нас пустая ДБ */
            }
        }
    }

    public boolean updateDatabase() {
        boolean positive = false;
        synchronized (this) {
            // данные в базе не менялись - незачем ее и обновлять
            if (!isNeedUpdateDatabase) {
                return true;
            }
            try (FileOutputStream stream = new FileOutputStream(FILENAME)) {
                ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(stream));
                out.writeObject(fakeBd);
                positive = true;
                out.close();
                stream.close();
            } catch (IOException e) {
            /* ух ты, беда */
                System.err.println("Не получилось обновить базу данных");
            }
        }
        return positive;
    }

    @Override
    public boolean addOrUpdateRecord(Record record) {
        synchronized (this) {
            isNeedUpdateDatabase = true;
            fakeBd.put(record.getSku(), record);
            /* запись "БД" на диск принудительно перед выходом, что бы избежать нагрузки на диск
            * считаем что у нас "in memory db" */
        }
        return true;
    }

    @Override
    public boolean deleteRecord(int sku) {
        boolean result = false;
        synchronized (this) {
            if (fakeBd.containsKey(sku)) {
                fakeBd.remove(sku);
                result = true;
                isNeedUpdateDatabase = true;
            }
        }
        return result;
    }

    @Override
    public boolean hasRecord(int sku) {
        synchronized (this) {
            return fakeBd.containsKey(sku);
        }
    }

    @Override
    public Collection<Record> getAllRecords() {
        synchronized (this) {
            return fakeBd.values();
        }
    }

    @Override
    public boolean removeAllRecords() {
        synchronized (this) {
            fakeBd.clear();
            isNeedUpdateDatabase = true;
        }
        return true;
    }
}
