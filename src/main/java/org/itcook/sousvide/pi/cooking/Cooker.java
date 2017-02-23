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

package org.itcook.sousvide.pi.cooking;

import org.itcook.sousvide.pi.gpio.HardwareController;
import org.itcook.sousvide.pi.sensors.TempAccessor;

public class Cooker {
    private final static Status status = new Status();
    private static HardwareController hardwareController;
    private static boolean isRunning = false;
    private static CookerThread cookerThread;
    private static TempAccessor tempAccessor;
    private static boolean isInit = false;

    public static synchronized void init(HardwareController newController, TempAccessor newAccessor) {
        if (isInit) {
            throw new IllegalStateException("Already initialized");
        }
        if (newAccessor != null || newController != null) {
            hardwareController = newController;
            hardwareController.turnOff();
            tempAccessor = newAccessor;
            tempAccessor.readTemp();
            isInit = true;
        } else {
            throw new IllegalArgumentException("Cannot init with null hardware controller or tem accessor");
        }
    }

    public static final boolean isRunning() {
        checkInit();
        return isRunning;
    }

    private Cooker() {
    }

    public static void startCooking(Recipe recipe) {
        checkInit();
        status.setCurentRecipe(recipe);
        status.setStartTime(System.currentTimeMillis());
        status.setElapsedTime(0);
        status.setCooking(true);
    }

    public static void stopCooking() {
        checkInit();
        hardwareController.turnOff();
        status.setCooking(false);
    }

    public static void heatOn() {
        checkInit();
        hardwareController.setHeaterHigh();
    }

    public static void heatOff() {
        checkInit();
        hardwareController.setHeaterLow();
    }

    public static Status getStatus() {
        checkInit();
        return status;
    }

    public static void turnOn() {
        checkInit();
        if (isRunning == true) {
            throw new IllegalStateException("Device already running");
        }
        hardwareController.turnOff();

        isRunning = true;

        cookerThread = new CookerThread();
        cookerThread.start();
    }
    private static void checkInit() {
        if (isInit == false) {
            throw new IllegalStateException("Instance not initialized");
        }
    }

    public static void turnOff() {
        checkInit();
        hardwareController.turnOff();
        isRunning = false;
        try {
            cookerThread.join();
            System.out.println("Device stopped");
        } catch (InterruptedException e) {
            throw new IllegalStateException("Cooker thread interrupted!");
        }
    }

    public static double getTemp() {
        return tempAccessor.readTemp();
    }
}
