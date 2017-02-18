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

package org.itcook.sousvide.pi.web.api;

import org.itcook.sousvide.pi.cooking.Cooker;
import org.itcook.sousvide.pi.cooking.Recipe;
import org.itcook.sousvide.pi.cooking.Status;

import spark.Request;
import spark.Response;

import com.google.gson.GsonBuilder;

public class CookingApi {
    public Status getStatus(Request req, Response resp) {
        resp.type("application/json");

        return Cooker.getStatus();
    }
    public Recipe postRecipe(Request req, Response resp) {
        Recipe recipe = new GsonBuilder().create().fromJson(req.body(), Recipe.class);
        Cooker.startCooking(recipe);
        resp.status(201);

        return recipe;
    }
}
