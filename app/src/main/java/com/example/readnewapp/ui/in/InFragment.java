package com.example.readnewapp.ui.in;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
import com.example.readnewapp.DetailActivity;
import com.example.readnewapp.News;
import com.example.readnewapp.NewsAdapter;
import com.example.readnewapp.R;
import com.example.readnewapp.Utils;
import com.example.readnewapp.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class InFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RequestQueue mRequestQueue;
    private ListView lvNews;
    private List<News> listNews = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_in, container, false);
        lvNews = root.findViewById(R.id.lvNews);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        mRequestQueue =  Volley.newRequestQueue(getContext());
        bindEvent();
        getData();
        return root;
    }

    private void bindEvent(){
        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("url",listNews.get(position).getLINK());
                intent.putExtra("title",listNews.get(position).getTITLE());
                intent.putExtra("description",listNews.get(position).getDETAILS());
                startActivity(intent);
            }
        });
    }

    private void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getString(R.string.url_in), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = Utils.fixEncoding(response);
                Log.e(TAG, "StringRequest onResponse: " + response);
                lvNews.setAdapter(new NewsAdapter(listNews = Utils.parseXml(response),getContext()));
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