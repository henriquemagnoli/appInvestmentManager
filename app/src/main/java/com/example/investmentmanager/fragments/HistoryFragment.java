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
import com.example.investmentmanager.models.Historic;
import com.example.investmentmanager.models.HistoricAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Historic> itens;
    private RecyclerView recyclerView;

    public HistoryFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        try {
            historicData(view);
        }catch (JSONException ex){
            throw new RuntimeException(ex);
        }
    }

    private void historicData(View view) throws JSONException
    {
        itens = new ArrayList<Historic>();
        int userID = Integer.parseInt(MainActivity.userIdCache.getString("usuarioID", null));

        MainActivity.requestQueue.add((new VolleyRequests().sendRequestGET("/historico/" + userID, new IVolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                if(response.length() > 0)
                {
                    for(int i = 0; i < response.length(); i++)
                    {
                        JSONObject jsonData = response.getJSONObject("historico" + i);
                        itens.add(new Historic(jsonData.getString("tipo"),
                                               jsonData.getString("codigoativo"),
                                               jsonData.getString("datacompra"),
                                               jsonData.getString("datavenda"),
                                               jsonData.getString("quantidade"),
                                               jsonData.getString("preco"),
                                               jsonData.getString("outroscustos"),
                                               jsonData.getString("usuarioID")));
                    }

                    recyclerView = view.findViewById(R.id.historicRecyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(new HistoricAdapter(getContext(), itens));
                }
            }

            @Override
            public void onError(JSONObject response) throws JSONException {
                Helpers.alert(getContext(), "Erro", response.getString("message"), "Ok", true);
            }
        }, "historico")));
    }
}