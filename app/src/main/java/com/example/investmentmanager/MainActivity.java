package com.example.investmentmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.investmentmanager.fragments.HistoryFragment;
import com.example.investmentmanager.fragments.HomeFragment;
import com.example.investmentmanager.fragments.WalletFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        /*TextView titulo = findViewById(R.id.titulo);
        TextView subTitulo = findViewById(R.id.subTitulo);
        TextView valorAplicado = findViewById(R.id.valorAplicado);*/

        setContentView(R.layout.main);

        BottomNavigationView bottomNav = findViewById(R.id.navBar);
        setFragment(new HomeFragment());

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if(id == R.id.btnHome)
                {
                    setFragment(new HomeFragment());
                    /*titulo.setText("Home");
                    subTitulo.setText("Valor aplicado");
                    valorAplicado.setText("R$ 1.000,00");*/

                    return true;
                }
                else if (id == R.id.btnWallet)
                {
                    setFragment(new WalletFragment());
                    /*titulo.setText("Carteira");
                    subTitulo.setText("Valor aplicado");
                    valorAplicado.setText("R$ 1.000,00");*/

                    return true;
                }
                else if (id == R.id.btnHistorico)
                {
                    setFragment(new HistoryFragment());
                    /*titulo.setText("Histórico");
                    subTitulo.setText("Exibe o histórico de lançamentos de compras de ativos");
                    valorAplicado.setText("");*/

                    return true;
                }

                return false;
            }
        });
    }

    void setFragment(Fragment fragment)
    {
        FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
        fragTrans.replace(R.id.main_frame, fragment);
        fragTrans.addToBackStack(null);
        fragTrans.commit();
    }

    public void screenAddStock(View view) {setContentView(R.layout.addstock_screen);}
}