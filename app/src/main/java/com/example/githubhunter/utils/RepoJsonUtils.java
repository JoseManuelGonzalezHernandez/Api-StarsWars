package com.example.githubhunter.utils;

import com.example.githubhunter.entities.RepoEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class RepoJsonUtils {
    public static RepoEntity[] parseRepoFromJson(String repoJsonString) throws JSONException {
        final String REPO_LIST = "items";
        final String REPO_NAME = "name";
        final String REPO_FULLNAME = "full_name";
        final String REPO_URL = "html_url";
        final String REPO_DESCRIPTION = "description";
        final String REPO_STARS = "stargazers_count";

        JSONObject repositoriesJSON = new JSONObject(repoJsonString);

        JSONArray repositoriesList = repositoriesJSON.getJSONArray(REPO_LIST);

        RepoEntity [] parsedRepoList = new RepoEntity[repositoriesList.length()];

        for (int i = 0; i < repositoriesList.length(); i++) {
            JSONObject parsedRepo = repositoriesList.getJSONObject(i);

            String repoName = parsedRepo.getString(REPO_NAME);
            String repoFullName = parsedRepo.getString(REPO_FULLNAME);
            String repoDescription = parsedRepo.getString(REPO_DESCRIPTION);
            String repoURL = parsedRepo.getString(REPO_URL);
            String repoStars = parsedRepo.getString(REPO_STARS);
            RepoEntity repo = new RepoEntity(repoName, repoFullName, repoURL, repoDescription, repoStars);

            parsedRepoList[i] = repo;
        }
        return parsedRepoList;
    }
}
