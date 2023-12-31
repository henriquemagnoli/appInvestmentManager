package com.example.investmentmanager.models;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.investmentmanager.R;
import com.google.android.material.button.MaterialButton;

public class StocksViewHolder extends RecyclerView.ViewHolder
{
    TextView stockType;
    TextView stockCode;
    TextView totalValue;
    TextView amount;
    MaterialButton btnExcluir;
    MaterialButton btnEditar;

    public StocksViewHolder(View itemView) {
        super(itemView);
        stockType = itemView.findViewById(R.id.tipoAtivoList);
        stockCode = itemView.findViewById(R.id.codigoAtivoList);
        totalValue = itemView.findViewById(R.id.valorTotalList);
        amount = itemView.findViewById(R.id.quantidadeList);
        btnExcluir = itemView.findViewById(R.id.btnExcluirAtivo);
        btnEditar = itemView.findViewById(R.id.btnAlterarAtivo);
    }
}
