package ru.prgmt.warehouse.implementation.displayResult;

public class PrinterDisplayResult extends DisplayResult {
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
    public String toString() {
        String color = isColor ? "цветной" : "ч/б";
        String network = isNetwork ? "сетевой" : "локальный";

        return String.format("%s - %s, %s", super.toString(), color, network);
    }

}
