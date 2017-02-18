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

public class Recipe {
    private int totalMinutes;
    private double temperature;
    public Recipe() {
    }
    public int getTotalMinutes() {
        return totalMinutes;
    }
    public double getTemperature() {
        return temperature;
    }

    public void setTotalMinutes(int totalMinutes) {
        if (totalMinutes <= 0) {
            throw new IllegalArgumentException("Total time must be a positive number in minutes.");
        }
        this.totalMinutes = totalMinutes;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
