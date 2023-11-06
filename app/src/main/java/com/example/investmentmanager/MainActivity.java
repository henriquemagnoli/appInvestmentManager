package com.example.investmentmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.example.investmentmanager.fragments.HistoryFragment;
import com.example.investmentmanager.fragments.HomeFragment;
import com.example.investmentmanager.fragments.WalletFragment;
import com.example.investmentmanager.helpers.Helpers;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
{
    public static String urlApi = "http://localhost:8181";
    public static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(getApplicationContext());

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

    public void screenNavBar(View view)
    {
        setContentView(R.layout.main);

        BottomNavigationView bottomNav = findViewById(R.id.navBar);
        setFragment(new HomeFragment(), "Home", "Valor Aplicado", "R$ 1.000,00");

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if(id == R.id.btnHome)
                {
                    setFragment(new HomeFragment(), "Home", "Valor Aplicado", "R$ 1.000,00");
                    return true;
                }
                else if (id == R.id.btnWallet)
                {
                    setFragment(new WalletFragment(), "Carteira", "Valor Aplicado", "R$ 1.000,00");
                    return true;
                }
                else if (id == R.id.btnHistorico)
                {
                    setFragment(new HistoryFragment(), "Histórico", "Exibe o histórico de lançamentos de compras de ativos.", "");
                    return true;
                }

                return false;
            }
        });
    }

    void setFragment(Fragment fragment, String title, String subTitle, String apliedValue)
    {
        ((android.widget.TextView) findViewById(R.id.titulo)).setText(title);
        ((android.widget.TextView) findViewById(R.id.subTitulo)).setText(subTitle);
        ((android.widget.TextView) findViewById(R.id.valorAplicado)).setText(apliedValue);

        FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
        fragTrans.replace(R.id.main_frame, fragment);
        fragTrans.addToBackStack(null);
        fragTrans.commit();
    }

    public void screenAddStock(View view) {setContentView(R.layout.addstock_screen);}

    public void authLogin(View view) throws Exception
    {
        Button btnEntrar = findViewById(R.id.btnEntrar);

        String email = String.valueOf(((TextInputLayout) findViewById(R.id.txtEmail)).getEditText().getText());
        String password = String.valueOf(((TextInputLayout) findViewById(R.id.txtSenha)).getEditText().getText());

        if(!email.equals(""))
        {
            if(!Helpers.validateRegexEmail())
            {
                Helpers.alert(MainActivity.this, "Atenção", "Formato do e-mail inválido.", "Ok", true);
                return;
            }
        }
        else
        {
            Helpers.alert(MainActivity.this, "Atenção", "O campo do e-mail deve ser preenchido.", "Ok", true);
            return;
        }

        if(password.equals(""))
        {
            Helpers.alert(MainActivity.this, "Atenção", "O campo da senha deve ser preenchido.", "Ok", true);
            return;
        }

        screenNavBar(view);
    }
}