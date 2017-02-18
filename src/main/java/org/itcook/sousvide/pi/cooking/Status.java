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

public class Status {
    private boolean isCooking;
    private long startTime;
    private long elapsedTime;
    private double waterTemperature;
    private volatile Recipe curentRecipe;

    public Status() {
        setCooking(false);
        setStartTime(System.currentTimeMillis());
        setElapsedTime(0);
        setWaterTemperature(0.0);
        setCurentRecipe(null);
    }

    public boolean isCooking() {
        return isCooking;
    }

    public void setCooking(boolean isCooking) {
        this.isCooking = isCooking;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        if (startTime <= 0) {
            throw new IllegalArgumentException("Elapsed time must be a positive integer in minutes.");
        }
        this.startTime = startTime;
    }

    public double getWaterTemperature() {
        return waterTemperature;
    }

    public void setWaterTemperature(double waterTemperature) {
        this.waterTemperature = waterTemperature;
    }

    public Recipe getCurentRecipe() {
        return curentRecipe;
    }

    public void setCurentRecipe(Recipe curentRecipe) {
        this.curentRecipe = curentRecipe;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        if (elapsedTime < 0) {
            throw new IllegalArgumentException("Elapsed time must be a positive or zero integer in minutes.");
        }
        this.elapsedTime = elapsedTime;
    }

}
