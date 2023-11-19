package com.example.badminton_court;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }
    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        database = dbHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }


    //register account
    public long addUser(String name, String email, String password, String age) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.NAME, name);
        values.put(DatabaseHelper.EMAIL, email);
        values.put(DatabaseHelper.PASSWORD, password);
        values.put(DatabaseHelper.AGE, age);

        return database.insert(DatabaseHelper.TABLE_NAME, null, values);
    }

    public long booking(String date, String branch, String court, String time, String skill, String status, String currentPlayers, String joinPlayers, String userId, String userName) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.BOOKING_DATE, date);
        values.put(DatabaseHelper.BOOKING_BRANCH, branch);
        values.put(DatabaseHelper.BOOKING_COURT, court);
        values.put(DatabaseHelper.BOOKING_TIME, time);
        values.put(DatabaseHelper.BOOKING_SKILL, skill);
        values.put(DatabaseHelper.BOOKING_STATUS, status);
        values.put(DatabaseHelper.BOOKING_CURRENT_PLAYERS, currentPlayers);
        values.put(DatabaseHelper.BOOKING_JOIN_PLAYERS, joinPlayers);
        values.put(DatabaseHelper.BOOKING_USER_ID, userId);
        values.put(DatabaseHelper.BOOKING_USER_NAME, userName);

        return database.insert(DatabaseHelper.BOOKING_TABLE, null, values);
    }

    public boolean checkBookingAvailability(String date, String branch, String court, String time){
        database = dbHelper.getReadableDatabase();
        String[] columns = new String[] {DatabaseHelper._BOOKING_ID};
        String selection = DatabaseHelper.BOOKING_DATE + "=? AND " + DatabaseHelper.BOOKING_BRANCH + "=? AND " + DatabaseHelper.BOOKING_COURT + "=? AND " + DatabaseHelper.BOOKING_TIME + "=? ";
        String[] selectionArgs = {date, branch, court, time};
        Cursor cursor = database.query(DatabaseHelper.BOOKING_TABLE, columns, selection, selectionArgs, null, null, null);
        // Check if there are any matching records
        boolean availability = cursor.getCount() > 0;
        cursor.close();
        return availability;
    }

    //check already email when register
    public boolean checkExistEmail(String email){
//        open();
        database = dbHelper.getReadableDatabase();
        String[] columns = new String[] {DatabaseHelper._ID, DatabaseHelper.NAME};
        String selection = DatabaseHelper.EMAIL + "=?";
        String[] selectionArgs = {email};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, selection, selectionArgs,null,null,null);
        boolean result = cursor.getCount() > 0; // if the result is not empty
        cursor.close();
        return result;
    }

    //login check
//    public Boolean checkemailpassword(String email, String hashedEnteredPassword){
//        open();
//        String[] columns = new String[] {DatabaseHelper._ID, DatabaseHelper.NAME}; //names of the columns that want to retrieve from the db
//        String selection = DatabaseHelper.EMAIL + "=? AND " + DatabaseHelper.PASSWORD + "=?"; //check the email column is = email parameter and also password column is = pass parameter
//        String[] selectionArgs = {email, hashedEnteredPassword}; // replace the ? in the selection
//        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
//
//        boolean result = cursor.getCount() > 0;
//        cursor.close();
//        close(); // Close the database
//        return result;
//    }
    public Boolean checkemailpassword(String email, String enteredPassword) {
        open();
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.NAME, DatabaseHelper.PASSWORD }; // select id name and password
        // Check the email column
        String selection = DatabaseHelper.EMAIL + "=?"; // where email column = email(parameter)
        String[] selectionArgs = { email };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        boolean result = false;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            // Get the stored hashed password from the db
            String storedHashedPassword = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PASSWORD));

            // Verify the entered password(not hashing) with the stored hashed pass
            if (BCrypt.checkpw(enteredPassword, storedHashedPassword)) {
                result = true;
            }
        }

        cursor.close();
        close(); // Close db
        return result;
    }

    //Get data based on email
    public User getUserByEmail(String email) {
        open();
        //database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, new String[]{"name", "email", "age", "_id"},
                "email=?", new String[]{email}, null, null, null);

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User();
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            user.setAge(cursor.getInt(cursor.getColumnIndex("age")));
            user.setId(cursor.getInt(cursor.getColumnIndex("_id")));

            // Retrieve other user details and set them in the User object
            cursor.close();
        }

