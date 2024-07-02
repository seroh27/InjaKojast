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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;
import java.util.Random;
import android.util.Log;
public class MapActivity extends Activity {

    private WebView webView;
    private RelativeLayout layout;

    @SuppressLint({"MissingInflatedId", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        String difficulty;
        String cityName;
        int imageIndex;
        difficulty = getDifficulty();
        webView = findViewById(R.id.webView);
        layout = findViewById(R.id.rootLayout);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/map.html");
        Intent intent = getIntent();
        if (intent != null) {
            cityName = getCityName();
        } else {
            cityName = null;
            imageIndex = 0;
        }
        Button backButton = findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(MapActivity.this, ShowImageActivity.class);
            intent1.putExtra("FROM_ACTIVITY","MapActivity");
            startActivity(intent1);
        });
        addCityButtons();

    }
    private String getDifficulty() {
        SharedPreferences sharedPref = getSharedPreferences("WhereIsThis", Context.MODE_PRIVATE);
        return sharedPref.getString("DIFFICULTY", "Easy");
    }
    private String getCityName() {
        SharedPreferences sharedPref = getSharedPreferences("WhereIsThis", Context.MODE_PRIVATE);
        return sharedPref.getString("CITYNAME", "Easy");
    }
    private void addCityButtons() {
        int[][] cityCoordinates = {
                {360, 600}, //tehran
                {410, 1100},//fars
                {550, 850},//yazd
                {380, 800},//isfahan
                {500, 610},//semnan
                {700, 610},//kh razavi
                {740, 900},//south khorasan
                {650, 1050},//Kerman
                {810, 1200},//sistan baluchestan
                {600, 1250},//Hormozgan
                {510, 480},//Golestan
                {620, 450},//north khorasan
                {380, 530},//Mazandaran
                {320, 1130},//Mazandaran
                {330, 670},//Qom
                {5, 440},//West Azerbaijan
                {90, 420},//East Azerbaijan
                {170, 360},//Ardabil
                {200, 930},//Khuzistan
                {180, 780},//Lorestan
                {100, 600},//Kurdistan
                {80, 700},//Kermanshah
                {90, 800},//Ilam
                {290, 900},//"Charmahal Bakhtiari"
                {300, 970},//"Kohgiluyeh and Boyer-Ahmad"
                {180, 520},//Zanjan
                {230, 450},//Gilan
                {260, 550},//Qazvin
                {260,700},//Markazi
                {180,670},//Hamedan
        };

        String[] cityNames = {"Tehran", "Fars","Yazd","Isfahan"
                ,"Semnan","Khorasan Razavi",
                "South Khorasan","Kerman",
                "Sistan Baluchestan","Hormozgan"
                ,"Golestan","North Khorasan","Mazandaran","Bushehr",
                "Qom","West Azerbaijan","East Azerbaijan","Ardabil",
                "Khuzistan","Lorestan","Kurdistan","Kermanshah","Ilam","Charmahal Bakhtiari",
        "Kohgiluyeh and Boyer-Ahmad","Zanjan","Gilan","Qazvin","Markazi","Hamedan"};

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
//            cityButton.setBackgroundColor(android.graphics.Color.RED);
            cityButton.setTextColor(android.graphics.Color.WHITE);
            cityButton.setTextSize(7);

            cityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCityClick(cityName);
                }
            });

            layout.addView(cityButton);
        }
    }

    private void onCityClick(String city) {
        // Handle city click
        Toast.makeText(this, "City clicked: " + city, Toast.LENGTH_SHORT).show();
        // You can navigate to another activity or display more information here
    }

}