/*
 * SousVide.PI - Sous Vide Cooking with Raspberry PI
 *    Copyright (C) 2017 Mincho Velev
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package org.itcook.sousvide.pi.gpio.impl;

import org.itcook.sousvide.pi.cfg.Config;
import org.itcook.sousvide.pi.gpio.HardwareController;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

public class HardwareControllerImpl implements HardwareController {
    private GpioPinDigitalOutput greenLedPin;
    private GpioPinDigitalOutput heaterPin1;
    private GpioPinDigitalOutput heaterPin2;

    public HardwareControllerImpl() {
        GpioController gpioController = GpioFactory.getInstance();

        greenLedPin = gpioController.provisionDigitalOutputPin(RaspiPin.getPinByAddress(Config.getGreenLedPinNumber()), "led");
        heaterPin1 = gpioController.provisionDigitalOutputPin(RaspiPin.getPinByAddress(Config.getHeaterPinNumber1()), "heater1");
        heaterPin2 = gpioController.provisionDigitalOutputPin(RaspiPin.getPinByAddress(Config.getHeaterPinNumber2()), "heater2");
    }
    public void setHeaterHigh() {
        heaterPin1.high();
        heaterPin2.high();
        greenLedPin.low();
    }

    public void setHeaterLow() {
        heaterPin1.low();
        heaterPin2.low();
        greenLedPin.high();
    }

    public void turnOff() {
        heaterPin1.low();
        heaterPin2.low();
        greenLedPin.low();
    }
}
