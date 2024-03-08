package com.hfad.ecolog.DataBase;

public class MyConstants {
    public static final String TABLE_NAME = "Emissions";
    public static final String _ID = "_id"; // id - первая колонока, сама заполняется
    public static final String VALUE = "num"; // Само значение
    public static final String DB_NAME = "my_db.db";
    public static final int  DB_VERSION = 1;// Нужна для перезапуска таблицы

    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + VALUE + " REAL)"; // Создание таблицы

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}