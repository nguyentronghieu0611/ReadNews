package com.example.readnewapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.readnewapp.R;
import com.example.readnewapp.adapter.NewsAdapter;
import com.example.readnewapp.config.NewsDatabase;
import com.example.readnewapp.model.News;

import java.util.ArrayList;
import java.util.List;

public class SavedActivity extends AppCompatActivity {
    private NewsDatabase db;
    private ListView lvNews;
    private List<News> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        lvNews = findViewById(R.id.lvNews);
        db = new NewsDatabase(this);
        listData = db.getContact();
        lvNews.setAdapter(new NewsAdapter(listData,this,db));
    }
}