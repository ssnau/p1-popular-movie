package com.example.liuxijin.popular_movies.utils;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.liuxijin.popular_movies.enums.SORT_TYPE;
import com.example.liuxijin.popular_movies.interfaces.IMovieList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liuxijin on 10/31/16.
 */

public class MovieListTask extends AsyncTask<SORT_TYPE, Void, ArrayList<Movie>> {
    IMovieList movieList;

    public void setMovieList(IMovieList movieList) {
        this.movieList = movieList;
    }

    @Override
    protected ArrayList<Movie> doInBackground(SORT_TYPE... sortTypes) {
        switch (sortTypes[0]) {
            case POPULARITY:
                return getMovies("popularity.desc");
            case TOP_RATED:
                return getMovies("vote_average.desc");
        }
        return new ArrayList<Movie>();
    }

    private String getImageFullPath(String path) {
        return "https://image.tmdb.org/t/p/w185_and_h278_bestv2/" + path;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        movieList.setMovies(movies);
    }

    public ArrayList<Movie> getMovies(String sortMethod) {
        String myUrl = Uri.parse("https://api.themoviedb.org/3/discover/movie")
                .buildUpon()
                .appendQueryParameter("sort_by", sortMethod)
                .appendQueryParameter("api_key", Constants.API_KEY)
                .build()
                .toString();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(myUrl).build();
        try {
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            JSONObject obj = new JSONObject(json);
            JSONArray movieArray = obj.getJSONArray("results");
            ArrayList<Movie> movies = new ArrayList<>();
            for (int i = 0; i < movieArray.length(); i++) {
                Movie m = new Movie();
                JSONObject item = movieArray.getJSONObject(i);
                m.setId(item.getString("id"));
                m.setTitle(item.getString("title"));
                m.setPoster_url(getImageFullPath(item.getString("poster_path")));
                m.setRelease_date(item.getString("release_date"));
                m.setVote_average(item.getString("vote_average"));
                m.setOverview(item.getString("overview"));
                movies.add(m);
            }
            return movies;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<Movie>();
    }

}
