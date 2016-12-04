package ru.prgmt.warehouse.implementation.displayResult;


public class ScannerDisplayResult extends DisplayResult{
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
    String getType() {
        return "Сканнер";
    }
    @Override
    public String toString() {
        String color = isColor ? "цветной" : "ч/б";
        String interfaces = "";
        if ((networkInterfaces & ETHERNET_FLAG) != 0) {
            interfaces = ", с Ethernet";
        }
        if ((networkInterfaces & WIFI_FLAG) != 0) {
            interfaces += ", с WiFi";
        }
        String hasNetwork = interfaces.length() > 0 ?
                "сетевой" : "локальный";

        return String.format("%s - %s, %s%s", super.toString(), color, hasNetwork, interfaces);
    }
}
