package com.example.readnewapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
        lvNews.setAdapter(new NewsAdapter(listData,this,db,2));
        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SavedActivity.this, DetailActivity.class);
                intent.putExtra("url",listData.get(position).getLINK());
                intent.putExtra("title",listData.get(position).getTITLE());
                intent.putExtra("description",listData.get(position).getDETAILS());
                startActivity(intent);
            }
        });
    }
}