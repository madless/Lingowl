package ua.madless.lingowl.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ua.madless.lingowl.db.DBManager;

/**
 * Created by User on 18.01.2016.
 */
public abstract class BaseDao {
    DBManager dbManager;
    abstract String getTableName();
}
