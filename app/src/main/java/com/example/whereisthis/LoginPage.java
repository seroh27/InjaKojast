package com.example.whereisthis;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class LoginPage extends Activity {

    private EditText editTextUsername;
    private Button buttonLogin;
    private DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        editTextUsername = findViewById(R.id.editTextUsername);
        buttonLogin = findViewById(R.id.buttonLogin);
        databaseHelper = new DatabaseHelper(this);

        buttonLogin.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();
            if (!username.isEmpty()) {
                saveUserName(username);
                boolean insertedToDb = databaseHelper.addUser(username, 0);
                int highScore = databaseHelper.getHighScore(username);
                System.out.println("from db" + highScore);
                saveHighScore(highScore);

                if (insertedToDb) {
                    Toast.makeText(LoginPage.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginPage.this, "Failed to save username to database.", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(LoginPage.this, DifficultyActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginPage.this, "Please enter a user name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserName(String userName) {
        SharedPreferences sharedPref = getSharedPreferences("WhereIsThis", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("USERNAME", userName);
        editor.apply();
    }

    private void saveHighScore(int highScore) {
        SharedPreferences sharedPref = getSharedPreferences("WhereIsThis", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("HIGHSCORE", String.valueOf(highScore));
        editor.apply();
    }


}
