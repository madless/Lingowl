package ua.madless.lingowl.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.sql.SQLException;


public class DBManager {
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public DBManager(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public DBManager createDatabase() {
        try {
            dbHelper.createDataBase();
        }
        catch (IOException ex) {
            Log.e("mylog", ex.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DBManager open() {
        try {
            dbHelper.openDatabase();
            dbHelper.close();
            database = dbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException) {
            Log.e("mylog", "open >>"+ mSQLException.toString());
        }
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}
