package com.luizguilherme.popularmovies.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luizguilherme.popularmovies.Constants;
import com.luizguilherme.popularmovies.Movie;
import com.luizguilherme.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import static com.luizguilherme.popularmovies.Constants.MOVIEDB_BASE_URL;
import static com.luizguilherme.popularmovies.Constants.POSTER_SIZE;


public class MovieDetailFragment extends Fragment {

    private static final String TAG = MovieDetailFragment.class.getSimpleName();

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Movie movie = null;

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Constants.EXTRA_MOVIE)) {
            movie = (Movie) intent.getSerializableExtra(Constants.EXTRA_MOVIE);
        }else{
            Log.d(TAG, "Could not get movie from extras.");
            return rootView;
        }

        ImageView moviePoster = (ImageView) rootView.findViewById(R.id.movie_poster);
        TextView originalTitle = (TextView) rootView.findViewById(R.id.original_title);
        TextView releaseDate = (TextView) rootView.findViewById(R.id.release_date);
        TextView userRating = (TextView) rootView.findViewById(R.id.user_rating);
        TextView overview = (TextView) rootView.findViewById(R.id.overview);

        String imageUrl = MOVIEDB_BASE_URL + POSTER_SIZE + movie.getPosterPath();

        Picasso.with(getContext()).load(imageUrl).into(moviePoster);
        originalTitle.setText(movie.getOriginalTitle());
        String formattedDate = String.format("(%s)", movie.getReleaseDate());
        releaseDate.setText(formattedDate);
        userRating.setText(String.format(new Locale(getString(R.string.languague), getString(R.string.country)), "%.2f", movie.getVoteAverage()));
        overview.setText(movie.getOverview());

        return rootView;
    }

}
