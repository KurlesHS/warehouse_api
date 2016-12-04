package ru.prgmt.warehouse.implementation.record;


import ru.prgmt.warehouse.implementation.displayResult.DisplayResult;
import ru.prgmt.warehouse.implementation.displayResult.PrinterDisplayResult;

public class PrinterRecord extends Record {
    private boolean isNetwork;
    private boolean isColor;

    public void setNetwork(boolean network) {
        isNetwork = network;
    }

    public void setColor(boolean color) {
        isColor = color;
    }

    @Override
    public String getType() {
        return "Принтер";
    }

    @Override
    public DisplayResult displayResult() {
        PrinterDisplayResult displayResult = new PrinterDisplayResult();
        fillCommonDisplayResultParams(displayResult);
        displayResult.setColor(isColor);
        displayResult.setNetwork(isNetwork);
        return displayResult;
    }
}
