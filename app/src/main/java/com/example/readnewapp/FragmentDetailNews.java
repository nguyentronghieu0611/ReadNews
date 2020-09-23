package com.example.readnewapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.readnewapp.ui.home.HomeViewModel;

import static android.content.ContentValues.TAG;

public class FragmentDetailNews extends Fragment {
    private RequestQueue mRequestQueue;
    private String url;

    public FragmentDetailNews(String url){
        this.url=url;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mRequestQueue =  Volley.newRequestQueue(getContext());
        getData();
        return root;
    }

    private void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = Utils.fixEncoding(response);
                Log.e(TAG, "StringRequest onResponse: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "StringRequest onErrorResponse: " + error.getMessage());
            }
        });
        mRequestQueue.add(stringRequest);
    }
}
