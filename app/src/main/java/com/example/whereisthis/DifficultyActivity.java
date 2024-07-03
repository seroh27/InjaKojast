package com.example.whereisthis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DifficultyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_diff);

        Button easyButton = findViewById(R.id.easyButton);
        Button mediumButton = findViewById(R.id.mediumButton);
        Button hardButton = findViewById(R.id.hardButton);
        saveCount(String.valueOf(0));
        View.OnClickListener listener = v -> {
            String difficulty = "";
            if (v.getId() == R.id.easyButton) {
                difficulty = "Easy";
            } else if (v.getId() == R.id.mediumButton) {
                difficulty = "Medium";
            } else if (v.getId() == R.id.hardButton) {
                difficulty = "Hard";
            }
            saveDifficulty(difficulty);
            saveScore();
            Intent intent = new Intent(DifficultyActivity.this, ShowImageActivity.class);
            startActivity(intent);
        };
        Button backButton = findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(DifficultyActivity.this, LoginPage.class);
            intent1.putExtra("FROM_ACTIVITY", "MapActivity");
            startActivity(intent1);
        });
        easyButton.setOnClickListener(listener);
        mediumButton.setOnClickListener(listener);
        hardButton.setOnClickListener(listener);
    }
    private void saveDifficulty(String userName) {
        SharedPreferences sharedPref = getSharedPreferences("WhereIsThis", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("DIFFICULTY", userName);
        editor.apply();
    }
    private void saveScore() {
        SharedPreferences sharedPref = getSharedPreferences("WhereIsThis", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("SCORE", String.valueOf(0));
        editor.apply();
    }
    private void saveCount(String count) {
        SharedPreferences sharedPref = getSharedPreferences("WhereIsThis", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("COUNT", count);
        editor.apply();
    }
}
