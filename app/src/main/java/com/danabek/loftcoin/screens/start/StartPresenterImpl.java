package com.danabek.loftcoin.screens.start;

import com.danabek.loftcoin.data.api.Api;
import com.danabek.loftcoin.data.api.model.RateResponse;
import com.danabek.loftcoin.data.prefs.Prefs;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class StartPresenterImpl implements StartPresenter {

    private Prefs prefs;
    private Api api;

    @Nullable
    private StartView view;

    public StartPresenterImpl(Prefs prefs, Api api) {
        this.prefs = prefs;
        this.api = api;
        this.view = view;
    }


    @Override
    public void attachView(StartView view) {
        this.view = view;

    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void loadRates() {
        Call<RateResponse> call = api.rates(api.CONVERT);
        call.enqueue(new Callback<RateResponse>() {
            @Override
            public void onResponse(@NotNull Call<RateResponse> call, @NotNull Response<RateResponse> response) {
                if (view != null) {
                    view.navigateToMainScreen();
                }
            }

            @Override
            public void onFailure(@NotNull Call<RateResponse> call, @NotNull Throwable t) {
                Timber.e(t);
            }
        });
    }
}
