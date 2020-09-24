package com.example.readnewapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.readnewapp.R;
import com.example.readnewapp.config.NewsDatabase;
import com.example.readnewapp.model.News;

import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private List<News> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private NewsDatabase db;
    private int type;

    public NewsAdapter(List<News> listData, Context context, NewsDatabase db, int type) {
        this.listData = listData;
        this.context = context;
        this.db = db;
        this.type = type;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_items_new, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.layoutLeft);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            holder.txtDetail = (TextView) convertView.findViewById(R.id.txtDetail);
            holder.imgSave = (ImageView) convertView.findViewById(R.id.btnSave);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        News news = this.listData.get(position);
        if (type == 2) {
            holder.imgDelete.setVisibility(View.VISIBLE);
            holder.imgSave.setVisibility(View.GONE);
        } else {
            holder.imgDelete.setVisibility(View.GONE);
            holder.imgSave.setVisibility(View.VISIBLE);
        }
        holder.txtTitle.setText(news.getTITLE());
        holder.txtDetail.setText(news.getDETAILS());
        if (news.getIMG() != null)
            Glide.with(context).load(news.getIMG()).placeholder(R.mipmap.ic_launcher_round).into(holder.imageView);
        holder.imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long i = db.insertContact(listData.get(position));
                if (i > 0)
                    Toast.makeText(context, "Lưu thành công!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Đã tồn tại tin tức!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = db.deleteNews(listData.get(position));
                if (a > 0) {
                    listData.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context,"Xóa thành công!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDetail;
        ImageView imgSave, imgDelete;
    }
}
