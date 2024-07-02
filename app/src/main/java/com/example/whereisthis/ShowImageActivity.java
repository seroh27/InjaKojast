package com.example.whereisthis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;
import java.util.Random;

public class ShowImageActivity extends Activity {
    private ImageView imageView;
    private TextView difficultyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_shown);

        // Initialize views
        imageView = findViewById(R.id.imageView);
        difficultyTextView = findViewById(R.id.difficultyTextView);
        String difficulty;
        difficulty = getDifficulty();
        Intent intent = getIntent();
        difficultyTextView.setText("Difficulty: " + difficulty);
        String cityName;
        int imageIndex;
        if (intent != null && Objects.equals(getIntent().getStringExtra("FROM_ACTIVITY"), "MapActivity")) {
            cityName = getCityName();
            Log.e("Inappin","im here");
            imageIndex = Integer.parseInt(getImageIndex());
            Log.e("Index",String.valueOf(imageIndex));
        } else {
            String[] cities = {"Tehran", "Fars", "Isfahan"};
            int[] indices = {1,2};
            Random random = new Random();
            cityName = cities[random.nextInt(cities.length)];
            imageIndex = indices[random.nextInt(indices.length)];
        }
        Log.e("Index",String.valueOf(imageIndex) );
        String imageName = difficulty.toLowerCase() + "_" + cityName.toLowerCase() + "_" + imageIndex;
        int imageResource = getResources().getIdentifier(imageName, "drawable", getPackageName());
        imageView.setImageResource(imageResource);
        Button backButton = findViewById(R.id.back);
        Button goToMapButton = findViewById(R.id.goButton);
        int finalImageIndex = imageIndex;
        String finalCityName = cityName;
        goToMapButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(ShowImageActivity.this, MapActivity.class);
            saveImageIndex(String.valueOf(imageIndex));
            saveCityName(cityName);
            startActivity(intent1);
        });
        backButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(ShowImageActivity.this, DifficultyActivity.class);
            startActivity(intent1);
        });
    }
    private String getDifficulty() {
        SharedPreferences sharedPref = getSharedPreferences("WhereIsThis", Context.MODE_PRIVATE);
        return sharedPref.getString("DIFFICULTY", "Easy");
    }
    private void saveImageIndex(String imageIndex) {
        SharedPreferences sharedPref = getSharedPreferences("WhereIsThis", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("IMAGEINDEX", imageIndex);
        editor.apply();
    }
    private void saveCityName(String cityName) {
        SharedPreferences sharedPref = getSharedPreferences("WhereIsThis", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("CITYNAME", cityName);
        editor.apply();
    }
    private String getImageIndex() {
        SharedPreferences sharedPref = getSharedPreferences("WhereIsThis", Context.MODE_PRIVATE);
        return sharedPref.getString("IMAGEINDEX", "Easy");
    }
    private String getCityName() {
        SharedPreferences sharedPref = getSharedPreferences("WhereIsThis", Context.MODE_PRIVATE);
        return sharedPref.getString("CITYNAME", "Easy");
    }
}
