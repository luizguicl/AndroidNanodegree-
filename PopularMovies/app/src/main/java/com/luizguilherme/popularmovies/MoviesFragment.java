package com.luizguilherme.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;


public class MoviesFragment extends Fragment {

    private final String TAG = MoviesFragment.class.getSimpleName();

    public MoviesFragment() {
        // Required empty public constructor
    }

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        MoviesAdapter moviesAdapter = new MoviesAdapter(
                getActivity(),
                new ArrayList<Movie>()
        );

        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        GridView listView = (GridView) rootView.findViewById(R.id.moviesList);
        listView.setAdapter(moviesAdapter);


        if (isOnline()) {
            FetchPopularMoviesTask moviesTask = new FetchPopularMoviesTask(moviesAdapter);
            moviesTask.execute();
        } else {
            Toast.makeText(getActivity(), "There is no internet connection!", Toast.LENGTH_SHORT).show();
        }


        return rootView;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
