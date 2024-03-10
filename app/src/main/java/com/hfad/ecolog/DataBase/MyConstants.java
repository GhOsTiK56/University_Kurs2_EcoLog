/*
Класс MyConstants прописывает поля для базы данных.
В этом классе создается база данных и поля, а так же создаются константы с обозначениями.
*/
package com.hfad.ecolog.DataBase;

public class MyConstants {
    public static final String TABLE_NAME = "Emissions";
    public static final String _ID = "_id"; // id - первая колонка
    public static final String EMAIL = "Email";
    public static final String PASSWORD = "Password";
    public static final String NAME = "Name";
    public static final String PHONE = "Phone";
    public static final String E_COMMUNAL = "E_Communal";
    public static final String E_CAR = "E_Car";
    public static final String E_RESOLVE = "E_Resolve";
    public static final String DB_NAME = "my_db.db";
    public static final int  DB_VERSION = 1;// Нужна для перезапуска таблицы, вот здесь надо будет менять значение, чтобы пересоздать табл
    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + EMAIL + " TEXT," + PASSWORD + " TEXT," + NAME + " TEXT,"
            + PHONE + " INTEGER,"+ E_COMMUNAL + " REAL," + E_CAR + " REAL," + E_RESOLVE + " REAL)"; // Создание таблицы
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}