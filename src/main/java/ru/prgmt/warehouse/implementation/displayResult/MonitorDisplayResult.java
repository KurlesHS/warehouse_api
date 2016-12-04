package ru.prgmt.warehouse.implementation.displayResult;


import ru.prgmt.warehouse.implementation.StringHelpers;

public class MonitorDisplayResult extends DisplayResult{
    public enum Kind {
        Tube,
        Lcd,
        Projector
    }

    private int size;
    private boolean isColor;
    private Kind kind = Kind.Tube;

    @Override
    public String getType() {
        return "Монитор";
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setColor(boolean color) {
        isColor = color;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        String colorStr = isColor ? "цветной" : "ч/б";
        String kindStr;
        String sizeSuffix = StringHelpers.getSuffixForNumber(size, "дюйм", "дюйма", "дюймов");
        switch (kind) {
            case Tube:
                kindStr = "ЭЛТ";
                break;
            case Lcd:
                kindStr = "ЖК";
                break;
            case Projector:
                kindStr = "проектор";
                break;
            default:
                kindStr = "-";
                break;
        }
        return String.format("%s - %s %s, %d %s", super.toString(), colorStr, kindStr, size, sizeSuffix);
    }
}
