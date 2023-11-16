package com.example.investmentmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.investmentmanager.fragments.HistoryFragment;
import com.example.investmentmanager.fragments.HomeFragment;
import com.example.investmentmanager.fragments.WalletFragment;
import com.example.investmentmanager.helpers.Helpers;
import com.example.investmentmanager.http.VolleyRequests;
import com.example.investmentmanager.interfaces.IVolleyCallback;
import com.example.investmentmanager.models.Stocks;
import com.example.investmentmanager.models.StocksAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static String urlApi = "http://20.206.160.91:3334";
    public static RequestQueue requestQueue;

    // Variavel para armazenar o id do usuario
    public static SharedPreferences userIdCache;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(getApplicationContext());

        userIdCache = getSharedPreferences(getString(R.string.user_id_cache), Context.MODE_PRIVATE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.login_screen);
            }
        }, 2000);
    }

    public void screenLogin(View view) {setContentView(R.layout.login_screen);}

    public void screenSignUp(View view) {setContentView(R.layout.signup_screen);}

    public void screenAddStock(View view)
    {
        View screen = LayoutInflater.from(view.getContext()).inflate(R.layout.addstock_screen, null);

        TextInputLayout txtCodigoAtivo = (TextInputLayout) screen.findViewById(R.id.txtCodigoAtivo);
        MaterialToolbar btnFecharToolBar = (MaterialToolbar) screen.findViewById(R.id.topBarTransacao);
        Button btnSalvar = (Button) screen.findViewById(R.id.btnSalvar);
        Button btnFechar = (Button) screen.findViewById(R.id.btnCancelar);
        Button btnProcurar = (Button) screen.findViewById(R.id.btnProcurar);

        // Cria o AlertDialog com o layout personalizado
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(), R.style.myFullscreenAlertDialogStyle);
        builder.setView(screen);

        final AlertDialog modalAddStock = builder.create();

        btnProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStocksValueApi(screen, String.valueOf(txtCodigoAtivo.getEditText().getText()));
            }
        });

        btnFecharToolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modalAddStock.dismiss();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    salvarTransacao(screen);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                modalAddStock.dismiss();
            }
        });

        btnFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modalAddStock.dismiss();
            }
        });

        modalAddStock.show();
    }

    public void screenNavBar(View view) {
        setContentView(R.layout.main);

        BottomNavigationView bottomNav = findViewById(R.id.navBar);
        setFragment(new HomeFragment(), "Home", "Valor Aplicado", "R$ 1.000,00");

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.btnHome) {
                    setFragment(new HomeFragment(), "Home", "Valor Aplicado", "R$ 1.000,00");
                    return true;
                } else if (id == R.id.btnWallet) {
                    setFragment(new WalletFragment(), "Carteira", "Valor Aplicado", "R$ 1.000,00");
                    return true;
                } else if (id == R.id.btnHistorico) {
                    setFragment(new HistoryFragment(), "Histórico", "Exibe o histórico de lançamentos de compras de ativos.", "");
                    return true;
                }
                return false;
            }
        });
    }

    void setFragment(Fragment fragment, String title, String subTitle, String apliedValue) {
        ((android.widget.TextView) findViewById(R.id.titulo)).setText(title);
        ((android.widget.TextView) findViewById(R.id.subTitulo)).setText(subTitle);
        ((android.widget.TextView) findViewById(R.id.valorAplicado)).setText(apliedValue);

        FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
        fragTrans.replace(R.id.main_frame, fragment);
        fragTrans.addToBackStack(null);
        fragTrans.commit();
    }

    /*
    * =======================================================================
    * Funções de Usuario
    * =======================================================================
    */
    public void authLogin(View view) throws Exception {
        Button btnEntrar = findViewById(R.id.btnEntrar);

        String email = String.valueOf(((TextInputLayout) findViewById(R.id.txtEmail)).getEditText().getText());
        String password = String.valueOf(((TextInputLayout) findViewById(R.id.txtSenha)).getEditText().getText());

        if(!Helpers.validateLogin(MainActivity.this, email, password))
            return;

        requestQueue.add((new VolleyRequests()).sendRequestPOST("/login", Helpers.getLoginData(email, password), new IVolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                try {
                    if (response.getString("type").equals("Received"))
                    {
                        SharedPreferences.Editor editor = MainActivity.userIdCache.edit();

                        editor.putString("usuarioID", response.getString("idUsuario"));
                        editor.apply();

                        screenNavBar(view);
                    }
                    else
                    {
                        Helpers.alert(MainActivity.this, "Atenção", response.getString("message"), "Ok", true);
                    }
                }
                catch (Exception ex) {
                   Helpers.alert(MainActivity.this, "Atenção", response.getString("message"), "Ok", true);
                }

            }
            @Override
            public void onError(JSONObject response) throws JSONException {
                Helpers.alert(MainActivity.this, "Erro", response.getString("message"), "Ok", true);
                System.out.println(response);
            }
        }));
    }

    public void createAccount(View view) throws Exception
    {
        String fullName = String.valueOf(((TextInputLayout) findViewById(R.id.txtNomeCompleto)).getEditText().getText());
        String cellPhone = String.valueOf(((TextInputLayout) findViewById(R.id.txtTelefone)).getEditText().getText());
        String email = String.valueOf(((TextInputLayout) findViewById(R.id.txtEmail)).getEditText().getText());
        String cpf = String.valueOf(((TextInputLayout) findViewById(R.id.txtCpf)).getEditText().getText());
        String password = String.valueOf(((TextInputLayout) findViewById(R.id.txtSenha)).getEditText().getText());
        String date = String.valueOf(((TextInputLayout) findViewById(R.id.txtData)).getEditText().getText());

        if(!Helpers.validateSignUp(MainActivity.this, fullName, cellPhone, email, cpf, password, date))
            return;

        requestQueue.add((new VolleyRequests().sendRequestPOST("/usuarios", Helpers.getCreateAccountData(fullName, cellPhone, email, cpf, password, date), new IVolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                try {
                    if(response.getString("type").equals("Received"))
                        screenLogin(view);
                }
                catch (Exception ex){
                    Helpers.alert(MainActivity.this, "Atenção", response.getString("message"), "Ok", true);
                }
            }

            @Override
            public void onError(JSONObject response) throws JSONException {
                Helpers.alert(MainActivity.this, "Erro", response.getString("message"), "Ok", true);
            }
        })));
    }

    public void salvarTransacao(View view) throws Exception
    {
        String stockTransactionType;

        Boolean verifyCheckedTransaction = ((MaterialSwitch) view.findViewById(R.id.swtTipoTransacao)).isChecked();
        String stockType = String.valueOf(((TextInputLayout) view.findViewById(R.id.txtTipoAtivo)).getEditText().getText());;
        String stockCode = String.valueOf(((TextInputLayout) view.findViewById(R.id.txtCodigoAtivo)).getEditText().getText());
        String boughtDate = String.valueOf(((TextInputLayout) view.findViewById(R.id.txtDataCompra)).getEditText().getText());
        String amount = String.valueOf(((TextInputLayout) view.findViewById(R.id.txtQuantidade)).getEditText().getText());
        String price = String.valueOf(((TextInputLayout) view.findViewById(R.id.txtPreco)).getEditText().getText());
        String otherCosts = String.valueOf(((TextInputLayout) view.findViewById(R.id.txtOutrosCustos)).getEditText().getText());
        int usuarioID = Integer.parseInt(MainActivity.userIdCache.getString("usuarioID", null));

        if(verifyCheckedTransaction)
            stockTransactionType = "C";
        else
            stockTransactionType = "V";

        if(!Helpers.validateAddTransaction(MainActivity.this, stockType, stockCode, boughtDate, amount, price))
            return;

        requestQueue.add((new VolleyRequests().sendRequestPOST("/ativos/" + usuarioID, Helpers.getAddTransaction(stockTransactionType,
                                                                                                                      stockType,
                                                                                                                      stockCode,
                                                                                                                      boughtDate,
                                                                                                                      Integer.parseInt(amount),
                                                                                                                      Double.valueOf(price),
                                                                                                                      0,
                                                                                                                      //Double.valueOf(otherCosts),
                                                                                                                      usuarioID),
                                                                                                                      new IVolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                try {
                    if(response.getString("type").equals("Received"))
                    {
                        setContentView(R.layout.main);
                        ((BottomNavigationView) findViewById(R.id.navBar)).setSelectedItemId(R.id.btnWallet);
                        setFragment(new WalletFragment(), "Carteira", "Valor Aplicado", "R$ 1.000,00");
                    }

                }
                catch (Exception ex){
                    Helpers.alert(MainActivity.this, "Erro", response.getString("message"), "Ok", true);
                }
            }

            @Override
            public void onError(JSONObject response) throws JSONException {
                Helpers.alert(MainActivity.this, "Erro", response.getString("message"), "Ok", true);
            }
        })));
    }

    public void deleteStock(View view, int idStock)
    {
        requestQueue.add((new VolleyRequests().sendRequestDelete("/ativos/" + idStock, new IVolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                try
                {
                    Helpers.alert(view.getContext(), "Sucesso", "Ativo Excluido com sucesso.", "Ok", true);
                }
                catch (Exception ex) {
                    Helpers.alert(view.getContext(), "Atenção", "Ocorreu um problema ao excluir o ativo.", "Ok", true);
                }
            }

            @Override
            public void onError(JSONObject response) throws JSONException {
                Helpers.alert(view.getContext(), "Erro", "Internal server error", "Ok", true);
            }
        })));
    }

    public void getStockDataByID(View view, int idStock)
    {
        int userID = Integer.parseInt(MainActivity.userIdCache.getString("usuarioID", null));
        MainActivity.requestQueue.add((new VolleyRequests().sendRequestGET("/ativos/" + idStock + "/" + userID, new IVolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                if(response.length() > 0)
                {
                    JSONObject jsonData = response.getJSONObject("ativosID0");

                    View screen = LayoutInflater.from(view.getContext()).inflate(R.layout.addstock_screen, null);

                    // Formatacao de valor
                    NumberFormat nf = NumberFormat.getCurrencyInstance();

                    // Captura os id's de cada campo
                    TextInputLayout txtTipoAtivo = (TextInputLayout) screen.findViewById(R.id.txtTipoAtivo);
                    TextInputLayout txtCodigoAtivo = (TextInputLayout) screen.findViewById(R.id.txtCodigoAtivo);
                    TextInputLayout txtDataCompra = (TextInputLayout) screen.findViewById(R.id.txtDataCompra);
                    TextInputLayout txtQuantidade = (TextInputLayout) screen.findViewById(R.id.txtQuantidade);
                    TextInputLayout txtPreco = (TextInputLayout) screen.findViewById(R.id.txtPreco);
                    TextInputLayout txtOutrosCustos = (TextInputLayout) screen.findViewById(R.id.txtOutrosCustos);
                    TextView txvValorTotal = (TextView) screen.findViewById(R.id.txvValorTotal);
                    MaterialToolbar btnFecharToolBar = (MaterialToolbar) screen.findViewById(R.id.topBarTransacao);
                    Button btnAlterar = (Button) screen.findViewById(R.id.btnSalvar);
                    Button btnFechar = (Button) screen.findViewById(R.id.btnCancelar);

                    String dataCompra = Helpers.dataFormatter(jsonData.getString("createdAt"));

                    // Insere as informacões nos meus text fields
                    txtTipoAtivo.getEditText().setText(jsonData.getString("tipoativo"));
                    txtCodigoAtivo.getEditText().setText(jsonData.getString("codigoativo"));
                    txtDataCompra.getEditText().setText(dataCompra);
                    txtQuantidade.getEditText().setText(jsonData.getString("quantidade"));
                    txtPreco.getEditText().setText(jsonData.getString("preco"));
                    txtOutrosCustos.getEditText().setText(jsonData.getString("outroscustos"));
                    txvValorTotal.setText(nf.format(Double.parseDouble(jsonData.getString("preco"))));

                    txtTipoAtivo.setEnabled(false);
                    txtCodigoAtivo.setEnabled(false);
                    txtDataCompra.setEnabled(false);

                    // Cria o AlertDialog com o layout personalizado
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(), R.style.myFullscreenAlertDialogStyle);
                    builder.setView(screen);

                    final AlertDialog modalAddStock = builder.create();

                    btnFecharToolBar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            modalAddStock.dismiss();
                        }
                    });

                    btnAlterar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            updateStock(screen, idStock);
                        }
                    });

                    btnFechar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            modalAddStock.dismiss();
                        }
                    });

                    modalAddStock.show();
                }
            }

            @Override
            public void onError(JSONObject response) throws JSONException {
                Helpers.alert(MainActivity.this, "Erro", response.getString("message"), "Ok", true);
            }
        }, "ativosID")));
    }

    public void updateStock(View view, int idStock)
    {
        int userID = Integer.parseInt(MainActivity.userIdCache.getString("usuarioID", null));
        String stockTransactionType;
        Map<String, String> params = new HashMap<>();

        SimpleDateFormat formatoDataHoraAutal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.000Z", Locale.getDefault());
        String currentTime = formatoDataHoraAutal.format(new Date());

        Boolean verifyCheckedTransaction = ((MaterialSwitch) view.findViewById(R.id.swtTipoTransacao)).isChecked();
        String stockType = String.valueOf(((TextInputLayout) view.findViewById(R.id.txtTipoAtivo)).getEditText().getText());;
        String stockCode = String.valueOf(((TextInputLayout) view.findViewById(R.id.txtCodigoAtivo)).getEditText().getText());
        String boughtDate = String.valueOf(((TextInputLayout) view.findViewById(R.id.txtDataCompra)).getEditText().getText());
        String amount = String.valueOf(((TextInputLayout) view.findViewById(R.id.txtQuantidade)).getEditText().getText());
        String price = String.valueOf(((TextInputLayout) view.findViewById(R.id.txtPreco)).getEditText().getText());
        String otherCosts = String.valueOf(((TextInputLayout) view.findViewById(R.id.txtOutrosCustos)).getEditText().getText());

        if(verifyCheckedTransaction)
            stockTransactionType = "C";
        else
            stockTransactionType = "V";

        Date formatoData = null;
        try {
            formatoData = new SimpleDateFormat("dd/MM/yyyy").parse(boughtDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String newBoughtDate = new SimpleDateFormat("yyyy-MM-dd").format(formatoData);

        params.put("tipoativo", stockType);
        params.put("codigoativo", stockCode);

        if(stockTransactionType.equals("C"))
        {
            params.put("datacompra", currentTime);
            params.put("datavenda", "");
        }
        else if(stockTransactionType.equals("V"))
        {
            params.put("datacompra", newBoughtDate);
            params.put("datavenda", currentTime);
        }

        params.put("quantidade", amount);
        params.put("preco", price);
        params.put("outroscustos", otherCosts);
        params.put("tipo", stockTransactionType);
        params.put("usuarioID", String.valueOf(userID));

        requestQueue.add((new VolleyRequests().sendRequestPUT("/ativos/" + idStock, params, new IVolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                System.out.println(response);
            }

            @Override
            public void onError(JSONObject response) throws JSONException {
                System.out.println(response);
            }
        })));
    }

    public void getStocksValueApi(View view, String stockCode)
    {
        TextInputLayout txtStockCode = ((TextInputLayout) view.findViewById(R.id.txtCodigoAtivo));

        requestQueue.add((new VolleyRequests().sendRequestGET("/bolsavalores/" + stockCode, new IVolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                System.out.println(response);
            }

            @Override
            public void onError(JSONObject response) throws JSONException {
                //Helpers.alert(MainActivity.this, "Erro", response.getString("message"), "Ok", true);
                System.out.println(response);
            }

        }, "bolsavalores")));
    }
}