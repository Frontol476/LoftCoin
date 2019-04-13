package com.danabek.loftcoin.screens.converter;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danabek.loftcoin.App;
import com.danabek.loftcoin.R;
import com.danabek.loftcoin.data.db.Database;
import com.jakewharton.rxbinding3.widget.RxTextView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ConverterFragment extends Fragment {


    public ConverterFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.converter_toolbar)
    Toolbar toolbar;

    @BindView(R.id.source_currency)
    ViewGroup sourceCurrency;

    @BindView(R.id.source_amount)
    AppCompatEditText sourceAmount;

    @BindView(R.id.destination_currency)
    ViewGroup destinationCurrency;

    @BindView(R.id.destination_amount)
    TextView destinationAmount;

    TextView sourceCurrencySymbolText;
    TextView sourceCurrencySymbolname;
    TextView destinationCurrencySymbolText;
    TextView destinationCurrencySymbolName;

    private ConverterViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_converter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Database database = ((App) getActivity().getApplication()).getDatabase();
        viewModel = new ConverterViewModelImpl(savedInstanceState, database);

        toolbar.setTitle(R.string.converter_screen_title);
        sourceCurrencySymbolText = sourceCurrency.findViewById(R.id.symbol_text);
        sourceCurrencySymbolname = sourceCurrency.findViewById(R.id.currency_name);
        destinationCurrencySymbolText = destinationCurrency.findViewById(R.id.symbol_text);
        destinationCurrencySymbolName = destinationCurrency.findViewById(R.id.currency_name);

        initInputs();
        initOutputs();

    }

    private Random random = new Random();

    private static int[] colors = {
            0xFFF5FF30,
            0xFFFFFFFF,
            0xFF2ABDF5,
            0xFFFF7416,
            0xFF534FFF,
    };

    private void bindCurrency(String curr, TextView symbolText, TextView currencyName) {
        Drawable background = symbolText.getBackground();
        Drawable wrapped = DrawableCompat.wrap(background);
        DrawableCompat.setTint(wrapped, colors[random.nextInt(colors.length)]);

        symbolText.setText(String.valueOf(curr.charAt(0)));

        currencyName.setText(curr);
    }

    private void initInputs() {
        Disposable disposable1 = viewModel.sourceCurrency().subscribe(s ->
                bindCurrency(s, sourceCurrencySymbolText, sourceCurrencySymbolname)
        );
        Disposable disposable2 = viewModel.destionationCurrency().subscribe(s ->
                bindCurrency(s, destinationCurrencySymbolText, destinationCurrencySymbolName)
        );
        Disposable disposable3 = viewModel.destinationAmount().subscribe(s ->
                destinationAmount.setText(s)
        );
    }


    private void initOutputs() {
        Disposable disposable1 = RxTextView.afterTextChangeEvents(sourceAmount)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    viewModel.onSourceAmountChange(event.getEditable().toString());
                });
    }

}
