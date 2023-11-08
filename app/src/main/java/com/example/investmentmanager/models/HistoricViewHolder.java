package com.example.investmentmanager.models;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.investmentmanager.R;

public class HistoricViewHolder extends RecyclerView.ViewHolder
{
    TextView type;
    TextView stockCode;
    TextView stockMovimentationDate;
    TextView amount;
    TextView price;

    public HistoricViewHolder(@NonNull View itemView) {
        super(itemView);
        type = itemView.findViewById(R.id.tipoHistorico);
        stockCode = itemView.findViewById(R.id.codigoAtivoHistorico);
        stockMovimentationDate = itemView.findViewById(R.id.dataMovimentacaoHistorico);
        amount = itemView.findViewById(R.id.quantidadeHistorico);
        price = itemView.findViewById(R.id.valorTotalHistorico);
    }
}
