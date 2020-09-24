package com.example.readnewapp.config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.readnewapp.model.News;

import java.util.ArrayList;
import java.util.List;

public class NewsDatabase extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contact.db";

    public NewsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + "tblNews" + " (" +
                "id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title" + " TEXT UNIQUE NOT NULL, " +
                "detail" + " TEXT NOT NULL, " +
                "image" + " TEXT NOT NULL, " +
                "url" + " TEXT)");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long insertContact(News news){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", news.getTITLE());
        values.put("detail", news.getDETAILS());
        values.put("image", news.getIMG());
        values.put("url", news.getLINK());
        return sqLiteDatabase.insert("tblNews", null, values);
    }

    public int updateContact(News news){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", news.getTITLE());
        values.put("detail", news.getDETAILS());
        values.put("image", news.getIMG());
        values.put("url", news.getLINK());
        return sqLiteDatabase.update("tblNews",values,"id = ?",new String[]{news.getID().toString()});
    }

    public int deleteNews(News news){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("tblNews", "id = ?", new String[]{news.getID().toString()});
    }

    public int deleteAll(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("tblNews",null,null);
    }

    public List<News> getContact(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("Select * from tblNews", null);
        List<News> result = new ArrayList<>();
        News news=null;
        if(c.moveToFirst()){
            do{
                int id = c.getInt(0);
                String title = c.getString(1);
                String detail = c.getString(2);
                String image = c.getString(3);
                String url = c.getString(4);
                news = new News();
                news.setID(id);
                news.setTITLE(title);
                news.setDETAILS(detail);
                news.setIMG(image);
                news.setLINK(url);
                result.add(news);
            }while (c.moveToNext());
        }
        return result;
    }

    public News getContactDetail(String idInput){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("Select * from tblNews where id = ?", new String[]{idInput});
        News contact = null;
        if(c.moveToFirst()){
            do{
                int id = c.getInt(0);
                String phonenumber = c.getString(1);
                String name = c.getString(2);
                String email = c.getString(3);
                byte[] image = c.getBlob(4);
//                contact = new News(id,phonenumber,name,email,image);
            }while (c.moveToNext());
        }
        return contact;
    }
}
