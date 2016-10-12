package com.luizguilherme.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends ArrayAdapter<Movie> {

    private final String MOVIEDB_BASE_URL = "http://image.tmdb.org/t/p/";
    private final String SIZE = "w185";

    public MoviesAdapter(Context context, List<Movie> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
        }

        String imageUrl = MOVIEDB_BASE_URL + SIZE + movie.getPosterPath();

        ImageView poster = (ImageView) convertView.findViewById(R.id.poster_image);
        Picasso.with(getContext()).load(imageUrl).into(poster);


        return convertView;
    }
}
