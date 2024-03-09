/*
Класс MyDbManager управляет различными операциями в базе данных.
В этом классе реализованы 7 методов:
openDb - Открывает базу данных.
updateOrInsertDb - В данном случае обновляет существующий идентификатор id и перезаписывает число num.
insertDb - Добавляет в базу данных число num.
updateDb - Обновляет число num в базе данных.
ReadFromDb - Читает базу данных и возвращает в виде списка все данные num.
CloseDb - Закрывает базу данных.
DestroyDb - Уничтожает полностью всю базу данных со всеми значениями.
*/
package com.hfad.ecolog.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MyDbManager {
    private final Context context;
    private final MyDbHelper myDbHelper;
    private SQLiteDatabase db;

    public MyDbManager(Context context){
        this.context = context; //Передаем контекст
        myDbHelper = new MyDbHelper(context); //Создаем объект MyDbHelper для создания и управления базой данных
    }
    public void openDb(){ //Открываем базу данных
        db = myDbHelper.getWritableDatabase(); // Функция для записи в базу данных
    }

    public void insertToDbEmailPassword(String Email, String Password){
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.EMAIL, Email);
        cv.put(MyConstants.PASSWORD, Password);
        db.insert(MyConstants.TABLE_NAME, null, cv);
    }

    public void insertToDbEmissions(float E_Communal, float E_Car, float E_Resolve){ // Добавляем в базу данных
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.E_COMMUNAL, E_Communal);// затем положили данные в объект в поле E_COMMUNAL значение value
        cv.put(MyConstants.E_CAR, E_Car);
        cv.put(MyConstants.E_RESOLVE, E_Resolve);
        db.insert(MyConstants.TABLE_NAME, null, cv);
    }

/*    public void UpdateOrInsertDbEmissions(int id, float num) { //Обновляем существующую запись с указанным идентификатора id, иначе вставляем новую
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

        if (cursor.getCount() > 0) {// Если запись существует, обновляем её
            updateDb(id, num);
        } else {// Если записи не существует, добавляем новую
            insertToDbEmissions(num);
        }
        cursor.close();
    }

    public void updateDb(int id, float num) { //Обновляем запись в базе данных с указанием нужно поля
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

    public List<String> ReadFromDb(){ // Читаем базу данных и возвращаем в виде списка
        List<String> tempList = new ArrayList<>();
        Cursor cursor = db.query(MyConstants.TABLE_NAME, null, null, null, null, null, null);

        while(cursor.moveToNext()){
            @SuppressLint("Range") String num = cursor.getString(cursor.getColumnIndex(MyConstants.VALUE));
            tempList.add(num);
        }
        cursor.close();
        return tempList;
    }*/

    public void CloseDb(){ //Закрываем базу данных
        myDbHelper.close();
    }

    public void DestroyDb(){ //Полностью удаляем всю базу данных
        db.delete(MyConstants.TABLE_NAME, null, null); // полностью очистили таблицу
    }
}
