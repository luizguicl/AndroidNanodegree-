package com.luizguilherme.myappportfolio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.luizguilherme.myappportfolio.R.id;
import static com.luizguilherme.myappportfolio.R.layout;

public class MainActivity extends AppCompatActivity {

    private Button popularMoviesButton;
    private Button stockHawkButton;
    private Button builtItBiggerButton;
    private Button appMaterialButton;
    private Button goUbiquitousButton;
    private Button capstoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        popularMoviesButton = (Button) findViewById(id.popular_movies);
        stockHawkButton = (Button) findViewById(id.stock_hawk);
        builtItBiggerButton = (Button) findViewById(id.built_it_bigger);
        appMaterialButton = (Button) findViewById(id.app_material);
        goUbiquitousButton = (Button) findViewById(id.go_ubiquitous);
        capstoneButton = (Button) findViewById(id.capstone);

        popularMoviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String launchAppMessage = String.format(getString(R.string.launch_app_message), button.getText());
                Toast.makeText(MainActivity.this, launchAppMessage, Toast.LENGTH_SHORT).show();
            }
        });

        stockHawkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String launchAppMessage = String.format(getString(R.string.launch_app_message), button.getText());
                Toast.makeText(MainActivity.this, launchAppMessage, Toast.LENGTH_SHORT).show();
            }
        });

        builtItBiggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String launchAppMessage = String.format(getString(R.string.launch_app_message), button.getText());
                Toast.makeText(MainActivity.this, launchAppMessage, Toast.LENGTH_SHORT).show();
            }
        });

        appMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String launchAppMessage = String.format(getString(R.string.launch_app_message), button.getText());
                Toast.makeText(MainActivity.this, launchAppMessage, Toast.LENGTH_SHORT).show();
            }
        });

        goUbiquitousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String launchAppMessage = String.format(getString(R.string.launch_app_message), button.getText());
                Toast.makeText(MainActivity.this, launchAppMessage, Toast.LENGTH_SHORT).show();
            }
        });

        capstoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String launchAppMessage = String.format(getString(R.string.launch_app_message), button.getText());
                Toast.makeText(MainActivity.this, launchAppMessage, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
