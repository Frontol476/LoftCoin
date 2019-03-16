package com.danabek.loftcoin.screens.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.danabek.loftcoin.R;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, WelcomeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
}
