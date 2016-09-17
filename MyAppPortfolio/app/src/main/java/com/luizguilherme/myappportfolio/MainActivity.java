package com.luizguilherme.myappportfolio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.luizguilherme.myappportfolio.R.id;
import static com.luizguilherme.myappportfolio.R.layout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        Button popularMoviesButton = (Button) findViewById(id.popular_movies);
        Button stockHawkButton = (Button) findViewById(id.stock_hawk);
        Button builtItBiggerButton = (Button) findViewById(id.built_it_bigger);
        Button appMaterialButton = (Button) findViewById(id.app_material);
        Button goUbiquitousButton = (Button) findViewById(id.go_ubiquitous);
        Button capstoneButton = (Button) findViewById(id.capstone);

        popularMoviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast((Button) v);
            }
        });

        stockHawkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast((Button) v);
            }
        });

        builtItBiggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast((Button) v);
            }
        });

        appMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast((Button) v);
            }
        });

        goUbiquitousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast((Button) v);
            }
        });

        capstoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast((Button) v);
            }
        });

    }

    private void showToast(Button button) {
        String launchAppMessage = String.format(getString(R.string.launch_app_message), button.getText());
        Toast.makeText(MainActivity.this, launchAppMessage, Toast.LENGTH_SHORT).show();
    }
}
