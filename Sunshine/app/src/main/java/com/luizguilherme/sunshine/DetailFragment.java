package com.luizguilherme.sunshine;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int WEATHER_DETAIL_LOADER_ID = 1;

    private String forecastStringUri;
    private String forecastDataString;

    private ShareActionProvider shareActionProvider;

    private TextView dayview;
    private TextView dateview;
    private TextView hightemperatureview;
    private TextView lowtemperatureview;
    private TextView humidityview;
    private TextView windview;
    private TextView pressureview;
    private android.widget.ImageView iconview;

    public DetailFragment() {
    }

    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        iconview = (ImageView) rootView.findViewById(R.id.icon_view);
        pressureview = (TextView) rootView.findViewById(R.id.pressure_view);
        windview = (TextView) rootView.findViewById(R.id.wind_view);
        humidityview = (TextView) rootView.findViewById(R.id.humidity_view);
        lowtemperatureview = (TextView) rootView.findViewById(R.id.low_temperature_view);
        hightemperatureview = (TextView) rootView.findViewById(R.id.high_temperature_view);
        dateview = (TextView) rootView.findViewById(R.id.date_view);
        dayview = (TextView) rootView.findViewById(R.id.day_view);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            forecastStringUri = intent.getDataString();
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(WEATHER_DETAIL_LOADER_ID, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detailfragment, menu);

        MenuItem item = menu.findItem(R.id.action_share);

        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

    }

    private Intent createShareIntent(String shareForecastData) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareForecastData);
        shareIntent.setType("text/plain");
        return shareIntent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void setShareIntent(Intent shareIntent) {
        if (shareActionProvider != null) {
            shareActionProvider.setShareIntent(shareIntent);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case WEATHER_DETAIL_LOADER_ID:

                return new CursorLoader(
                        getActivity(),   // Parent activity context
                        Uri.parse(forecastStringUri),// Table to query
                        ForecastFragment.FORECAST_COLUMNS,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null        // Default sort order
                );
            default:
                // An invalid id was passed in
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToNext()) {
            forecastDataString = convertCursorRowToUXFormat(data);

            bindData(data);

            String shareForecastData = TextUtils.concat(forecastDataString, "#SunshineApp").toString();

            Intent shareIntent = createShareIntent(shareForecastData);
            setShareIntent(shareIntent);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void bindData(Cursor data) {
        int weatherId = data.getInt(ForecastFragment.COL_WEATHER_CONDITION_ID);
        long dateInMilli = data.getLong(ForecastFragment.COL_WEATHER_DATE);
        double high = data.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP);
        double low = data.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP);
        float humidity = data.getFloat(ForecastFragment.COL_WEATHER_HUMIDITY);
        float windSpeed = data.getFloat(ForecastFragment.COL_WEATHER_WIND_SPEED);
        float degrees = data.getFloat(ForecastFragment.COL_WEATHER_DEGREES);
        float pressure = data.getFloat(ForecastFragment.COL_WEATHER_PRESSURE);

        iconview.setImageResource(Utility.getArtResourceForWeatherCondition(weatherId));
        dayview.setText(Utility.getDayName(getContext(), dateInMilli));
        dateview.setText(Utility.getFormattedMonthDay(getContext(), dateInMilli));
        hightemperatureview.setText(getContext().getString(R.string.format_temperature, high));
        lowtemperatureview.setText(getContext().getString(R.string.format_temperature, low));
        humidityview.setText(getContext().getString(R.string.format_humidity, humidity));
        windview.setText(Utility.getFormattedWind(getContext(), windSpeed, degrees));
        pressureview.setText(getContext().getString(R.string.format_pressure, pressure));


    }

    private String convertCursorRowToUXFormat(Cursor cursor) {

        String highAndLow = formatHighLows(
                cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP),
                cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP));

        return Utility.formatDate(cursor.getLong(ForecastFragment.COL_WEATHER_DATE)) +
                " - " + cursor.getString(ForecastFragment.COL_WEATHER_DESC) +
                " - " + highAndLow;
    }

    private String formatHighLows(double high, double low) {
        boolean isMetric = Utility.isMetric(getActivity());
        String highLowStr = Utility.formatTemperature(getContext(), high, isMetric) + "/" + Utility.formatTemperature(getContext(), low, isMetric);
        return highLowStr;
    }
}
