package ru.prgmt.warehouse.implementation.record;

import ru.prgmt.warehouse.implementation.displayResult.DisplayResult;

import java.io.Serializable;
import java.util.Date;


public abstract class Record implements Serializable {

    private int sku;
    private int quantity = 1;
    private String name;
    private Date date = new Date();

    public void setSku(int sku) {
        this.sku = sku;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public abstract String getType();

    public abstract DisplayResult displayResult();

    void fillCommonDisplayResultParams(DisplayResult displayResult) {
        displayResult.setQuantity(quantity);
        displayResult.setDate(date);
        displayResult.setName(name);
        displayResult.setSku(sku);
    }

    public int getSku() {
        return sku;
    }

    protected int getQuantity() {
        return quantity;
    }

    protected String getName() {
        return name;
    }

    protected Date getDate() {
        return date;
    }
}