//        database.close();
        return user;
    }

    // Get the booking history based on user id and name
    public Cursor fetch(String id, String name) {
        String[] columns = new String[] { DatabaseHelper._BOOKING_ID, DatabaseHelper.BOOKING_DATE, DatabaseHelper.BOOKING_TIME, DatabaseHelper.BOOKING_BRANCH };
        String selection = DatabaseHelper.BOOKING_USER_ID + "=? AND " + DatabaseHelper.BOOKING_USER_NAME + "=?" ; // where email column = email(parameter)
        String[] selectionArgs = { id, name };
        Cursor cursor = database.query(DatabaseHelper.BOOKING_TABLE, columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchApprovedRequest(String name) {
        String[] columns = new String[] { DatabaseHelper._APPROVED_ID,DatabaseHelper.APPROVED_BRANCH, DatabaseHelper.APPROVED_COURT, DatabaseHelper.APPROVED_DATE, DatabaseHelper.APPROVED_TIME, DatabaseHelper.APPROVED_HOST};
        String selection = DatabaseHelper.APPROVED_USERNAME + "=?" ;
        String[] selectionArgs = { name };
        Cursor cursor = database.query(DatabaseHelper.APPROVED_TABLE, columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetchJoinRequest(String id) {
        String[] columns = new String[] { DatabaseHelper._JOIN_REQUEST_ID,DatabaseHelper.JOIN_REQUEST_BOOKING_ID, DatabaseHelper.JOIN_REQUEST_USER_NAME, DatabaseHelper.JOIN_REQUEST_EMAIL, DatabaseHelper.JOIN_REQUEST_BRANCH, DatabaseHelper.JOIN_REQUEST_COURT, DatabaseHelper.JOIN_REQUEST_DATE, DatabaseHelper.JOIN_REQUEST_TIME, DatabaseHelper.JOIN_REQUEST_HOST_NAME};
        String selection = DatabaseHelper.JOIN_REQUEST_BOOKING_ID + "=?" ; //
        String[] selectionArgs = { id };
        Cursor cursor = database.query(DatabaseHelper.JOIN_REQUEST_TABLE, columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public long approveRequest(String bookingId, String username, String email, String branch, String court, String date, String time, String host) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.APPROVED_BOOKING_ID, bookingId);
        values.put(DatabaseHelper.APPROVED_USERNAME, username);
        values.put(DatabaseHelper.APPROVED_EMAIL, email);
        values.put(DatabaseHelper.APPROVED_BRANCH, branch);
        values.put(DatabaseHelper.APPROVED_COURT, court);
        values.put(DatabaseHelper.APPROVED_DATE, date);
        values.put(DatabaseHelper.APPROVED_TIME, time);
        values.put(DatabaseHelper.APPROVED_HOST, host);

        return database.insert(DatabaseHelper.APPROVED_TABLE, null, values);
    }

    public boolean checkJoinPlayers(String id){
        database = dbHelper.getReadableDatabase();
        String[] columns = new String[] {DatabaseHelper._BOOKING_ID};
        String selection = DatabaseHelper._BOOKING_ID + " = ? AND " + DatabaseHelper.BOOKING_JOIN_PLAYERS + " = ?";
        String[] selectionArgs = { id, "0" }; // Checking if BOOKING_JOIN_PLAYERS = 0 for the specific ID
        Cursor cursor = database.query(DatabaseHelper.BOOKING_TABLE, columns, selection, selectionArgs, null, null, null);
        // Check if there are any matching records
        boolean isJoinPlayersZero = cursor.getCount() > 0;
        cursor.close();
        return isJoinPlayersZero;
    }

    public int updateAfterApproved(String id) {
        Cursor cursor = fetchOneBooking(id);
        if (cursor != null && cursor.moveToFirst()) {
            int currentPlayersIndex = cursor.getColumnIndex(DatabaseHelper.BOOKING_CURRENT_PLAYERS);
            int joinPlayersIndex = cursor.getColumnIndex(DatabaseHelper.BOOKING_JOIN_PLAYERS);

            int currentPlayers = cursor.getInt(currentPlayersIndex);
            int joinPlayers = cursor.getInt(joinPlayersIndex);
            // Modify the player counts
            currentPlayers += 1; // Increment currentPlayers by 1
            joinPlayers -= 1;    // Decrement joinPlayers by 1

            // Update the database with the modified values
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.BOOKING_CURRENT_PLAYERS, String.valueOf(currentPlayers));
            values.put(DatabaseHelper.BOOKING_JOIN_PLAYERS, String.valueOf(joinPlayers));

            int i = database.update(DatabaseHelper.BOOKING_TABLE, values, DatabaseHelper._BOOKING_ID + " = " + id, null);
            return i;
        }
        return 0; // Handle the case where the columns are not found or the update couldn't be performed
    }

    public void deleteRequest(String id){
        database.delete(DatabaseHelper.JOIN_REQUEST_TABLE, DatabaseHelper._JOIN_REQUEST_ID + "=?",
                new String[]{id});
    }

    // Get others booking details based on booking id
    public Cursor fetchOneBooking(String id) {
        String[] columns = new String[] { DatabaseHelper.BOOKING_COURT, DatabaseHelper.BOOKING_STATUS, DatabaseHelper.BOOKING_CURRENT_PLAYERS, DatabaseHelper.BOOKING_SKILL, DatabaseHelper.BOOKING_JOIN_PLAYERS};
        String selection = DatabaseHelper._BOOKING_ID+ "=?"; // where email column = email(parameter)
        String[] selectionArgs = { id };
        Cursor cursor = database.query(DatabaseHelper.BOOKING_TABLE, columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Cancel booking
    public void cancelBooking(long bookingId){
        database.delete(DatabaseHelper.BOOKING_TABLE, DatabaseHelper._BOOKING_ID + "=?",
                new String[]{String.valueOf(bookingId)});
    }

    public Cursor fetchBeginnerCourtList() {
        String[] columns = new String[] { DatabaseHelper.BOOKING_COURT, DatabaseHelper._BOOKING_ID, DatabaseHelper.BOOKING_DATE, DatabaseHelper.BOOKING_TIME, DatabaseHelper.BOOKING_USER_NAME, DatabaseHelper.BOOKING_CURRENT_PLAYERS, DatabaseHelper.BOOKING_BRANCH };
        Cursor cursor = database.query(DatabaseHelper.BOOKING_TABLE, columns, "skill=? AND status=?", new String[]{"Beginner","Public"}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetchIntermediateCourtList() {
        String[] columns = new String[] { DatabaseHelper.BOOKING_COURT, DatabaseHelper._BOOKING_ID, DatabaseHelper.BOOKING_DATE, DatabaseHelper.BOOKING_TIME, DatabaseHelper.BOOKING_USER_NAME, DatabaseHelper.BOOKING_CURRENT_PLAYERS, DatabaseHelper.BOOKING_BRANCH };
        Cursor cursor = database.query(DatabaseHelper.BOOKING_TABLE, columns, "skill=? AND status=?", new String[]{"Intermediate","Public"}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetchExpertCourtList() {
        String[] columns = new String[] { DatabaseHelper.BOOKING_COURT, DatabaseHelper._BOOKING_ID, DatabaseHelper.BOOKING_DATE, DatabaseHelper.BOOKING_TIME, DatabaseHelper.BOOKING_USER_NAME, DatabaseHelper.BOOKING_CURRENT_PLAYERS, DatabaseHelper.BOOKING_BRANCH };
        Cursor cursor = database.query(DatabaseHelper.BOOKING_TABLE, columns, "skill=? AND status=?", new String[]{"Expert","Public"}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public boolean checkSameUser(String id, String bookingId){
        database = dbHelper.getReadableDatabase();
        String[] columns = new String[] {DatabaseHelper._BOOKING_ID};
        String selection = DatabaseHelper.BOOKING_USER_ID + "=? AND " + DatabaseHelper._BOOKING_ID + "=? ";
        String[] selectionArgs = {id, bookingId};
        Cursor cursor = database.query(DatabaseHelper.BOOKING_TABLE, columns, selection, selectionArgs, null, null, null);
        // Check if there are any matching records
        boolean exist = cursor.getCount() > 0;
        cursor.close();
        return exist;
    }

    //insert data into court join request
    public long insertJoinCourt(String courtId,String courtHost, String date, String time, String branch, String court, String joinName, String joinEmail) {
        open();
        String[] columns = new String[] {DatabaseHelper.JOIN_REQUEST_BOOKING_ID, DatabaseHelper.JOIN_REQUEST_USER_NAME};
        String selection = DatabaseHelper.JOIN_REQUEST_BOOKING_ID + "=? AND " + DatabaseHelper.JOIN_REQUEST_USER_NAME + "=? " ;
        String[] selectionArgs = {courtId, joinName};
        Cursor cursor = database.query(DatabaseHelper.JOIN_REQUEST_TABLE, columns, selection, selectionArgs, null, null, null);
        // Check if there are any matching records
        boolean availability = cursor.getCount() == 0;

        ContentValues contentValue = new ContentValues();
        if (availability) {
            contentValue.put(DatabaseHelper.JOIN_REQUEST_BOOKING_ID, courtId);
            contentValue.put(DatabaseHelper.JOIN_REQUEST_HOST_NAME, courtHost);
            contentValue.put(DatabaseHelper.JOIN_REQUEST_DATE, date);
            contentValue.put(DatabaseHelper.JOIN_REQUEST_TIME, time);
            contentValue.put(DatabaseHelper.JOIN_REQUEST_BRANCH, branch);
            contentValue.put(DatabaseHelper.JOIN_REQUEST_COURT, court);
            contentValue.put(DatabaseHelper.JOIN_REQUEST_USER_NAME, joinName);
            contentValue.put(DatabaseHelper.JOIN_REQUEST_EMAIL, joinEmail);
        }

        return database.insert(DatabaseHelper.JOIN_REQUEST_TABLE, null, contentValue);
    }

    public int update(long _id, String name, String age, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.AGE, age);
        contentValues.put(DatabaseHelper.PASSWORD, password);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public int updateWithoutPassword(long _id, String name, String age) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.AGE, age);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }


//    public Cursor fetch() {
//        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.NAME, DatabaseHelper.PASSWORD };
//        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        return cursor;
//    }

}
