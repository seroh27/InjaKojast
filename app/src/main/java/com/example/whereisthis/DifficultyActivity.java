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
            Intent intent = new Intent(DifficultyActivity.this, ShowImageActivity.class);
            startActivity(intent);
        };

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
}
