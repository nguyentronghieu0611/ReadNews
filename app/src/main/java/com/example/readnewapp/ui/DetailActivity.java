package com.example.readnewapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.readnewapp.R;
import com.example.readnewapp.config.NewsViewModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;

import static android.content.ContentValues.TAG;

public class DetailActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private LinearLayout layout;
    private NewsViewModel viewModel;
    private String url;
    private String title;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detail);
        layout = findViewById(R.id.layout_detail);
        mRequestQueue =  Volley.newRequestQueue(this);
        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        if(!viewModel.html.isEmpty() && getResources().getConfiguration().orientation==1){
            renderLayoutVertical(viewModel.html,title,description);
        }
        else if(!viewModel.html.isEmpty() && getResources().getConfiguration().orientation==2)
            renderLayouHorizontal(viewModel.html,title,description);
        else {
            getData(url);
        }
    }

    private void getData(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(String response) {
                viewModel.html = response;
                if(!viewModel.html.isEmpty() && getResources().getConfiguration().orientation==1){
                    renderLayoutVertical(viewModel.html,title,description);
                }
                else if(!viewModel.html.isEmpty() && getResources().getConfiguration().orientation==2)
                    renderLayouHorizontal(viewModel.html,title,description);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "StringRequest onErrorResponse: " + error.getMessage());
            }
        });
        mRequestQueue.add(stringRequest);
    }

    @SuppressLint("ResourceAsColor")
    private void renderLayoutVertical(String response, String title, String description){
        Document document = (Document) Jsoup.parse(response);

        TextView textViewTitle = new TextView(DetailActivity.this);
        textViewTitle.setText(title);
        textViewTitle.setTextSize(25);
        textViewTitle.setTypeface(null, Typeface.BOLD);
        textViewTitle.setTextColor(R.color.colorBlack);
        textViewTitle.setPadding(0,0,0,10);
        textViewTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.addView(textViewTitle);

        TextView textViewDesciption = new TextView(DetailActivity.this);
        textViewDesciption.setText(description);
        textViewDesciption.setTextSize(20);
        textViewDesciption.setPadding(0,0,0,10);
        textViewDesciption.setTypeface(null, Typeface.BOLD);
        textViewDesciption.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.addView(textViewDesciption);

        Element element = document.getElementsByClass("fck_detail").get(0);
        Elements elements = element.children();
        for(int i=0;i<elements.size();i++){
            if(elements.get(i).is(new Evaluator.Tag("p"))){
                TextView textView = new TextView(DetailActivity.this);
                textView.setTextSize(20);
                textView.setText(elements.get(i).text());
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                layout.addView(textView);
            }
            else if(elements.get(i).is(new Evaluator.Tag("figure"))){
                String url = elements.get(i).getElementsByTag("img").get(0).attr("src");
                ImageView imageView = new ImageView(DetailActivity.this);
                Glide.with(DetailActivity.this).load(url).apply(new RequestOptions().override(1366,768)).into(imageView);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                layout.addView(imageView);
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private void renderLayouHorizontal(String response, String title, String description){
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setWeightSum(3);
        LinearLayout layoutImage = new LinearLayout(DetailActivity.this);
        layoutImage.setOrientation(LinearLayout.VERTICAL);
        layoutImage.setPadding(5,0,5,0);
        layoutImage.setLayoutParams(new LinearLayout.LayoutParams(500,LinearLayout.LayoutParams.MATCH_PARENT,1));
        LinearLayout layoutText = new LinearLayout(DetailActivity.this);
        layoutText.setPadding(5,0,5,0);
        layoutText.setOrientation(LinearLayout.VERTICAL);
        layoutText.setLayoutParams(new LinearLayout.LayoutParams(500,LinearLayout.LayoutParams.MATCH_PARENT,2));
        layout.addView(layoutImage);
        layout.addView(layoutText);

        Document document = (Document) Jsoup.parse(response);

        TextView textViewTitle = new TextView(DetailActivity.this);
        textViewTitle.setText(title);
        textViewTitle.setTextSize(25);
        textViewTitle.setTypeface(null, Typeface.BOLD);
        textViewTitle.setTextColor(R.color.colorBlack);
        textViewTitle.setPadding(0,0,0,10);
        textViewTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        layoutText.addView(textViewTitle);

        TextView textViewDesciption = new TextView(DetailActivity.this);
        textViewDesciption.setText(description);
        textViewDesciption.setTextSize(20);
        textViewDesciption.setPadding(0,0,0,10);
        textViewDesciption.setTypeface(null, Typeface.BOLD);
        textViewDesciption.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        layoutText.addView(textViewDesciption);

        Element element = document.getElementsByClass("fck_detail").get(0);
        Elements elements = element.children();
        for(int i=0;i<elements.size();i++){
            if(elements.get(i).is(new Evaluator.Tag("p"))){
                TextView textView = new TextView(DetailActivity.this);
                textView.setTextSize(20);
                textView.setText(elements.get(i).text());
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                layoutText.addView(textView);
            }
            else if(elements.get(i).is(new Evaluator.Tag("figure"))){
                String url = elements.get(i).getElementsByTag("img").get(0).attr("src");
                ImageView imageView = new ImageView(DetailActivity.this);
                Glide.with(DetailActivity.this).load(url).apply(new RequestOptions().override(1366,768)).into(imageView);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                layoutImage.addView(imageView);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        int orientation = getResources().getConfiguration().orientation;
        Log.d("A",String.valueOf(orientation));
        super.onConfigurationChanged(newConfig);
    }
}