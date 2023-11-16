package com.example.investmentmanager.models;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.investmentmanager.R;
import com.example.investmentmanager.helpers.Helpers;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoricAdapter extends RecyclerView.Adapter<HistoricViewHolder>
{
    private Context context;
    private ArrayList<Historic> itens;

    public HistoricAdapter(Context context, ArrayList<Historic> itens){
        this.context = context;
        this.itens = itens;
    }

    @NonNull
    @Override
    public HistoricViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.historic_list, parent, false);
        return new HistoricViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricViewHolder holder, int position) {

        Historic historic = itens.get(position);

        // Se o tipo for C então coloco a string COMPRA, se for V coloco string VENDA
        String tipoMovimentacao = "";
        String dataMovimentacao = "";
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        String valorFormatado = nf.format(Double.parseDouble(historic.getPrice()));

        if(historic.getType().equals("C"))
        {
            holder.type.setTextColor(Color.parseColor("#1C9838"));
            holder.stockCode.setTextColor(Color.parseColor("#1C9838"));

            if(historic.getBoughtDate() != null)
                dataMovimentacao = Helpers.dataFormatter(historic.getBoughtDate());
            else
                dataMovimentacao = "Ocorreu um erro ao carregar.";

            tipoMovimentacao = "Compra";
        }
        else if(historic.getType().equals("V"))
        {
            holder.type.setTextColor(Color.parseColor("#B52424"));
            holder.stockCode.setTextColor(Color.parseColor("#B52424"));

            if(historic.getSoldDate() != null)
                dataMovimentacao = Helpers.dataFormatter(historic.getSoldDate());
            else
                dataMovimentacao = "Ocorreu um erro ao carregar.";

            tipoMovimentacao = "Venda";
        }

        holder.stockCode.setText(historic.getStockCode() + " - ");
        holder.type.setText(tipoMovimentacao);
        holder.stockMovimentationDate.setText(dataMovimentacao);
        holder.amount.setText("Quantidade: " + historic.getAmount());
        holder.price.setText("Valor total: " + valorFormatado);
    }

    @Override
    public int getItemCount() {
        return this.itens.size();
    }
}
