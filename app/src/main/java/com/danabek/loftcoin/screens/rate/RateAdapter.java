package com.danabek.loftcoin.screens.rate;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danabek.loftcoin.R;
import com.danabek.loftcoin.data.db.model.CoinEntity;
import com.danabek.loftcoin.data.db.model.QuoteEntity;
import com.danabek.loftcoin.data.prefs.Prefs;
import com.danabek.loftcoin.utils.CurrencyFormatter;
import com.danabek.loftcoin.utils.Fiat;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.RateViewHolder> {


    private Prefs prefs;
    private Listener listener;

    public RateAdapter(Prefs prefs) {
        this.prefs = prefs;
    }

    private List<CoinEntity> items = Collections.emptyList();

    public void setItems(List<CoinEntity> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rate, parent, false);
        return new RateViewHolder(view, prefs);
    }

    @Override
    public void onBindViewHolder(@NonNull RateViewHolder holder, int position) {
        holder.bind(items.get(position), position, listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private static int[] colors = {
            0xFFF5FF30,
            0xFFFFFFFF,
            0xFF2ABDF5,
            0xFFFF7416,
            0xFF534FFF,
    };

    interface Listener {
        void onRateLongClick(String symbol);
    }


    static class RateViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.symbol_text)
        TextView symbolText;

        @BindView(R.id.currency_name)
        TextView name;

        @BindView(R.id.price)
        TextView price;

        @BindView(R.id.percent_change)
        TextView percentChange;

        private Prefs prefs;

        private Context context;

        private Random random = new Random();

        private CurrencyFormatter currencyFormatter = new CurrencyFormatter();

        public RateViewHolder(@NonNull View itemView, Prefs prefs) {
            super(itemView);

            context = itemView.getContext();
            this.prefs = prefs;

            ButterKnife.bind(this, itemView);
        }

        public void bind(CoinEntity coin, int position, Listener listener) {

            bindName(coin);
            bindBackground(position);
            bindIcon(coin);
            bindPercentrage(coin);
            bindPrice(coin);
            bindListener(coin, listener);
        }


        private void bindName(CoinEntity coin) {
            name.setText(coin.symbol);
        }

        private void bindBackground(int position) {
            if (position % 2 == 0) {
                itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.rate_item_background_even));
            } else {
                itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.rate_item_background_odd));
            }
        }

        private void bindIcon(CoinEntity coin) {
            symbolText.setVisibility(View.VISIBLE);

            Drawable background = symbolText.getBackground();
            Drawable wrapped = DrawableCompat.wrap(background);
            DrawableCompat.setTint(wrapped, colors[random.nextInt(colors.length)]);

            symbolText.setText(String.valueOf(coin.symbol.charAt(0)));
        }

        private void bindPercentrage(CoinEntity coin) {
            Fiat fiat = prefs.getFiatCurrency();

            QuoteEntity quote = coin.getQuote(fiat);

            double percentChangeValue = quote.percentChange24h;

            percentChange.setText(context.getString(R.string.rate_item_percent_change, percentChangeValue));

            if (percentChangeValue >= 0) {
                percentChange.setTextColor(context.getResources().getColor(R.color.percent_change_up));
            } else {
                percentChange.setTextColor(context.getResources().getColor(R.color.percent_change_down));
            }

        }

        private void bindPrice(CoinEntity coin) {
            Fiat fiat = prefs.getFiatCurrency();
            QuoteEntity quote = coin.getQuote(fiat);
            String value = currencyFormatter.format(quote.price, false);

            price.setText(context.getString(R.string.currency_ammount, value, fiat.symbol));
        }

        private void bindListener(CoinEntity coin, Listener listener) {
            itemView.setOnLongClickListener(v -> {
                if (listener != null) {
                    listener.onRateLongClick(coin.symbol);
                }

                return true;
            });
        }
    }
}



