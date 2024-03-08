package com.hfad.ecolog.DataBase;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyDbManager {
    private Context context;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase db;

    public MyDbManager(Context context){
        this.context = context;
        myDbHelper = new MyDbHelper(context);
    }
    public void openDb(){
        db = myDbHelper.getWritableDatabase(); // Функция для записи в базу данных
    }

    public void insertDb(float num){ // Добавляем в базу данных
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.VALUE, num);// затем положили данные в объект (num)
        db.insert(MyConstants.TABLE_NAME, null, cv);
    }
    public void updateOrInsertDb(int id, float num) {
        ContentValues cv = new ContentValues();
        cv.put(MyConstants._ID, id); // Необходимо вставить _id
        cv.put(MyConstants.VALUE, num);

        Cursor cursor = db.query(
                MyConstants.TABLE_NAME,
                null,
                MyConstants._ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor.getCount() > 0) {
            // Если запись существует, обновляем её
            updateDb(id, num);
        } else {
            // Если записи не существует, добавляем новую
            insertDb(num);
        }
        cursor.close();
    }

    public void updateDb(int id, float num) {
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.VALUE, num);
        String selection = MyConstants._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        int count = db.update(
                MyConstants.TABLE_NAME,
                cv,
                selection,
                selectionArgs);
        if (count == 0) {
            Toast.makeText(context, "Нет данных для обновления", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Данные обновлены", Toast.LENGTH_SHORT).show();
        }
    }

    public List<String> ReadFromDb(){ // Читаем базу данных
        List<String> tempList = new ArrayList<>();
        Cursor cursor = db.query(MyConstants.TABLE_NAME, null, null, null, null, null, null);

        while(cursor.moveToNext()){
            @SuppressLint("Range") String num = cursor.getString(cursor.getColumnIndex(MyConstants.VALUE));
            tempList.add(num);
        }
        cursor.close();
        return tempList;
    }

    public void CloseDb(){
        myDbHelper.close();
    }

    public void DestroyDb(){
        db.delete(MyConstants.TABLE_NAME, null, null); // полностью очистили таблицу
    }
}
