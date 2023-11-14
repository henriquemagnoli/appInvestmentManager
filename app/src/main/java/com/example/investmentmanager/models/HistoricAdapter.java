package com.example.investmentmanager.models;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.investmentmanager.R;

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
        String dataMovimentacao = null;

        if(historic.getType() == "C")
        {
            holder.type.setTextColor(Color.parseColor("#1C9838"));
            holder.stockCode.setTextColor(Color.parseColor("#1C9838"));
            dataMovimentacao = new SimpleDateFormat("dd/MM/yyyy").format(historic.getBoughtDate());
            tipoMovimentacao = "Compra";
        }
        else if(historic.getType() == "V")
        {
            holder.type.setTextColor(Color.parseColor("#B52424"));
            holder.stockCode.setTextColor(Color.parseColor("#B52424"));
            dataMovimentacao = new SimpleDateFormat("dd/MM/yyyy").format(historic.getSoldDate());
            tipoMovimentacao = "Venda";
        }

        holder.stockCode.setText(historic.getStockCode() + " - ");
        holder.type.setText(tipoMovimentacao);
        holder.stockMovimentationDate.setText(dataMovimentacao);
        holder.amount.setText("Quantidade: " + historic.getAmount());
        holder.price.setText("Valor total: " + historic.getPrice());
    }

    @Override
    public int getItemCount() {
        return this.itens.size();
    }
}
