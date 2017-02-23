package org.itcook.sousvide.pi.gpio;

public interface HardwareController {
    void setHeaterHigh();
    void setHeaterLow();
    void turnOff();
}
