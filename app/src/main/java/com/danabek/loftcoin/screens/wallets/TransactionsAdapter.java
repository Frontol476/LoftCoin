package com.danabek.loftcoin.screens.wallets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.danabek.loftcoin.R;
import com.danabek.loftcoin.data.db.model.QuoteEntity;
import com.danabek.loftcoin.data.db.model.Transaction;
import com.danabek.loftcoin.data.prefs.Prefs;
import com.danabek.loftcoin.utils.CurrencyFormatter;
import com.danabek.loftcoin.utils.Fiat;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder> {
    private static final String TAG = "TransactionsAdapter";

    private List<Transaction> transactions = Collections.emptyList();

    private Prefs prefs;

    public TransactionsAdapter(Prefs prefs) {
        this.prefs = prefs;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view, prefs);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        holder.bind(transactions.get(position));
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.transaction_icon)
        ImageView icon;

        @BindView(R.id.transaction_crypto_amount)
        TextView cryptoAmount;

        @BindView(R.id.transaction_fiat_amount)
        TextView fiatAmount;

        @BindView(R.id.transaction_date)
        TextView date;

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        private CurrencyFormatter currencyFormatter = new CurrencyFormatter();

        private Prefs prefs;

        TransactionViewHolder(View itemView, Prefs prefs) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.prefs = prefs;
        }

        void bind(Transaction model) {
            bindIcon(model);
            bindCryptoAmount(model);
            bindFiatAmount(model);
            bindDate(model);
        }

        private void bindIcon(Transaction transaction) {
            if (transaction.amount < 0) {
                icon.setImageResource(R.drawable.ic_transaction_expense);
            } else {
                icon.setImageResource(R.drawable.ic_transaction_income);
            }
        }

        private void bindCryptoAmount(Transaction transaction) {
            if (transaction.amount < 0) {
                String value = "- " + currencyFormatter.format(Math.abs(transaction.amount), true);
                cryptoAmount.setText(itemView.getContext().getString(R.string.currency_ammount, value, transaction.coin.symbol));
            } else {
                String value = "+ " + currencyFormatter.format(Math.abs(transaction.amount), true);
                cryptoAmount.setText(itemView.getContext().getString(R.string.currency_ammount, value, transaction.coin.symbol));
            }
        }

        private void bindFiatAmount(Transaction transaction) {
            Fiat fiat = prefs.getFiatCurrency();
            QuoteEntity quote = transaction.coin.getQuote(fiat);

            if (transaction.amount < 0) {
                fiatAmount.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.transaction_expense));
                double amount = Math.abs(transaction.amount) * quote.price;
                String value = "- " + currencyFormatter.format(amount, false);
                fiatAmount.setText(itemView.getContext().getString(R.string.currency_ammount, value, fiat.symbol));
            } else {
                fiatAmount.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.transaction_income));
                double amount = Math.abs(transaction.amount) * quote.price;
                String value = "- " + currencyFormatter.format(amount, false);
                fiatAmount.setText(itemView.getContext().getString(R.string.currency_ammount, value, fiat.symbol));

            }
        }

        private void bindDate(Transaction transaction) {
            Date date = new Date(transaction.date);
            this.date.setText(dateFormatter.format(date));
        }
    }


}
