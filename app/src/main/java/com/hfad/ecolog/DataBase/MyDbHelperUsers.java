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
public class MyDbHelperUsers extends SQLiteOpenHelper { //SQLiteOpenHelper - вспомогательный класс, который помогает управлять базой данных SQLite

    public MyDbHelperUsers(Context context){
        super(context, MyConstantsUsers.DB_NAME, null, MyConstantsUsers.DB_VERSION); //принимает контекст, имя БД, указатель на курсор и версию БД
    }
    @Override
    public void onCreate(SQLiteDatabase db) { //Создает таблицу в базе данных, структура таблицы хранится в константе MyConstants.TABLE_STRUCTURE
        db.execSQL(MyConstantsUsers.USERS_TABLE_STRUCTURE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//Обновление БД, сначала удаляем старую, затем создаем новую
        if (oldVersion == 1){
            //Прописываем, что будет если версия равна 1
        }

        if (oldVersion < 3){
            //Прописываем, что будет если версия 1 или 2
        }
        db.execSQL(MyConstantsUsers.DROP_TABLE);
        onCreate(db);
    }
}
