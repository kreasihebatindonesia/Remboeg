package com.kreasihebatindonesia.remboeg.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kreasihebatindonesia.remboeg.models.DbHistoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IT DCM on 08/11/2017.
 */

public class SqliteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "remboeg";

    private static final String TABLE_HISTORY_SEARCH = "history";
    private static final String KEY_ID_HISTORY_SEARCH = "id";
    private static final String KEY_TITLE_HISTORY_SEARCH = "search";
    private static final String KEY_CREATED_AT_HISTORY_SEARCH = "created_at";

    private static final String CREATE_TABLE_HISTORY_SEARCH = "CREATE TABLE "
            + TABLE_HISTORY_SEARCH + "(" + KEY_ID_HISTORY_SEARCH + " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_TITLE_HISTORY_SEARCH
            + " TEXT," + KEY_CREATED_AT_HISTORY_SEARCH
            + " DATETIME" + ")";

    public SqliteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_HISTORY_SEARCH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY_SEARCH);
        onCreate(db);
    }

    public long createHistorySearch(DbHistoryModel historyModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE_HISTORY_SEARCH, historyModel.getTitle());

        return db.insert(TABLE_HISTORY_SEARCH, null, values);
    }

    public List<DbHistoryModel> getAllHistorySearch() {
        List<DbHistoryModel> history = new ArrayList<DbHistoryModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_HISTORY_SEARCH + " ORDER BY " + KEY_ID_HISTORY_SEARCH + " DESC LIMIT 5";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                DbHistoryModel td = new DbHistoryModel();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID_HISTORY_SEARCH))));
                td.setTitle((c.getString(c.getColumnIndex(KEY_TITLE_HISTORY_SEARCH))));

                history.add(td);
            } while (c.moveToNext());
        }

        return history;
    }

    public void deleteHistory(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HISTORY_SEARCH, KEY_ID_HISTORY_SEARCH + " = ?",
                new String[] { String.valueOf(id) });
    }

    public void clearHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HISTORY_SEARCH, null, null);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
