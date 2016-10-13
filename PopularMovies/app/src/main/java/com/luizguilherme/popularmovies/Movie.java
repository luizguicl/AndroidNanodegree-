package com.luizguilherme.popularmovies;

import java.io.Serializable;

public class Movie implements Serializable {

    private int id;
    private String originalTitle;
    private String overview;
    private String posterPath;
    private String releaseDate;
    private double voteAverage;
    private double popularity;

    public Movie(int id, String originalTitle, String posterPath, String overview, double voteAverage, String releaseDate, double popularity) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public double getPopularity() {
        return popularity;
    }

}

