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

package org.itcook.sousvide.pi.web;

import org.itcook.sousvide.pi.cfg.Config;
import org.itcook.sousvide.pi.web.api.CookingApi;
import org.itcook.sousvide.pi.web.api.JsonTransformer;

import static spark.Spark.*;

public class Server {
    private static boolean isInitialized = false;

    public void initServer() {
        if (isInitialized) {
            throw new IllegalStateException("Called init on initialized server.");
        }

        CookingApi cookingApi = new CookingApi();
        JsonTransformer jsonTransformer = new JsonTransformer();

        port(Config.getServerPort());
        
        staticFileLocation("/staticContent");
        
        path("/api/cooking", () -> {
            get("/status", cookingApi::getStatus, jsonTransformer);
            post("/recipe", cookingApi::postRecipe, jsonTransformer);
        });

        awaitInitialization();
        isInitialized = true;
        System.out.println("Server initialized");
    }
}
