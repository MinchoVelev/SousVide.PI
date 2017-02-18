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

package org.itcook.sousvide.pi.cfg;

public class Config {
    private static final int SERVER_PORT = 8080;
    private static final int LED_PIN_NUMBER = 0;
    private static final int HEATER_PIN_NUMBER = 27;
    private static final int HEATER_PIN_NUMBER2 = 25;

    public static int getGreenLedPinNumber() {
        return LED_PIN_NUMBER;
    }
    public static int getHeaterPinNumber1() {
        return HEATER_PIN_NUMBER;
    }
    public static int getHeaterPinNumber2() {
        return HEATER_PIN_NUMBER2;
    }
    public static int getServerPort() {
        return SERVER_PORT;
    }
}
