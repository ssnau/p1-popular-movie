package com.example.liuxijin.popular_movies;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.liuxijin.popular_movies.adapters.ImageAdapter;
import com.example.liuxijin.popular_movies.enums.SORT_TYPE;
import com.example.liuxijin.popular_movies.interfaces.IMovieList;
import com.example.liuxijin.popular_movies.utils.Constants;
import com.example.liuxijin.popular_movies.utils.Movie;
import com.example.liuxijin.popular_movies.utils.MovieListTask;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import static com.example.liuxijin.popular_movies.enums.SORT_TYPE.POPULARITY;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, IMovieList {
    SORT_TYPE sortType = POPULARITY;
    ArrayList<Movie> movies = new ArrayList<>();
    ImageAdapter imageAdapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, DetailActivity.class);
        Movie movie = movies.get(i);
        Movie.putIntoCache(movie.getId(), movie);
        intent.putExtra(getString(R.string.movie_id), movies.get(i).getId());
        startActivity(intent);
    }

    public void showSimpleDialog(String text) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        builder.setTitle("Alert");
        builder.setMessage(text);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Popular Movies");
        if (Constants.API_KEY.equals("")) showSimpleDialog("Please Fill API_KEY in Constants.java first!");
        GridView gridView = ((GridView) findViewById(R.id.main_grid));
        imageAdapter = new ImageAdapter(this);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(this);
        fetchMovies();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SORT_TYPE oldType = sortType;
        switch (item.getItemId()) {
            case R.id.menu_popularity:
                sortType = POPULARITY;
                break;
            case R.id.menu_top_rated:
                sortType = SORT_TYPE.TOP_RATED;
                break;
        }
        if (sortType != oldType) {
            fetchMovies();
            Toast.makeText(this, "Fetching Movies", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchMovies() {
        MovieListTask mft = new MovieListTask();
        mft.setMovieList(this);
        mft.execute(sortType);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        if (movies == null) return;
        ArrayList<String> strings = new ArrayList<>();
        for (Movie movie : movies) {
            strings.add(movie.getPoster_url());
        }
        imageAdapter.updateURLs(strings.toArray(new String[strings.size()]));

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
