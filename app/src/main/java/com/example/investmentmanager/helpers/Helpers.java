package com.example.investmentmanager.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.view.ContextThemeWrapper;

import com.example.investmentmanager.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helpers
{
    public static JSONObject getLoginData(String email, String password) throws JSONException
    {
        JSONObject data = new JSONObject();
        data.put("email", email);
        data.put("password", password);
        return data;
    }

    public static JSONObject getCreateAccountData(String fullname, String cellphone, String email, String cpf, String password, String date) throws JSONException
    {
        JSONObject data = new JSONObject();
        data.put("nomecompleto", fullname);
        data.put("telefone", cellphone);
        data.put("email", email);
        data.put("cpf", cpf);
        data.put("senha", password);
        data.put("datanascimento", date);
        return data;
    }

    public static void alert(Context context, String title, String message, String btnText, Boolean cancelable)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.alertDialog));
        alert.setTitle(title)
             .setMessage("\n" + message + "\n")
             .setCancelable(cancelable)
             .setNegativeButton(btnText, null);

        alert.create().show();
    }

    public static boolean validateRegexEmail(String email)
    {
        String expression = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static boolean validateCpf(String cpf)
    {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (cpf.equals("00000000000") ||
                cpf.equals("11111111111") ||
                cpf.equals("22222222222") ||
                cpf.equals("33333333333") ||
                cpf.equals("44444444444") ||
                cpf.equals("55555555555") ||
                cpf.equals("66666666666") ||
                cpf.equals("77777777777") ||
                cpf.equals("88888888888") ||
                cpf.equals("99999999999") ||
                (cpf.length() != 11))
            return false;

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char) (r + 48);

            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char) (r + 48);

            if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
                return true;
            else
                return false;
        } catch (InputMismatchException err) {
            return false;
        }
    }


}
