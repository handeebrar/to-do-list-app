package com.example.casperpc.todolistapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class LoginDatabaseAdapter {
    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;

    //yeni bir db oluşturuyoruz
    static final String DATABASE_CREATE = "CREATE TABLE " + " LOGIN " + "(  " + " ID " + " INTEGER PRIMARY KEY AUTOINCREMENT " + " , USERNAME text,PASSWORD text);";

    //db örneğini oluşturan değişken
    public static SQLiteDatabase db;

    //db'yi kullanan uygulamanın context'i
    private static Context context = null;

    //db open/upgrade helper
    private static DataBaseHelper dbHelper;

    public LoginDatabaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public LoginDatabaseAdapter open() throws SQLException {
        if (dbHelper == null) {
            Log.i("", "");
        }
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public void insertEntry(String userName, String password) {
        ContentValues newValues = new ContentValues();
        newValues.put("USERNAME", userName);
        newValues.put("PASSWORD", password);
        db.insert("LOGIN", null, newValues);
        db.close();

    }


    public static int getSingleEntry(String userName, String passwrod) {

        int count = 0;
        String countQuery = "SELECT  * FROM " + "LOGIN where USERNAME = " + "'" + userName + "'" + " and " + "PASSWORD = " + "'" + passwrod + "'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        } //cursoru editText'e girilen kullanıcı adı ve şifre doğruysa o kayda getiriyoruz.

        if (count != 0) {
            Toast.makeText(context, "Kullanıcı Girişi Başarılı", Toast.LENGTH_LONG).show();
        }
        return 0;

    }


}
