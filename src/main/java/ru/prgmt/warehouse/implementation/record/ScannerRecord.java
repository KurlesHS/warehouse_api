package ru.prgmt.warehouse.implementation.record;

import ru.prgmt.warehouse.implementation.displayResult.DisplayResult;
import ru.prgmt.warehouse.implementation.displayResult.ScannerDisplayResult;

public class ScannerRecord extends Record {
    public static final int WIFI_FLAG = 0x01;
    public static final int ETHERNET_FLAG = 0x02;

    private int networkInterfaces = 0;
    private boolean isColor;

    public void setColor(boolean color) {
        isColor = color;
    }

    public void setNetworkInterfaces(int interfaceFlag) {
        this.networkInterfaces |= interfaceFlag;
    }

    @Override
    public String getType() {
        return "Сканнер";
    }

    @Override
    public DisplayResult displayResult() {
        ScannerDisplayResult displayResult = new ScannerDisplayResult();
        fillCommonDisplayResultParams(displayResult);
        displayResult.setColor(isColor);
        if ((networkInterfaces & WIFI_FLAG) != 0) {
            displayResult.setNetworkInterfaces(ScannerDisplayResult.WIFI_FLAG);
        }
        if ((networkInterfaces & ETHERNET_FLAG) != 0) {
            displayResult.setNetworkInterfaces(ScannerDisplayResult.ETHERNET_FLAG);
        }

        return displayResult;
    }
}
