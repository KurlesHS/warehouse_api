package ru.prgmt.warehouse.implementation.displayResult;

import ru.prgmt.warehouse.implementation.StringHelpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DisplayResult implements Comparable<DisplayResult>{
    private int sku;
    private int quantity = 1;
    private String name;
    private Date date = new Date();

    public int getSku() {
        return sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

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

    public int compareTo(DisplayResult o) {

        return -1;
    }

    abstract String getType();

    @Override
    public String toString() {
        String quantityDescription = StringHelpers.getSuffixForNumber(quantity, "штука", "штуки", "штук");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return String.format("%s %d %s %s \"%s\"", dateFormat.format(date),
                quantity, quantityDescription, getType(), name);
    }

}
