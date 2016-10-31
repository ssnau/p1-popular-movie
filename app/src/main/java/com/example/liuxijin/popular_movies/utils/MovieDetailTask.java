package com.example.liuxijin.popular_movies.utils;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.liuxijin.popular_movies.interfaces.IMovieDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liuxijin on 10/31/16.
 */

public class MovieDetailTask extends AsyncTask<String, Void, Movie> {

    IMovieDetail movieDetail;

    public void setMovieDetail(IMovieDetail movieDetail) {
        this.movieDetail = movieDetail;
    }

    @Override
    protected Movie doInBackground(String... movieIDs) {
        String url = Uri.parse("https://api.themoviedb.org/3/movie/")
                .buildUpon()
                .appendPath(movieIDs[0])
                .appendQueryParameter("api_key", Constants.API_KEY)
                .build()
                .toString();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            JSONObject item = new JSONObject(json);
            Movie m = new Movie();
            m.setId(item.getString("id"));
            m.setTitle(item.getString("title"));
            m.setPoster_url(getImageFullPath(item.getString("poster_path")));
            m.setRelease_date(item.getString("release_date"));
            m.setVote_average(item.getString("vote_average"));
            m.setOverview(item.getString("overview"));
            return m;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getImageFullPath(String path) {
        return "https://image.tmdb.org/t/p/w185_and_h278_bestv2/" + path;
    }

    @Override
    protected void onPostExecute(Movie movie) {
        movieDetail.setMovie(movie);
    }


}
