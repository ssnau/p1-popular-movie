package com.example.liuxijin.popular_movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liuxijin.popular_movies.interfaces.IMovieDetail;
import com.example.liuxijin.popular_movies.utils.Movie;
import com.example.liuxijin.popular_movies.utils.MovieDetailTask;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity implements IMovieDetail{

    Movie mMovie;

    @Override
    public void setMovie(Movie movie) {
        mMovie = movie;
        Log.i("setMovie", movie.toString());

        ImageView imageView = (ImageView) findViewById(R.id.poster);
        Picasso.with(this)
                .load(mMovie.getPoster_url())
                .fit()
                .centerCrop()
                .into(imageView);

        ((TextView) findViewById(R.id.movie_title)).setText(mMovie.getTitle());
        ((TextView) findViewById(R.id.release_date)).setText(mMovie.getRelease_date());
        ((TextView) findViewById(R.id.rate_average)).setText(mMovie.getVote_average());
        ((TextView) findViewById(R.id.overview)).setText(mMovie.getOverview());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        this.setTitle("Movie Detail");
        String movieId = getIntent().getStringExtra(getString(R.string.movie_id));
        Movie movie = Movie.getFromCache(movieId);
        if (movie == null) {
            MovieDetailTask task = new MovieDetailTask();
            task.setMovieDetail(this);
            task.execute();
        } else {
            setMovie(movie);
        }
    }
}
