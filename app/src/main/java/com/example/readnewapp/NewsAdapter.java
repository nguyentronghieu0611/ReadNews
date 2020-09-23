package com.example.readnewapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private List<News> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public NewsAdapter(List<News> listData, Context context) {
        this.listData = listData;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_items_new, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.layoutLeft);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            holder.txtDetail = (TextView) convertView.findViewById(R.id.txtDetail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        News news = this.listData.get(position);

        holder.txtTitle.setText(news.getTITLE());
        holder.txtDetail.setText(news.getDETAILS());
        if(news.getIMG() != null)
            Glide.with(context).load(news.getIMG()).placeholder(R.mipmap.ic_launcher_round).into(holder.imageView);
        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDetail;
    }
}
