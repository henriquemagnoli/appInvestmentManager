package com.example.investmentmanager.interfaces;

import org.json.JSONException;
import org.json.JSONObject;
public interface IVolleyCallback
{
    void onSuccess(JSONObject response) throws JSONException;
    void onError(JSONObject response) throws JSONException;
}
