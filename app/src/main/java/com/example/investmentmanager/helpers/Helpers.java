package com.example.investmentmanager.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.view.ContextThemeWrapper;

import com.example.investmentmanager.R;

public class Helpers
{
    public static void alert(Context context, String title, String message, String btnText, Boolean cancelable)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.alertDialog));
        alert.setTitle(title)
             .setMessage("\n" + message + "\n")
             .setCancelable(cancelable)
             .setNegativeButton(btnText, null);

        alert.create().show();
    }

    public static boolean validateRegexEmail()
    {
        return true;
    }
}
