package Lise.whopaintit;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScoreDataBase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE_NAME = "ScoreDataBase";
    public static final String PKEY = "pkey";
    public static final String USER = "username";
    public static final String SCORE = "score";

    ScoreDataBase(Context context) {
        super(context, DATABASE_TABLE_NAME, null, DATABASE_VERSION); }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String DATABASE_TABLE_CREATE = "CREATE TABLE " + DATABASE_TABLE_NAME + " (" + PKEY + " INTEGER PRIMARY KEY," + USER + " TEXT," + SCORE + " INTEGER" + ");";
        db.execSQL(DATABASE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(String u, Integer s) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(USER, u);
        values.put(SCORE, s);
        db.insertOrThrow(DATABASE_TABLE_NAME, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }
}
