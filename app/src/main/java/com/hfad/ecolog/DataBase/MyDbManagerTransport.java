package com.hfad.ecolog.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MyDbManagerTransport {
    private final Context context;
    private final MyDbHelperTransport myDbHelperTransport;
    private SQLiteDatabase db;


    public MyDbManagerTransport(Context context){
        this.context = context;
        myDbHelperTransport = new MyDbHelperTransport(context);
    }

    public void OpenDb(){
        db = myDbHelperTransport.getWritableDatabase();
    }

    public void CloseDb(){ //Закрываем базу данных
        myDbHelperTransport.close();
    }

    public void DestroyDb(){ //Полностью удаляем всю базу данных
        db.delete(MyConstantsTransport.TRANSPORT_TABLE_NAME, null, null); // полностью очистили таблицу
    }
}
