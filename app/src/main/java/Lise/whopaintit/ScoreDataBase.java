package Lise.whopaintit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

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

    public void insertData(Score s) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(USER, s.getUser());
        values.put(SCORE, s.getScore());
        db.insertOrThrow(DATABASE_TABLE_NAME, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    public List<Score> readDatas(){
            List<Score> scoreList = new ArrayList<Score>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_NAME;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Score score = new Score(cursor.getString(1),cursor.getInt(2));
                    scoreList.add(score);
                } while (cursor.moveToNext());
            }
            return scoreList;
    }

}
