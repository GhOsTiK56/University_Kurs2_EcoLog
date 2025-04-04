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
package com.hfad.ecolog.DataBaseUsers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MyDbManagerUsers {
    private final Context context;
    private final MyDbHelperUsers myDbHelperUsers;
    private SQLiteDatabase db;

    public MyDbManagerUsers(Context context){
        this.context = context; //Передаем контекст
        myDbHelperUsers = new MyDbHelperUsers(context); //Создаем объект MyDbHelper для создания и управления базой данных
    }
    public void OpenDb(){ //Открываем базу данных
        db = myDbHelperUsers.getWritableDatabase(); // Функция для записи в базу данных
    }

    public void insertToDbUsers(String Email, String Password, String Name, String Phone){
        ContentValues cv = new ContentValues();
        cv.put(MyConstantsUsers.EMAIL, Email);
        cv.put(MyConstantsUsers.PASSWORD, Password);
        cv.put(MyConstantsUsers.NAME, Name);
        cv.put(MyConstantsUsers.PHONE, Phone);
        db.insert(MyConstantsUsers.USERS_TABLE_NAME, null, cv);
    }

    public void insertToDbEmissions(String UserId, float E_Communal, float E_Car, float E_Resolve){ // Добавляем в базу данных
        ContentValues cv = new ContentValues();
        cv.put(MyConstantsUsers.E_COMMUNAL, E_Communal);// затем положили данные в объект в поле E_COMMUNAL значение value
        cv.put(MyConstantsUsers.E_CAR, E_Car);
        cv.put(MyConstantsUsers.E_RESOLVE, E_Resolve);

        db.update(MyConstantsUsers.USERS_TABLE_NAME, cv, MyConstantsUsers._ID + " = ?", new String[]{UserId});
    }

    public boolean checkUserRegistrationExists(String Email){
        String[] columns = {MyConstantsUsers.EMAIL};
        String selection = MyConstantsUsers.EMAIL + " = ?";
        String[] selectionArgs = {Email};
        boolean exists = false;

        if (db != null && db.isOpen()){
            Cursor cursor = db.query(MyConstantsUsers.USERS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
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
        String[] projection = {MyConstantsUsers._ID};
        String selection = MyConstantsUsers.EMAIL + "=?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(MyConstantsUsers.USERS_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        String userId = null;
        if (cursor.moveToFirst()) {
            int userIdIndex = cursor.getColumnIndex(MyConstantsUsers._ID);
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
            String[] columns = {MyConstantsUsers.PASSWORD};
            String selection = MyConstantsUsers._ID + "=?";
            String[] selectionArgs = {userId};
            cursor = db.query(MyConstantsUsers.USERS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                int passwordIndex = cursor.getColumnIndex(MyConstantsUsers.PASSWORD);
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
        String[] columns = {MyConstantsUsers.E_RESOLVE};
        String selection = MyConstantsUsers._ID + "=?";
        String[] selectionArgs = {UserId};

        Cursor cursor = db.query(MyConstantsUsers.USERS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        float eResolveValue = 0.0F;

        if (cursor != null && cursor.moveToFirst()) {
            int eResolveIndex = cursor.getColumnIndex(MyConstantsUsers.E_RESOLVE);
            if (eResolveIndex != -1) {
                if (!cursor.isNull(eResolveIndex)) {
                    eResolveValue = cursor.getFloat(eResolveIndex);
                }
            }
            cursor.close();
        }

        return eResolveValue;
    }

    public float getECommunalForUser(String UserId){
        String[] columns = {MyConstantsUsers.E_COMMUNAL};
        String selections = MyConstantsUsers._ID + "=?";
        String[] selectionsArgs = {UserId};

        Cursor cursor = db.query(MyConstantsUsers.USERS_TABLE_NAME, columns, selections, selectionsArgs, null, null, null);
        float eCommunnalValue = 0.0F;

        if (cursor != null && cursor.moveToFirst()) {
            int eCommunalIndex = cursor.getColumnIndex(MyConstantsUsers.E_COMMUNAL);
            if (eCommunalIndex != -1) {
                if (!cursor.isNull(eCommunalIndex)) {
                    eCommunnalValue = cursor.getFloat(eCommunalIndex);
                }
            }
            cursor.close();
        }
        return eCommunnalValue;
    }

    public float getECarForUser(String UserId){
        String[] columns = {MyConstantsUsers.E_CAR};
        String selections = MyConstantsUsers._ID + "=?";
        String[] selectionsArgs = {UserId};

        Cursor cursor = db.query(MyConstantsUsers.USERS_TABLE_NAME, columns, selections, selectionsArgs, null, null, null);
        float eCarValue = 0.0F;

        if (cursor != null && cursor.moveToFirst()) {
            int eCarIndex = cursor.getColumnIndex(MyConstantsUsers.E_CAR);
            if (eCarIndex != -1) {
                if (!cursor.isNull(eCarIndex)) {
                    eCarValue = cursor.getFloat(eCarIndex);
                }
            }
            cursor.close();
        }
        return eCarValue;
    }

    public void CloseDb(){ //Закрываем базу данных
        myDbHelperUsers.close();
    }

    public void DestroyDb(){ //Полностью удаляем всю базу данных
        db.delete(MyConstantsUsers.USERS_TABLE_NAME, null, null); // полностью очистили таблицу
    }

    public String getUserName(String userId) {
        String userName = null;
        Cursor cursor = null;
        try {
            // Определение столбцов, которые вы хотите получить из запроса
            String[] columns = {MyConstantsUsers.NAME};
            // Условие для выборки записи с определенным идентификатором пользователя
            String selection = MyConstantsUsers._ID + "=?";
            // Аргументы для условия выборки (в данном случае идентификатор пользователя)
            String[] selectionArgs = {userId};
            // Выполнение запроса к базе данных
            cursor = db.query(MyConstantsUsers.USERS_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            // Проверка наличия результатов и переход к первой записи, если они есть
            if (cursor != null && cursor.moveToFirst()) {
                // Получение индекса столбца с именем пользователя
                int userNameIndex = cursor.getColumnIndex(MyConstantsUsers.NAME);
                // Проверка наличия такого столбца в результате запроса
                if (userNameIndex != -1) {
                    // Получение имени пользователя из текущей записи
                    userName = cursor.getString(userNameIndex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Обработка возможных исключений, например, ошибок при выполнении запроса
        } finally {
            // Закрытие курсора, чтобы освободить ресурсы
            if (cursor != null) {
                cursor.close();
            }
        }
        // Возвращение имени пользователя или null, если произошла ошибка
        return userName;
    }
}