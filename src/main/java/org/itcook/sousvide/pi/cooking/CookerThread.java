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

import org.itcook.sousvide.pi.sensors.impl.TempAccessor;
import org.itcook.sousvide.pi.sensors.impl.TempAccessorImpl;

public class CookerThread extends Thread {
    private static final int MINUTE_IN_MILLI_SECONDS = 60_000;

    @Override
    public void run() {
        do {
            Status status = Cooker.getStatus();

            double waterTemp = getTemp();
            status.setWaterTemperature(waterTemp);

            if (status.isCooking()) {
                handleCooking(status);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new IllegalStateException("Cooker thread interrupted!");
            }
        } while (Cooker.isRunning());
    }

    private void handleCooking(Status status) {
        long elapsedTime = (System.currentTimeMillis() - status.getStartTime()) / MINUTE_IN_MILLI_SECONDS;
        status.setElapsedTime(elapsedTime);
        Recipe currentRecipe = status.getCurentRecipe();

        if (elapsedTime < currentRecipe.getTotalMinutes()) {
            boolean isTempInRange = status.getWaterTemperature() > currentRecipe.getTemperature();
            if (isTempInRange) {
                Cooker.heatOff();
            } else {
                Cooker.heatOn();
            }

        } else {
            Cooker.stopCooking();
        }
    }

    private double getTemp() {
        TempAccessor tempAccessorImpl = new TempAccessorImpl();
        return tempAccessorImpl.readTemp();
    }
}
