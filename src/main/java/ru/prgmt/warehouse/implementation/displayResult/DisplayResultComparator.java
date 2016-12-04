package ru.prgmt.warehouse.implementation.displayResult;

import java.util.Comparator;

public class DisplayResultComparator implements Comparator<DisplayResult> {
    private String field;
    private boolean sortAscending;

    public DisplayResultComparator(String field, boolean sortAscending) {
        this.field = field;
        this.sortAscending = sortAscending;
    }

    @Override
    public int compare(DisplayResult o1, DisplayResult o2) {
        /* сортировка по убываению - меняем объекты местами */
        if (!sortAscending) {
            DisplayResult t = o2;
            o2 = o1;
            o1 = t;
        }
        switch (field) {
            case "sku":
                return Integer.compare(o1.getSku(), o2.getSku());
            case "quantity":
                return Integer.compare(o1.getQuantity(), o2.getQuantity());
            case "name":
                return o1.getName().compareTo(o2.getName());
            case "date":
                return o1.getDate().compareTo(o2.getDate());
        }
        /* если неизвестное поле - сортировать не надо ничего */
        return 0;
    }
}
