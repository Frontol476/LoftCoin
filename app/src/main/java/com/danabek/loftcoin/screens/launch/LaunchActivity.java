package com.danabek.loftcoin.screens.launch;

import android.os.Bundle;

import com.danabek.loftcoin.App;
import com.danabek.loftcoin.data.prefs.Prefs;
import com.danabek.loftcoin.screens.start.StartActivity;
import com.danabek.loftcoin.screens.welcome.WelcomeActivity;

import androidx.appcompat.app.AppCompatActivity;

public class LaunchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Prefs prefs = ((App) getApplication()).getPrefs();

        if (prefs.isFirstLaunch()) {
            WelcomeActivity.start(this);
        } else {
            StartActivity.start(this);

        }
        finish();
    }
}
