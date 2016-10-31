package com.example.liuxijin.popular_movies.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuxijin on 10/31/16.
 */

public class Movie {

    static Map<String, Movie> caches = new HashMap<>();

    public static void putIntoCache(String id, Movie movie) {
        caches.put(id, movie);
    }

    public static Movie getFromCache(String id) {
        return caches.get(id);
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public String getId() {
        return id;
    }

    private String id;
    private String title; // 片名
    private String poster_url;  // 电影海报缩略图
    private String overview; // 剧情简介

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    private String release_date; // 上映日期
    private String vote_average;  // 用户评分

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", poster_url='" + poster_url + '\'' +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                ", vote_average='" + vote_average + '\'' +
                '}';
    }
}
