package com.example.githubhunter.utils;

import android.util.Log;

import com.example.githubhunter.entities.PlanetEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class PlanetJsonUtils {
    public static PlanetEntity[] parseRepoFromJson(String planetJsonString) throws JSONException {
        final String PLANET_LIST = "results";
        final String PLANET_NAME = "name";
        final String PLANET_GRAVITY = "gravity";
        final String PLANET_TERRAIN = "terrain";

        JSONObject planetsJSON = new JSONObject(planetJsonString);


        JSONArray planetsList = planetsJSON.getJSONArray(PLANET_LIST);

        Log.i("Juan", String.valueOf(planetsList.length()));




        PlanetEntity[] parsedPlanetList = new PlanetEntity[planetsList.length()];

        for (int i = 0; i < planetsList.length(); i++) {
            JSONObject parsedPlanet = planetsList.getJSONObject(i);

            String name = parsedPlanet.getString(PLANET_NAME);
            String terrain = parsedPlanet.getString(PLANET_TERRAIN);
            String gravity = parsedPlanet.getString(PLANET_GRAVITY);
            PlanetEntity repo = new PlanetEntity(name, gravity, terrain);

            parsedPlanetList[i] = repo;
        }
        return parsedPlanetList;
    }
}
