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

package org.itcook.sousvide.pi.sensors.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.itcook.sousvide.pi.sensors.TempAccessor;

public class TempAccessorImpl implements TempAccessor {
    private static final String TEMP_PREFIX = "t=";
    private static final String UTF_8 = "UTF-8";
    private static final String TEMP_PATH = "/sys/bus/w1/devices/28-800000289195";
    private static final String W1_SLAVE = "w1_slave";

    @Override
    public double readTemp() {
        Path path = FileSystems.getDefault().getPath(TEMP_PATH, W1_SLAVE);
        try (InputStream is = Files.newInputStream(path, StandardOpenOption.READ)) {
            InputStreamReader reader = new InputStreamReader(is, UTF_8);
            BufferedReader lineReader = new BufferedReader(reader);

            String firstLine = lineReader.readLine();
            if (firstLine.endsWith("YES")) {
                String secondLine = lineReader.readLine();
                int tempIndex = secondLine.indexOf(TEMP_PREFIX) + TEMP_PREFIX.length();
                String tempString = secondLine.substring(tempIndex);

                return Integer.parseInt(tempString) / 1000.0;
            } else {
                throw new IllegalStateException("Temp reading not stable.");
            }
        } catch (IOException e) {
            throw new IllegalStateException("Clouldn't read temp");
        }
    }

}
