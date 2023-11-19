package com.example.badminton_court;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "USERS";
    public static final String BOOKING_TABLE = "BOOKING";
    public static final String JOIN_REQUEST_TABLE = "JOINING";
    public static final String APPROVED_TABLE = "APPROVED";

    //Booking columns
    public static final String _BOOKING_ID = "_id";
    public static final String BOOKING_DATE = "date";
    public static final String BOOKING_TIME = "time";
    public static final String BOOKING_BRANCH = "branch";
    public static final String BOOKING_COURT = "court";
    public static final String BOOKING_SKILL = "skill";
    public static final String BOOKING_STATUS = "status";
    public static final String BOOKING_CURRENT_PLAYERS = "currentPlayers";
    public static final String BOOKING_JOIN_PLAYERS = "joinPlayers";
    public static final String BOOKING_USER_ID = "userId";
    public static final String BOOKING_USER_NAME = "userName";

    // Table columns
    public static final String _ID = "_id";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String AGE = "age";

    //Court Join Request columns
    public static final String _JOIN_REQUEST_ID = "_id";
    public static final String JOIN_REQUEST_BOOKING_ID = "courtId";
    public static final String JOIN_REQUEST_HOST_NAME = "hostName";
    public static final String JOIN_REQUEST_DATE = "date";
    public static final String JOIN_REQUEST_TIME = "time";
    public static final String JOIN_REQUEST_BRANCH = "branch";
    public static final String JOIN_REQUEST_COURT = "court";
    public static final String JOIN_REQUEST_USER_NAME = "name";
    public static final String JOIN_REQUEST_EMAIL = "email";
    public static final String _APPROVED_ID = "_id";
    public static final String APPROVED_BOOKING_ID = "bookingId";
    public static final String APPROVED_USERNAME = "username";
    public static final String APPROVED_EMAIL = "email";
    public static final String APPROVED_BRANCH = "branch";
    public static final String APPROVED_COURT = "court";
    public static final String APPROVED_DATE = "date";
    public static final String APPROVED_TIME = "time";
    public static final String APPROVED_HOST = "host";
    // Database Information
    static final String DB_NAME = "BADMINTON.DB";

    // database version
    static final int DB_VERSION = 7;

    // Creating table query
//    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
//            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT NOT NULL, " + PASSWORD + " TEXT);";

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + EMAIL + " TEXT NOT NULL, " + NAME + " TEXT NOT NULL, " + PASSWORD + " TEXT NOT NULL, " + AGE + " TEXT NOT NULL);";
    private static final String CREATE_BOOKING_TABLE = "create table " + BOOKING_TABLE + "(" + _BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BOOKING_DATE + " TEXT NOT NULL, " + BOOKING_BRANCH + " TEXT NOT NULL, " + BOOKING_COURT + " TEXT NOT NULL, " + BOOKING_TIME + " TEXT NOT NULL, " + BOOKING_SKILL + " TEXT NOT NULL, " + BOOKING_STATUS + " TEXT NOT NULL, " + BOOKING_CURRENT_PLAYERS + " TEXT NOT NULL, " + BOOKING_JOIN_PLAYERS + " TEXT NOT NULL, " + BOOKING_USER_ID + " TEXT NOT NULL," + BOOKING_USER_NAME + " TEXT NOT NULL);";
    private static final String CREATE_JOIN_REQUEST_TABLE = "create table " + JOIN_REQUEST_TABLE + "(" + _JOIN_REQUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + JOIN_REQUEST_BOOKING_ID + " TEXT NOT NULL, " + JOIN_REQUEST_HOST_NAME + " TEXT NOT NULL, " + JOIN_REQUEST_DATE + " TEXT NOT NULL, " + JOIN_REQUEST_TIME + " TEXT NOT NULL, " + JOIN_REQUEST_BRANCH + " TEXT NOT NULL, " + JOIN_REQUEST_COURT + " TEXT NOT NULL, " + JOIN_REQUEST_USER_NAME + " TEXT NOT NULL, " + JOIN_REQUEST_EMAIL + " TEXT NOT NULL);";
    private static final String CREATE_APPROVED_TABLE = "create table " + APPROVED_TABLE + "(" + _APPROVED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + APPROVED_BOOKING_ID + " TEXT NOT NULL, " + APPROVED_USERNAME + " TEXT NOT NULL, " + APPROVED_EMAIL + " TEXT NOT NULL, " + APPROVED_BRANCH + " TEXT NOT NULL, " + APPROVED_COURT + " TEXT NOT NULL, " + APPROVED_DATE + " TEXT NOT NULL, " + APPROVED_TIME + " TEXT NOT NULL, " + APPROVED_HOST + " TEXT NOT NULL);";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_BOOKING_TABLE);
        db.execSQL(CREATE_JOIN_REQUEST_TABLE);
        db.execSQL(CREATE_APPROVED_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BOOKING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + JOIN_REQUEST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + APPROVED_TABLE);
        onCreate(db);
    }
}

