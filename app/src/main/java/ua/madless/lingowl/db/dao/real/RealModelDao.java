package ua.madless.lingowl.db.dao.real;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ua.madless.lingowl.db.dao.BaseDao;

/**
 * Created by User on 18.01.2016.
 */
public abstract class RealModelDao extends BaseDao {
    public final static String FIELD_ID = "_ID";

    public int getNextId() {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        String selection =
                "SELECT MAX(" + getIdName() + ") " +
                        "FROM " + getTableName();
        String[] selectionArgs = null;
        Cursor wordCursor = db.rawQuery(selection, selectionArgs);
        wordCursor.moveToFirst();
        int maxId = wordCursor.getInt(0);
        wordCursor.close();
        dbManager.close();
        return maxId + 1;
    }

    public String getIdName() {
        return FIELD_ID;
    }
}
