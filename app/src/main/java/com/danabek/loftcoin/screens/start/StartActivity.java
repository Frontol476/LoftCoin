package com.danabek.loftcoin.screens.start;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.danabek.loftcoin.App;
import com.danabek.loftcoin.R;
import com.danabek.loftcoin.data.api.Api;
import com.danabek.loftcoin.data.prefs.Prefs;
import com.danabek.loftcoin.screens.main.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;

public class StartActivity extends AppCompatActivity implements StartView {

    public static void start(Context context) {
        Intent starter = new Intent(context, StartActivity.class);
        context.startActivity(starter);
    }

    public static void startInNewTask(Context context) {
        Intent starter = new Intent(context, StartActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(starter);
    }

    @BindView(R.id.start_top_corner)
    ImageView topCorner;

    @BindView(R.id.start_bottom_corner)
    ImageView bottomCorner;

    private StartPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Prefs prefs = ((App) getApplication()).getPrefs();
        Api api = (((App) getApplication()).getApi());

        presenter = new StartPresenterImpl(prefs, api);

        presenter.attachView(this);

        presenter.loadRates();

    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();

    }

    @Override
    public void navigateToMainScreen() {
        MainActivity.start(this);
    }
}
