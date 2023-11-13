package com.example.investmentmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

    public void screenAddStock(View view) {setContentView(R.layout.addstock_screen);}

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

        if (!email.equals("")) {
            if (!Helpers.validateRegexEmail(email)) {
                Helpers.alert(MainActivity.this, "Atenção", "Formato do e-mail inválido.", "Ok", true);
                return;
            }
        } else {
            Helpers.alert(MainActivity.this, "Atenção", "O campo do e-mail deve ser preenchido.", "Ok", true);
            return;
        }

        if (password.equals("")) {
            Helpers.alert(MainActivity.this, "Atenção", "O campo da senha deve ser preenchido.", "Ok", true);
            return;
        }

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

        if(fullName.equals("")){
            Helpers.alert(MainActivity.this, "Atenção", "Campo de nome completo deve ser preenchido.", "Ok", true);
            return;
        }

        if(cellPhone.equals("")){
            Helpers.alert(MainActivity.this, "Atenção", "Campo do telefone deve ser preenchido.", "Ok", true);
            return;
        }

        if (!email.equals("")) {
            if (!Helpers.validateRegexEmail(email)) {
                Helpers.alert(MainActivity.this, "Atenção", "Formato do e-mail inválido.", "Ok", true);
                return;
            }
        }
        else {
            Helpers.alert(MainActivity.this, "Atenção", "O campo do e-mail deve ser preenchido.", "Ok", true);
            return;
        }

        if(!cpf.equals("")){
           if(!Helpers.validateCpf(cpf)){
               Helpers.alert(MainActivity.this, "Atenção", "Formato do cpf inválido.", "Ok", true);
               return;
           }
        }
        else{
            Helpers.alert(MainActivity.this, "Atenção", "Campo de nome completo deve ser preenchido.", "Ok", true);
            return;
        }

        if(password.equals("")){
            Helpers.alert(MainActivity.this, "Atenção", "Campo da senha deve ser preenchido.", "Ok", true);
            return;
        }

        if(date.equals("")){
            Helpers.alert(MainActivity.this, "Atenção", "Campo da data de nascimento deve ser preenchido.", "Ok", true);
            return;
        }

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
}