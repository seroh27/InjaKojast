package com.example.whereisthis;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameOverActivity extends Activity {

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        TextView scoreTxt = findViewById(R.id.ScoreText);
        scoreTxt.setText("Score: " + getScore());
        Button backButton = findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(GameOverActivity.this, DifficultyActivity.class);
            intent1.putExtra("FROM_ACTIVITY", "MapActivity");
            startActivity(intent1);
        });
    }

    private String getScore() {
        SharedPreferences sharedPref = getSharedPreferences("WhereIsThis", Context.MODE_PRIVATE);
        return sharedPref.getString("SCORE", "Easy");
    }
}
