package com.example.badminton_court;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import org.mindrot.jbcrypt.BCrypt;

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

        database.close();
        return user;
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
