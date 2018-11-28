package com.example.casperpc.todolistapp;


import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {


    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //diskte hiçbir veritabanı bulunmadığında yardımcı sınıfın yeni bir tane oluşturması için çağrılır
    @Override
    public void onCreate(SQLiteDatabase _db) {
        _db.execSQL(LoginDatabaseAdapter.DATABASE_CREATE);
    }

    //sürüm uyuşmazlığı olduğunda güncelleme yapmak için kullanılır
    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {

        Log.w("TaskDBAdapter", "Versiyon" + _oldVersion + " 'dan " + _newVersion + " 'a yükseltiliyor.");
        //en basit şekilde güncellemeyi eski tabloyu silip yeni bir tane oluşturarak yapıyoruz.
        _db.execSQL("DROP TABLE IF EXIST " + "TEMPLATE");
        //yeni bir tane oluşturuyoruz
        onCreate(_db);
    }
}
