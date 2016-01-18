package ua.madless.lingowl.db.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ua.madless.lingowl.db.DBManager;
import ua.madless.lingowl.model.Category;
import ua.madless.lingowl.model.Word;

/**
 * Created by madless on 02.01.2016.
 */
public class DaoCatWord extends BaseDao {
    DBManager dbManager;
    public final static String LINK_TABLE_NAME = "cat_word";
    public final static String LINK_FIELD_ID_CAT = "id_cat";
    public final static String LINK_FIELD_ID_WORD = "id_word";
    public final static String QUERY_CREATE_LINK_TABLE = "CREATE TABLE " + LINK_TABLE_NAME +
            " ( " +
                LINK_FIELD_ID_CAT + " integer, " +
                LINK_FIELD_ID_WORD + " integer " +
            " );";

    public DaoCatWord(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public void linkCategoryWithWord(Category category, Word word) {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        ContentValues wordCatRow = new ContentValues();
        wordCatRow.put(LINK_FIELD_ID_WORD, word.getId());
        wordCatRow.put(LINK_FIELD_ID_CAT, category.getId());
        db.insert(LINK_TABLE_NAME, null, wordCatRow);
        dbManager.close();
    }

    @Override
    String getTableName() {
        return LINK_TABLE_NAME;
    }
}
