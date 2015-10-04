package com.blogger;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Database {
    public static final String KEY_POST = "post_id";
    public static final String KEY_POST_CONTENT = "post_content";

    public static final String DATABASE_NAME = "blogreader";
    public static final String DATABASE_TABLE = "blogposts";
    public static final int DATABASE_VERSION = 1;
    private final Context ourContext;
    private DbHelper ourhelper;
    private SQLiteDatabase ourDatabase;

    public Database(Context c) {
        ourContext = c;
    }

    public Database open() {
        ourhelper = new DbHelper(ourContext);
        ourDatabase = ourhelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourhelper.close();
    }

    public long createEntry(String post, String content) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST, post);
        cv.put(KEY_POST_CONTENT, content);
        return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }

    public String getData() {
        String columns[] = {KEY_POST, KEY_POST_CONTENT};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        String result = " ";

        int iPost = c.getColumnIndex(KEY_POST);
        int iContent = c.getColumnIndex(KEY_POST_CONTENT);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result = result + " " + c.getString(iPost) + " "
                    + c.getString(iContent) + "\n";
        }
        return result;
    }

    public String getContent(String company) {
        String columns[] = {KEY_POST, KEY_POST_CONTENT};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_POST + "= '" + company + "'", null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            String name = c.getString(1);
            return name;
        }
        return null;
    }

    public void UpdateEntry(String post, String content) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST, post);
        cv.put(KEY_POST_CONTENT, content);
        ourDatabase.update(DATABASE_TABLE, cv, KEY_POST + "= '" + post + "'", null);
    }

    public List<String> PostList() {
        String columns[] = {KEY_POST};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        List<String> result = new ArrayList<String>();
        String temp = "";
        int iPost = c.getColumnIndex(KEY_POST);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            temp = c.getString(iPost);
            result.add(temp);
        }

        return result;
    }

    public void DeleteEntry(String title) {
        ourDatabase.delete(DATABASE_TABLE, KEY_POST + "= '" + title + "'", null);
    }

    public boolean isTableExists() {
        String tableName = DATABASE_TABLE;
        if (tableName == null || ourDatabase == null || !ourDatabase.isOpen()) {
            return false;
        }

        Cursor cursor = ourDatabase.rawQuery(
                "SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[]{"table", tableName});
        if (!cursor.moveToFirst()) {
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    public boolean isEmpty(ContextWrapper context) {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        return !dbFile.exists();
    }

    public void AddEntry(String title, String post) {
        String columns[] = {KEY_POST, KEY_POST_CONTENT};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_POST + "= '" + title + "'", null, null, null, null);
        if (c.getCount() > 0)
            UpdateEntry(title, post);
        else
            createEntry(title, post);
    }

    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_POST
                    + " TEXT PRIMARY KEY NOT NULL, " + KEY_POST_CONTENT + " TEXT NOT NULL);");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE);
            onCreate(db);
        }

    }
}
