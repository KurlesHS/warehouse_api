package ru.prgmt.warehouse.implementation.record;

import ru.prgmt.warehouse.implementation.displayResult.DisplayResult;
import ru.prgmt.warehouse.implementation.displayResult.MonitorDisplayResult;

public class MonitorRecord extends Record {
    public enum Kind {
        Tube,
        Lcd,
        Projector
    }

    private int size;
    private boolean isColor;
    private Kind kind = Kind.Tube;

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
    public String getType() {
        return "Монитор";
    }

    @Override
    public DisplayResult displayResult() {
        MonitorDisplayResult displayResult = new MonitorDisplayResult();
        fillCommonDisplayResultParams(displayResult);
        displayResult.setSize(size);
        displayResult.setColor(isColor);
        switch (kind) {
            case Tube:
                displayResult.setKind(MonitorDisplayResult.Kind.Tube);
                break;
            case Lcd:
                displayResult.setKind(MonitorDisplayResult.Kind.Lcd);
                break;
            case Projector:
                displayResult.setKind(MonitorDisplayResult.Kind.Projector);
                break;
        }
        return displayResult;
    }

}
