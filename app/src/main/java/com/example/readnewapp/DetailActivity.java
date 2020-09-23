package com.example.readnewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;

import static android.content.ContentValues.TAG;

public class DetailActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detail);
        layout = findViewById(R.id.layout_detail);
        mRequestQueue =  Volley.newRequestQueue(this);
        getData(getIntent().getStringExtra("url"),getIntent().getStringExtra("title"),getIntent().getStringExtra("description"));
    }

    private void getData(String url, final String title, final String description){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(String response) {
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "StringRequest onErrorResponse: " + error.getMessage());
            }
        });
        mRequestQueue.add(stringRequest);
    }

}