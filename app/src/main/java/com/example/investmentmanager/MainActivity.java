package com.example.investmentmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

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
    public void screenHome(View view) {setContentView(R.layout.home_screen);}
    public void screenWallet(View view) {setContentView(R.layout.wallet_screen);}
    public void screenHistory(View view) {setContentView(R.layout.history_screen);}
    public void screenAddStock(View view) {setContentView(R.layout.addstock_screen);}
}