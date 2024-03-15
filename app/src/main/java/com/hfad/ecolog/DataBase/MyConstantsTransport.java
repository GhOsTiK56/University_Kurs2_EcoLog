package com.hfad.ecolog.DataBase;

public class MyConstantsTransport {
    public static final String TRANSPORT_TABLE_NAME = "Transport";
    public static final String _ID = "_id"; // id - первая колонка
    public static final String USER_ID = "User_id";
    public static final String DATE = "Date";
    public static final String DISTANCE = "Distance";
    public static final String DB_NAME = "my_db_transport.db";
    public static final int  DB_VERSION = 1;// Нужна для перезапуска таблицы, вот здесь надо будет менять значение, чтобы пересоздать табл

    public static final String TRANSPORT_TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TRANSPORT_TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + USER_ID + " INTEGER," +
            DATE + " TEXT," + DISTANCE + " REAL," + "FOREIGN KEY(" + USER_ID + ") REFERENCES "
            + MyConstantsUsers.USERS_TABLE_NAME + "(" + MyConstantsUsers._ID + "))";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TRANSPORT_TABLE_NAME;
}