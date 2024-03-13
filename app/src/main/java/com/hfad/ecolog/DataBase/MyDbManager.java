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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MyDbManager {
    private final Context context;
    private final MyDbHelper myDbHelper;
    private SQLiteDatabase db;

    public MyDbManager(Context context){
        this.context = context; //Передаем контекст
        myDbHelper = new MyDbHelper(context); //Создаем объект MyDbHelper для создания и управления базой данных
    }
    public void OpenDb(){ //Открываем базу данных
        db = myDbHelper.getWritableDatabase(); // Функция для записи в базу данных
    }

    public void insertToDbUsers(String Email, String Password, String Name, String Phone){
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.EMAIL, Email);
        cv.put(MyConstants.PASSWORD, Password);
        cv.put(MyConstants.NAME, Name);
        cv.put(MyConstants.PHONE, Phone);
        db.insert(MyConstants.TABLE_NAME, null, cv);
    }

    public void insertToDbEmissions(String UserId, float E_Communal, float E_Car, float E_Resolve){ // Добавляем в базу данных
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.E_COMMUNAL, E_Communal);// затем положили данные в объект в поле E_COMMUNAL значение value
        cv.put(MyConstants.E_CAR, E_Car);
        cv.put(MyConstants.E_RESOLVE, E_Resolve);

        db.update(MyConstants.TABLE_NAME, cv, MyConstants._ID + " = ?", new String[]{UserId});
    }

    public boolean checkUserRegistrationExists(String Email){
        String[] columns = {MyConstants.EMAIL};
        String selection = MyConstants.EMAIL + " = ?";
        String[] selectionArgs = {Email};
        boolean exists = false;

        if (db != null && db.isOpen()){
            Cursor cursor = db.query(MyConstants.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            if (cursor != null) {
                exists = cursor.moveToFirst(); // Проверяем, есть ли какие-либо результаты в курсоре
                cursor.close();
            }
        }
        else {
            Log.e("MyDbManager", "Database is not opened or initialized properly");
        }
        return exists;
    }

    public String getUserIdForEmail(String email) {
        String[] projection = {MyConstants._ID};
        String selection = MyConstants.EMAIL + "=?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(MyConstants.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        String userId = null;
        if (cursor.moveToFirst()) {
            int userIdIndex = cursor.getColumnIndex(MyConstants._ID);
            if (userIdIndex != -1) {
                userId = cursor.getString(userIdIndex);
            }
        }
        cursor.close();
        return userId;
    }

    public String getPasswordForUserId(String userId) {
        String password = null;
        Cursor cursor = null;
        try {
            String[] columns = {MyConstants.PASSWORD};
            String selection = MyConstants._ID + "=?";
            String[] selectionArgs = {userId};
            cursor = db.query(MyConstants.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                int passwordIndex = cursor.getColumnIndex(MyConstants.PASSWORD);
                if (passwordIndex != -1) {
                    password = cursor.getString(passwordIndex);
                }
            }
            // Добавляем лог о выполненном запросе и полученном пароле
            Log.d("Database", "getPasswordForUserId query successful for userId: " + userId + ", password: " + password);
        } catch (Exception e) {
            e.printStackTrace();
            // Добавляем лог о возникшем исключении
            Log.e("Database", "Error in getPasswordForUserId: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            // Не закрываем базу данных здесь, так как она не открывается в этом методе
        }
        return password;
    }


    public float getEResolveForUser(String UserId) {
        String[] columns = {MyConstants.E_RESOLVE};
        String selection = MyConstants._ID + "=?";
        String[] selectionArgs = {UserId};

        Cursor cursor = db.query(MyConstants.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        float eResolveValue = 0.0F;

        if (cursor != null && cursor.moveToFirst()) {
            int eResolveIndex = cursor.getColumnIndex(MyConstants.E_RESOLVE);
            if (eResolveIndex != -1) {
                if (!cursor.isNull(eResolveIndex)) {
                    eResolveValue = cursor.getFloat(eResolveIndex);
                }
            }
            cursor.close();
        }

        return eResolveValue;
    }



    public void CloseDb(){ //Закрываем базу данных
        myDbHelper.close();
    }

    public void DestroyDb(){ //Полностью удаляем всю базу данных
        db.delete(MyConstants.TABLE_NAME, null, null); // полностью очистили таблицу
    }
}
