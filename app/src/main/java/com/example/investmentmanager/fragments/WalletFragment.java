package com.example.investmentmanager.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.investmentmanager.MainActivity;
import com.example.investmentmanager.R;
import com.example.investmentmanager.helpers.Helpers;
import com.example.investmentmanager.http.VolleyRequests;
import com.example.investmentmanager.interfaces.IVolleyCallback;
import com.example.investmentmanager.models.Stocks;
import com.example.investmentmanager.models.StocksAdapter;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Stocks> itens;
    private RecyclerView recyclerView;

    public WalletFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WalletFragment newInstance(String param1, String param2) {
        WalletFragment fragment = new WalletFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        try {
            stocksData(view);
        }catch (JSONException ex){
            throw new RuntimeException(ex);
        }
    }

    private void stocksData(View view) throws JSONException
    {
        itens = new ArrayList<Stocks>();
        int userID = Integer.parseInt(MainActivity.userIdCache.getString("usuarioID", null));

        String stockCode = String.valueOf(((TextInputLayout) view.findViewById(R.id.txtProcurarAtivo)).getEditText().getText());
        String path;

        if(!stockCode.equals(""))
            path = "/ativos/" + userID + "/" + stockCode;
        else
            path = "/ativos/" + userID;

        MainActivity.requestQueue.add((new VolleyRequests().sendRequestGET(path, new IVolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                if(response.length() > 0)
                {
                    //Double.parseDouble(jsonData.getString("outrosCustos")),

                    for (int i = 0; i < response.length(); i++)
                    {
                        JSONObject jsonData = response.getJSONObject("ativos" + i);
                        itens.add(new Stocks(jsonData.getString("tipoativo"),
                                             jsonData.getString("codigoativo"),
                                             jsonData.getString("quantidade"),
                                             jsonData.getString("preco"),
                                             "0",
                                             jsonData.getString("usuarioID")));
                    }

                    recyclerView = view.findViewById(R.id.stocksRecyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(new StocksAdapter(getContext(), itens));
                }
            }

            @Override
            public void onError(JSONObject response) throws JSONException {
                Helpers.alert(getContext(), "Erro", response.getString("message"), "Ok", true);
            }
        }, "ativos")));
    }
}