package com.hfad.ecolog.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelperTransport extends SQLiteOpenHelper {

    public MyDbHelperTransport(Context context){
        super(context, MyConstantsTransport.DB_NAME, null, MyConstantsTransport.DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MyConstantsTransport.TRANSPORT_TABLE_STRUCTURE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1){
            //Прописываем, что будет если версия равна 1
        }

        if (oldVersion < 3){
            //Прописываем, что будет если версия 1 или 2
        }
        db.execSQL(MyConstantsTransport.DROP_TABLE);
        onCreate(db);
    }
}
