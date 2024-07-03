package com.example.whereisthis;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;
import android.util.Log;
public class MapActivity extends Activity {

    private WebView webView;
    private RelativeLayout layout;
    private TextView ReportText;
    private TextView ScoreText;
    private TextView CorrectProvince;
    private Button nextSiteButton;

    @SuppressLint({"MissingInflatedId", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        webView = findViewById(R.id.webView);
        layout = findViewById(R.id.rootLayout);
        ReportText = findViewById(R.id.ReportText);
        ScoreText = findViewById(R.id.ScoreText);
        CorrectProvince = findViewById(R.id.CorrectProvince);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/map.html");

        String cityName = getCityName();

        Button backButton = findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(MapActivity.this, ShowImageActivity.class);
            intent1.putExtra("FROM_ACTIVITY", "MapActivity");
            startActivity(intent1);
        });

        addCityButtons(cityName);
    }

    private String getCityName() {
        SharedPreferences sharedPref = getSharedPreferences("WhereIsThis", Context.MODE_PRIVATE);
        return sharedPref.getString("CITYNAME", "Tehran");
    }

    private void addCityButtons(String correctCity) {
        int[][] cityCoordinates = {
                {360, 600}, // Tehran
                {410, 1100}, // Fars
                {550, 850}, // Yazd
                {380, 800}, // Isfahan
                {500, 610}, // Semnan
                {700, 610}, // Khorasan Razavi
                {740, 900}, // South Khorasan
                {650, 1050}, // Kerman
                {810, 1200}, // Sistan Baluchestan
                {600, 1250}, // Hormozgan
                {510, 480}, // Golestan
                {620, 450}, // North Khorasan
                {380, 530}, // Mazandaran
                {320, 1130}, // Bushehr
                {330, 670}, // Qom
                {5, 440}, // West Azerbaijan
                {90, 420}, // East Azerbaijan
                {170, 360}, // Ardabil
                {200, 930}, // Khuzistan
                {180, 780}, // Lorestan
                {100, 600}, // Kurdistan
                {80, 700}, // Kermanshah
                {90, 800}, // Ilam
                {290, 900}, // Charmahal Bakhtiari
                {300, 970}, // Kohgiluyeh and Boyer-Ahmad
                {180, 520}, // Zanjan
                {230, 450}, // Gilan
                {260, 550}, // Qazvin
                {260, 700}, // Markazi
                {180, 670}, // Hamedan
        };

        String[] cityNames = {"Tehran", "Fars", "Yazd", "Isfahan", "Semnan", "Khorasan Razavi",
                "South Khorasan", "Kerman", "Sistan Baluchestan", "Hormozgan", "Golestan", "North Khorasan",
                "Mazandaran", "Bushehr", "Qom", "West Azerbaijan", "East Azerbaijan", "Ardabil", "Khuzistan",
                "Lorestan", "Kurdistan", "Kermanshah", "Ilam", "Charmahal Bakhtiari", "Kohgiluyeh and Boyer-Ahmad",
                "Zanjan", "Gilan", "Qazvin", "Markazi", "Hamedan"};

        for (int i = 0; i < cityCoordinates.length; i++) {
            int[] coord = cityCoordinates[i];
            String cityName = cityNames[i];

            Button cityButton = new Button(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    90,
                    90);
            params.leftMargin = coord[0];
            params.topMargin = coord[1];
            cityButton.setLayoutParams(params);
            cityButton.setBackgroundResource(R.drawable.circular_button);
            cityButton.setText(cityName);
            cityButton.setTextColor(android.graphics.Color.WHITE);
            cityButton.setTextSize(7);

            cityButton.setOnClickListener(v -> onCityClick(cityName, correctCity));
            layout.addView(cityButton);
        }
    }

    @SuppressLint("SetTextI18n")
    private void onCityClick(String city, String correctCity) {
        disableAllCityButtons();
        int highScore = Integer.parseInt(getHighScore());
        int score = Integer.parseInt(getScore());
        if (Objects.equals(city, correctCity)) {
            score+=5;
            Log.d("MAP ACTIVITY", "score" + score);
            Log.d("MAP ACTIVITY", "highscore" + highScore);
            if(score > highScore) {
                highScore = score;
                saveHighScore(highScore);
            }
            saveScore(score);
            Log.d("MAP ACTIVITY", "im here" );
            ReportText.setText("Correct Answer!! HIGH SCORE: " + highScore);
        } else {
            ReportText.setText("Wrong Answer!! HIGH SCORE: " + highScore);
        }
        CorrectProvince.setText("Correct Province is: " + correctCity);
        ScoreText.setText("SCORE: " + score);
        addNextSiteButton();
    }

    private void disableAllCityButtons() {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof Button && child != findViewById(R.id.back)) {
                child.setEnabled(false);
            }
        }
    }

    private void addNextSiteButton() {
        nextSiteButton = new MaterialButton(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.bottomMargin = 350;
        nextSiteButton.setLayoutParams(params);
        nextSiteButton.setText("Next Site");
        nextSiteButton.setTextColor(android.graphics.Color.WHITE);
        nextSiteButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapActivity.this, ShowImageActivity.class); // Replace NextActivity.class with your actual next activity
            startActivity(intent);
        });

        layout.addView(nextSiteButton);
    }

    private String getUserName() {
        SharedPreferences sharedPref = getSharedPreferences("WhereIsThis", Context.MODE_PRIVATE);
        return sharedPref.getString("USERNAME", "Easy");
    }
    private void saveHighScore(int highScore) {
        SharedPreferences sharedPref = getSharedPreferences("WhereIsThis", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("HIGHSCORE");
        editor.putString("HIGHSCORE", String.valueOf(highScore));
        editor.apply();
    }
    private String getHighScore() {
        SharedPreferences sharedPref = getSharedPreferences("WhereIsThis", Context.MODE_PRIVATE);
        return sharedPref.getString("HIGHSCORE", "Easy");
    }
    private String getScore() {
        SharedPreferences sharedPref = getSharedPreferences("WhereIsThis", Context.MODE_PRIVATE);
        return sharedPref.getString("SCORE", "Easy");
    }
    private void saveScore(int score) {
        SharedPreferences sharedPref = getSharedPreferences("WhereIsThis", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("SCORE");
        editor.putString("SCORE", String.valueOf(score));
        editor.apply();
    }
}
