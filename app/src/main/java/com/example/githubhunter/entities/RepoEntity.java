package com.example.githubhunter.entities;

public class RepoEntity {
    public String name;
    public String fullName;
    public String url;
    public String description;
    public String stars;

    public RepoEntity(String name, String fullName, String url, String description, String stars) {
        this.name = name;
        this.fullName = fullName;
        this.url = url;
        this.description = description;
        this.stars = stars;
    }
}
