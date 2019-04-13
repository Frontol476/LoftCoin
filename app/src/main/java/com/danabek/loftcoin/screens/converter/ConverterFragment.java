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
import com.danabek.loftcoin.data.db.model.CoinEntity;
import com.danabek.loftcoin.screens.currencies.CurrenciesBottomSheet;
import com.jakewharton.rxbinding3.widget.RxTextView;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ConverterFragment extends Fragment {

    private static final String SOURCE_CURRENCY_BOTTOM_SHEET_TAG = "source_currency_bottom_sheet";
    private static final String DESTINATION_CURRENCY_BOTTOM_SHEET_TAG = "destination_currency_bottom_sheet";


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

    private CompositeDisposable disposable = new CompositeDisposable();


    public void onSaveInstanceState(@NonNull Bundle outState) {
        viewModel.saveState(outState);
        super.onSaveInstanceState(outState);
    }

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

        if (savedInstanceState == null) {
            sourceAmount.setText("1");
        }

        Fragment bottomSheetSource = getFragmentManager().findFragmentByTag(SOURCE_CURRENCY_BOTTOM_SHEET_TAG);
        if (bottomSheetSource != null) {
            ((CurrenciesBottomSheet) bottomSheetSource).setListener(sourceListener);
        }
        Fragment bottomSheetDestination = getFragmentManager().findFragmentByTag(DESTINATION_CURRENCY_BOTTOM_SHEET_TAG);
        if (bottomSheetDestination != null) {
            ((CurrenciesBottomSheet) bottomSheetDestination).setListener(destinationListner);
        }


        initInputs();
        initOutputs();

    }


    @Override
    public void onDestroyView() {
        disposable.dispose();
        super.onDestroyView();
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

        Disposable disposable4 = viewModel.selectSourceCurrency().subscribe(s ->
                showCurrenciesBottomSheet(true)
        );

        Disposable disposable5 = viewModel.selectDestinationCurrency().subscribe(s ->
                showCurrenciesBottomSheet(false)
        );


        disposable.add(disposable1);
        disposable.add(disposable2);
        disposable.add(disposable3);
        disposable.add(disposable4);
        disposable.add(disposable5);
    }

    private void showCurrenciesBottomSheet(boolean source) {
        CurrenciesBottomSheet bottomSheet = new CurrenciesBottomSheet();

        if (source) {
            bottomSheet.show(getFragmentManager(), SOURCE_CURRENCY_BOTTOM_SHEET_TAG);
            bottomSheet.setListener(sourceListener);
        } else {
            bottomSheet.show(getFragmentManager(), DESTINATION_CURRENCY_BOTTOM_SHEET_TAG);
            bottomSheet.setListener(destinationListner);
        }

    }

    private CurrenciesBottomSheetListener sourceListener = new CurrenciesBottomSheetListener() {
        @Override
        public void onCurrencySelected(CoinEntity coin) {
            viewModel.onSourceCurrencySelected(coin);
        }
    };

    private CurrenciesBottomSheetListener destinationListner = new CurrenciesBottomSheetListener() {
        @Override
        public void onCurrencySelected(CoinEntity coin) {
            viewModel.onDestinationCurrencySelected(coin);
        }
    };

    private void initOutputs() {
        Disposable disposable1 = RxTextView.afterTextChangeEvents(sourceAmount)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    viewModel.onSourceAmountChange(event.getEditable().toString());
                });

        sourceCurrency.setOnClickListener(v -> viewModel.onSourceCurrencyClick());
        destinationCurrency.setOnClickListener(v -> viewModel.onDestinationCurrencyClick());

        disposable.add(disposable1);
    }

}
