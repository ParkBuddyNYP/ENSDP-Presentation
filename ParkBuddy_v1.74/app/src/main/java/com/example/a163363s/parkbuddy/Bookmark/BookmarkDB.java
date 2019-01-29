package com.example.a163363s.parkbuddy.Bookmark;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BookmarkDB extends SQLiteOpenHelper {

    public BookmarkDB(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table Bookmarks (location_name Varchar, location_description Varchar, lat Real, lng Real)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists Bookmarks");
        onCreate(db);
    }

    public long addBookmark(String name, String description, double lat, double lng) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues obj = new ContentValues();
        obj.put("location_name", name);
        obj.put("location_description", description);
        obj.put("lat", lat);
        obj.put("lng", lng);
        return (db.insert("Bookmarks", null, obj));
    }

    public List<bookmarkModel> getBookmarksList() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<bookmarkModel> mList = new ArrayList<bookmarkModel>();
        Cursor data = db.rawQuery("SELECT * FROM Bookmarks", null);
        data.moveToFirst();
        for (int i = 0; i < data.getCount(); i++) {
            bookmarkModel obj = new bookmarkModel();
            obj.location_name = data.getString(0);
            obj.location_description = data.getString(1);
            obj.lat = data.getDouble(2);
            obj.lng = data.getDouble(3);
            mList.add(obj);
            data.moveToNext();
        }
        return mList;
    }

    public void deleteBookmark(double lat) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Bookmarks", "lat=?", new String[]{String.valueOf(lat)});
    }

    public void updateBookmark(String name, String description, double lat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues obj = new ContentValues();
        obj.put("location_name", name);
        obj.put("location_description", description);
        db.update("Bookmarks", obj, "lat=?", new String[]{String.valueOf(lat)});
    }
}
