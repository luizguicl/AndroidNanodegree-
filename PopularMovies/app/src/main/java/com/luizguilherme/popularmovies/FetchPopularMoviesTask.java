package com.luizguilherme.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchPopularMoviesTask extends AsyncTask<Void, Void, List<Movie>> {

    private final String TAG = FetchPopularMoviesTask.class.getSimpleName();
    private ArrayAdapter<Movie> adapter;

    public FetchPopularMoviesTask(ArrayAdapter<Movie> adapter) {
        this.adapter = adapter;
    }

    @Override
    protected List<Movie> doInBackground(Void... voids) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String popularMoviesJsonStr = null;

        try {

            // Construct the URL for the MovieDatabase api request
            final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/popular?";
            final String APPID_PARAM = "api_key";

            Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                    .appendQueryParameter(APPID_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());

            Log.d(TAG, "Built URI " + builtUri.toString());

            // Create the request to TheMovieDatabase API, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            popularMoviesJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(TAG, "Error ", e);

            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return getMovieDataFromJson(popularMoviesJsonStr);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> result) {
        if (result != null && adapter != null) {
            adapter.clear();
            adapter.addAll(result);
        }
    }

    private List<Movie> getMovieDataFromJson(String popularMoviesJsonStr) throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String MDB_RESULTS = "results";
        final String MDB_ID = "id";
        final String MDB_TITLE = "title";
        final String MDB_POSTER = "poster_path";
        final String MDB_OVERVIEW = "overview";
        final String MDB_VOTE_AVERAGE = "vote_average";
        final String MDB_RELEASE_DATE = "release_date";

        JSONObject popularMoviesJson = new JSONObject(popularMoviesJsonStr);
        JSONArray moviesArray = popularMoviesJson.getJSONArray(MDB_RESULTS);

        List<Movie> moviesResult = new ArrayList<>();

        for (int i = 0; i < moviesArray.length(); i++) {

            // Get the JSON object representing a movie
            JSONObject movieObject = moviesArray.getJSONObject(i);

            int id = Integer.parseInt(movieObject.getString(MDB_ID));
            String title = movieObject.getString(MDB_TITLE);
            String posterPath = movieObject.getString(MDB_POSTER);
            String overview = movieObject.getString(MDB_OVERVIEW);
            Double voteAverage = movieObject.getDouble(MDB_VOTE_AVERAGE);
            String releaseDate = movieObject.getString(MDB_RELEASE_DATE);

            Movie movie = new Movie(id, title, posterPath, overview, voteAverage, releaseDate);

            moviesResult.add(movie);

        }

        return moviesResult;
    }
}
