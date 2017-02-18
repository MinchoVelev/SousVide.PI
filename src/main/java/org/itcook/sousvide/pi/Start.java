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
import org.itcook.sousvide.pi.gpio.impl.HardwareController;
import org.itcook.sousvide.pi.sensors.impl.TempAccessorImpl;
import org.itcook.sousvide.pi.web.Server;

public class Start {
    public static void main(String[] args) {
        HardwareController newController = new HardwareController();
        Cooker.init(newController, new TempAccessorImpl());
        if (args.length > 0 && "stop".equalsIgnoreCase(args[0])) {
            newController.turnOff();
            return;
        }
        Cooker.turnOn();

        Server server = new Server();
        server.initServer();
    }
}
