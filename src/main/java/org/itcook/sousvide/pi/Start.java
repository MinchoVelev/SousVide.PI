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

package org.itcook.sousvide.pi;

import org.itcook.sousvide.pi.cooking.Cooker;
import org.itcook.sousvide.pi.gpio.HardwareController;
import org.itcook.sousvide.pi.gpio.impl.HardwareControllerImpl;
import org.itcook.sousvide.pi.sensors.TempAccessor;
import org.itcook.sousvide.pi.sensors.impl.TempAccessorImpl;
import org.itcook.sousvide.pi.web.Server;

public class Start {
    public static void main(String[] args) {
        HardwareController newController;
        TempAccessor newAccessor;
        if (args.length == 1 && "dev".equals(args[0])) {
            newController = getDevController();
            newAccessor = getDevTempAccessor();
        } else {
            newController = new HardwareControllerImpl();
            newAccessor = new TempAccessorImpl();
        }

        Cooker.init(newController, newAccessor);
        Cooker.turnOn();

        Server server = new Server();
        server.initServer();
    }

    private static TempAccessor getDevTempAccessor() {
        return new TempAccessor() {
            private double temp = 45;
            private double addTemp = 0.25;
            @Override
            public double readTemp() {
                handleChange();
                
                return temp;
            }
            private void handleChange() {
                if (temp > 60) {
                    addTemp = -0.25;
                } else if (temp < 55) {
                    addTemp = 0.25;
                }
                temp += addTemp;
            }
        };
    }
    private static HardwareController getDevController() {
        return new HardwareController() {

            @Override
            public void turnOff() {
                // TODO Auto-generated method stub

            }

            @Override
            public void setHeaterLow() {
                // TODO Auto-generated method stub

            }

            @Override
            public void setHeaterHigh() {
                // TODO Auto-generated method stub

            }

            @Override
            public boolean isHeaterHigh() {
                // TODO Auto-generated method stub
                return false;
            }
        };
    }
}
