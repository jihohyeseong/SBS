package com.example.bookmarket.mypage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelperMemo extends SQLiteOpenHelper {

    static final String DATABASE_NAME =  "Memo.db"; //DB 이름
    static  final  String TABLE_NAME = "MemoTABLE"; //테이블 이름
    static  final  int DATABASE_VERSION = 1; // 데이터베이스 버전

    static  final String KEY_ID = "id";
    static  final String KEY_DATE = "date";
    static  final String KEY_TITLE = "title";
    static  final String KEY_CONTENT = "content";


    public DatabaseHelperMemo(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { // 테이블 생성

        String query =  ("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_DATE + " TEXT,"
                + KEY_TITLE + " TEXT,"
                + KEY_CONTENT + " TEXT " + ")");

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { //테이블 갱신

        String query = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);

    }

    boolean insertData(String date, String title, String content){ //삽입
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_DATE,date);
        cv.put(KEY_TITLE,title);
        cv.put(KEY_CONTENT,content);

        long result = db.insert(TABLE_NAME,null,cv);
        db.close();
        if (result != -1) return true;
        else return false;
    }


        public Cursor readData(){ //모든 데이터 조회
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " +TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        // db.close();
        return cursor;
    }




    public boolean updateData(String date, String  title, String content, int id) { //수정
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        // cv.put("phone", phone);
        cv.put(KEY_DATE, date);;
        cv.put(KEY_TITLE, title);
        cv.put(KEY_CONTENT, content);
        int result = db.update(TABLE_NAME,
                cv,
                KEY_ID+"=? ",
                new String[]{ String.valueOf(id) });

        db.close();
        if (result != -1) return true;
        else return false;

    }


    boolean deleteData(int userid){ //삭제
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME,    KEY_ID+"=? ", new String[]{ String.valueOf(userid) });
        db.close();
        if (result != -1) return true;
        else return false;
    }

    public void deleteAllData() //모든 데이터 삭제
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    public Cursor searchReadData(String content){ //테이블의 content 필드에 저장된 내용에서 검색어 조회
        SQLiteDatabase db = this.getReadableDatabase();
         //  Cursor cursor = db.rawQuery(query, null);
        Cursor cursor = db.query(TABLE_NAME, null, KEY_CONTENT+" LIKE ?", new String[]{"%"+ content +"%"},
                null, null, null );
        return cursor;

    }
}
