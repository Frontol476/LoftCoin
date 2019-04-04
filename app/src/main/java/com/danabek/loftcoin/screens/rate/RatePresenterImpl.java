package com.danabek.loftcoin.screens.rate;

import com.danabek.loftcoin.data.api.Api;
import com.danabek.loftcoin.data.api.model.RateResponse;
import com.danabek.loftcoin.data.prefs.Prefs;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RatePresenterImpl implements RatePresenter {

    private Prefs prefs;
    private Api api;

    @Nullable
    private RateView view;

    public RatePresenterImpl(Prefs prefs, Api api) {
        this.prefs = prefs;
        this.api = api;
    }

    @Override
    public void attachView(RateView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void getRate() {
        Call<RateResponse> call = api.rates(api.CONVERT);
        call.enqueue(new Callback<RateResponse>() {
            @Override
            public void onResponse(@NotNull Call<RateResponse> call, @NotNull Response<RateResponse> response) {
                RateResponse body = response.body();

                if (view != null && body != null) {
                    view.setCoins(response.body().data);
                    view.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(@NotNull Call<RateResponse> call, @NotNull Throwable t) {
                if (view != null) {
                    view.setRefreshing(false);
                }
                Timber.e(t);
            }
        });
    }

    @Override
    public void onRefresh() {
        getRate();
    }
}
