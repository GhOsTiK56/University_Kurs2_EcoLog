/*
Класс MyDbHelper создает базу данных.
В этом классе реализованы 3 метода:
MyDbHelper - конструктор, при вызове создается БД, либо открывается старая.
onCreate - создает таблицу базы данных.
onUpgrade - обновляет БД.
*/
package com.hfad.ecolog.DataBase;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class MyDbHelper extends SQLiteOpenHelper { //SQLiteOpenHelper - вспомогательный класс, который помогает управлять базой данных SQLite

    public MyDbHelper(Context context){
        super(context, MyConstants.DB_NAME, null, MyConstants.DB_VERSION); //принимает контекст, имя БД, указатель на курсор и версию БД
    }
    @Override
    public void onCreate(SQLiteDatabase db) { //Создает таблицу в базе данных, структура таблицы хранится в константе MyConstants.TABLE_STRUCTURE
        db.execSQL(MyConstants.TABLE_STRUCTURE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//Обновление БД, сначала удаляем старую, затем создаем новую
        db.execSQL(MyConstants.DROP_TABLE);
        onCreate(db);
    }
}
