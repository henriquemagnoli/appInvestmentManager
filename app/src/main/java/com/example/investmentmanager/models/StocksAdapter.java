package com.example.investmentmanager.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.investmentmanager.R;

import java.text.NumberFormat;
import java.util.ArrayList;


public class StocksAdapter extends RecyclerView.Adapter<StocksViewHolder>
{
    private Context context;
    private ArrayList<Stocks> itens;

    public StocksAdapter(Context context, ArrayList<Stocks> itens){
        this.context = context;
        this.itens = itens;
    }

    @NonNull
    @Override
    public StocksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stocks_list, parent, false);
        return new StocksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StocksViewHolder holder, int position) {

        Stocks stocks = itens.get(position);

        NumberFormat nf = NumberFormat.getCurrencyInstance();
        String valorFormatado = nf.format(Double.parseDouble(stocks.getPrice()));

        holder.stockType.setText(stocks.getStockType());
        holder.stockCode.setText(stocks.getStockCode());
        holder.totalValue.setText("Valor total: " + valorFormatado);
        holder.amount.setText("Quantidade: " + stocks.getAmount());
    }

    @Override
    public int getItemCount() {
        return this.itens.size();
    }
}
