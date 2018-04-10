package edu.galileo.android.moviemanager.models;

import java.io.Serializable;

/**
 * Created by Larry on 3/20/2018.
 */

public class Movie implements Serializable {

    final static String BASE_URL_FMT_POSTER = "https://image.tmdb.org/t/p/w342%s";
    final static String BASE_URL_FMT_BACKDROP = "https://image.tmdb.org/t/p/w780%s";
    String id;
    String title;
    String overview;
    float voteAverage;
    float voteCount;
    String posterPath;
    String backdropPath;

    public Movie(String id, String title, String overview, float voteAverage, float voteCount, String posterPath, String backdropPath) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public float getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(float voteCount) {
        this.voteCount = voteCount;
    }

    public String getPosterPath() {
        return String.format(BASE_URL_FMT_POSTER, posterPath);
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return String.format(BASE_URL_FMT_BACKDROP, backdropPath);
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

}
