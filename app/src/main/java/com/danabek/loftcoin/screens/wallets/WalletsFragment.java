package com.danabek.loftcoin.screens.wallets;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.danabek.loftcoin.App;
import com.danabek.loftcoin.R;
import com.danabek.loftcoin.data.db.model.CoinEntity;
import com.danabek.loftcoin.data.prefs.Prefs;
import com.danabek.loftcoin.screens.converter.CurrenciesBottomSheetListener;
import com.danabek.loftcoin.screens.currencies.CurrenciesBottomSheet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletsFragment extends Fragment implements CurrenciesBottomSheetListener {

    public WalletsFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.wallets_toolbar)
    Toolbar toolbar;

    @BindView(R.id.transactions_recycler)
    RecyclerView transactionsRecycler;

    @BindView(R.id.wallets_pager)
    ViewPager walletsPager;

    @BindView(R.id.new_wallet)
    ViewGroup newWallet;

    private WalletsPagerAdapter walletsPagerAdapter;
    private WalletsViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallets, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(WalletsViewModelImpl.class);

        Prefs prefs = ((App) getActivity().getApplication()).getPrefs();
        walletsPagerAdapter = new WalletsPagerAdapter(prefs);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);


        walletsPager.setAdapter(walletsPagerAdapter);

        toolbar.setTitle(R.string.wallets_screen_title);
        toolbar.inflateMenu(R.menu.menu_wallets);

        int screenWidth = getScreenWidth();
        int walletItemWidth = getResources().getDimensionPixelOffset(R.dimen.item_wallet_width);
        int walletItemMargin = getResources().getDimensionPixelOffset(R.dimen.item_wallet_margin);
        int pageMargin = (screenWidth - walletItemWidth) - walletItemMargin;

        walletsPager.setPageMargin(-pageMargin);
        walletsPager.setOffscreenPageLimit(5);


        Fragment bottomSheet = getFragmentManager().findFragmentByTag(CurrenciesBottomSheet.TAG);
        if (bottomSheet != null) {
            ((CurrenciesBottomSheet) bottomSheet).setListener(this);
        }

        viewModel.getWallets();

        initOutputs();
        initInputs();
    }

    private void initOutputs() {
        newWallet.setOnClickListener(v -> {
            viewModel.onNewWalletClick();
        });

        toolbar.getMenu().findItem(R.id.menu_item_add_wallet).setOnMenuItemClickListener(item -> {
            viewModel.onNewWalletClick();
            return true;
        });
    }

    private void initInputs() {
        viewModel.selectCurrency().observe(this, o -> {
            showCurrenciesBottomSheet();
        });

        viewModel.newWalletVisible().observe(this, visible -> {
            newWallet.setVisibility(visible ? View.VISIBLE : View.GONE);
        });

        viewModel.walletsVisible().observe(this, visible ->
                walletsPager.setVisibility(visible ? View.VISIBLE : View.GONE));

        viewModel.wallets().observe(this, wallets -> {
            walletsPagerAdapter.setWallets(wallets);
        });
    }

    private void showCurrenciesBottomSheet() {
        CurrenciesBottomSheet bottomSheet = new CurrenciesBottomSheet();
        bottomSheet.show(getFragmentManager(), CurrenciesBottomSheet.TAG);
        bottomSheet.setListener(this);

    }

    @Override
    public void onCurrencySelected(CoinEntity coin) {
        viewModel.onCurrencySelected(coin);
    }

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return width;
    }
}
