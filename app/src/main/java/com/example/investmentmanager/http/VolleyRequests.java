package com.example.investmentmanager.http;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.investmentmanager.MainActivity;
import com.example.investmentmanager.interfaces.IRequest;
import com.example.investmentmanager.interfaces.IVolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class VolleyRequests implements IRequest
{
    @Override
    public JsonObjectRequest sendRequestPOST(String path, JSONObject jsonObject, IVolleyCallback callback) throws UnsupportedOperationException {
        final String requestBody = jsonObject.toString();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                MainActivity.urlApi + path,
                null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        System.out.println("sucesso -> " + response);
                        try
                        {
                            callback.onSuccess(response);
                        }
                        catch (JSONException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError err)
                    {
                        try
                        {
                            System.out.println(new String(err.networkResponse.data, "UTF-8"));
                            JSONObject response = new JSONObject();
                            response.put("message", "Internal error.");
                            callback.onError(response);
                        }
                        catch (JSONException ex)
                        {
                            throw new RuntimeException(ex);
                        }
                        catch (UnsupportedEncodingException ex)
                        {
                            throw new RuntimeException(ex);
                        }
                    }
                })
                {
                    @Override
                    public String getBodyContentType()
                    {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody()
                    {
                        try
                        {
                            return requestBody.getBytes("utf-8");
                        }
                        catch (UnsupportedEncodingException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }

                };

        request.setTag("postRequest");
        return request;
    }

    @Override
    public JsonArrayRequest sendRequestGET(String path, IVolleyCallback callback, String objectName) throws UnsupportedOperationException {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                MainActivity.urlApi + path,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = new JSONObject();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                jsonObject.put(objectName + i, response.getJSONObject(i));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        try {
                            callback.onSuccess(jsonObject);
                        } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try
                        {
                            JSONObject response = new JSONObject();
                            response.put("message", "Internal error." + error.getMessage());
                            callback.onError(response);
                        }
                        catch (JSONException ex)
                        {
                            throw new RuntimeException(ex);
                        }
                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-type","application/application/json; charset=utf-8");
                headers.put("Accept", "application/json; charset=utf-8");
                return headers;
            }
        };

        request.setTag("getRequest");
        return request;
    }

    @Override
    public StringRequest sendRequestPUT(String path, Map params, IVolleyCallback callback) throws UnsupportedOperationException
    {
        StringRequest request = new StringRequest(
                Request.Method.PUT,
                MainActivity.urlApi + path,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonData = new JSONObject(response);
                            callback.onSuccess(jsonData);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try
                        {
                            System.out.println(new String(error.networkResponse.data, "UTF-8"));
                            JSONObject response = new JSONObject();
                            response.put("message", "Internal error.");
                            callback.onError(response);
                        }
                        catch (JSONException ex)
                        {
                            throw new RuntimeException(ex);
                        }
                        catch (UnsupportedEncodingException ex)
                        {
                            throw new RuntimeException(ex);
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        request.setTag("putRequest");
        return request;
    }

    @Override
    public StringRequest sendRequestDelete(String path, IVolleyCallback callback) throws UnsupportedOperationException
    {
        StringRequest request = new StringRequest(
                Request.Method.DELETE,
                MainActivity.urlApi + path,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            callback.onSuccess(jsonObject);
                        } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try
                        {
                            JSONObject response = new JSONObject();
                            response.put("message", "Internal error." + error.getMessage());
                            callback.onError(response);
                        }
                        catch (JSONException ex)
                        {
                            throw new RuntimeException(ex);
                        }
                    }
                }
        );
        /*{
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-type","application/application/json; charset=utf-8");
                headers.put("Accept", "application/json; charset=utf-8");
                return headers;
            }
        };*/

        request.setTag("deleteRequest");
        return request;
    }
}
