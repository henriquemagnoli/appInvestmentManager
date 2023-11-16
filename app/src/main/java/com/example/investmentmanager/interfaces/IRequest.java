package com.example.investmentmanager.interfaces;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.Map;

public interface IRequest
{
    JsonObjectRequest sendRequestPOST(String path, JSONObject jsonObject, IVolleyCallback callback) throws UnsupportedOperationException;
    JsonArrayRequest sendRequestGET(String path, IVolleyCallback callback, String objectName) throws UnsupportedOperationException;

    StringRequest sendRequestPUT(String path, Map params, IVolleyCallback callback) throws UnsupportedOperationException;
    StringRequest sendRequestDelete(String path, IVolleyCallback callback) throws  UnsupportedOperationException;
}
