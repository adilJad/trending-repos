package com.jadeddib.trendingrepos.model;

import java.text.DecimalFormat;

/**
 * Created by jad on 16/01/2018.
 */

public class Repo {
    private String title;
    private String description;
    private String username;
    private String avatarUrl;
    private String ratings;

    public Repo() {
    }

    public Repo(String title,
                String description,
                String username,
                String avatarUrl,
                String ratings) {

        this.title = title;
        this.description = description;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.ratings = ratings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title =
                title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description =
                description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username =
                username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl =
                avatarUrl;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        String sr;
        if (ratings > 1000) {
            DecimalFormat decimalFormat = new DecimalFormat("##.00");
            Double r = ratings / 1000.0;
            sr = decimalFormat.format(r) + "k";
        } else {
            sr = String.valueOf(ratings);
        }
        this.ratings = sr;
    }

    @Override
    public String toString() {
        return "Repo{" +
                "title='" +
                title +
                '\'' +
                ", description='" +
                description +
                '\'' +
                ", username='" +
                username +
                '\'' +
                ", avatarUrl='" +
                avatarUrl +
                '\'' +
                ", ratings='" +
                ratings +
                '\'' +
                '}';
    }
}
