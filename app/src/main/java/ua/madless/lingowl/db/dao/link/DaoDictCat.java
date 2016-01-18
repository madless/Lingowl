package ua.madless.lingowl.db.dao.link;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ua.madless.lingowl.db.DBManager;
import ua.madless.lingowl.db.dao.BaseDao;
import ua.madless.lingowl.model.Category;
import ua.madless.lingowl.model.Dictionary;
import ua.madless.lingowl.model.Word;

/**
 * Created by madless on 12.01.2016.
 */
public class DaoDictCat extends BaseDao {
    DBManager dbManager;
    public final static String LINK_TABLE_NAME = "dict_cat";
    public final static String LINK_FIELD_ID_DICT = "id_dict";
    public final static String LINK_FIELD_ID_CAT = "id_cat";
    public final static String QUERY_CREATE_LINK_TABLE = "CREATE TABLE " + LINK_TABLE_NAME +
            " ( " +
            LINK_FIELD_ID_CAT + " integer, " +
            LINK_FIELD_ID_DICT + " integer " +
            " );";

    public DaoDictCat(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public void linkDictionaryWithCategory(Dictionary dictionary, Category category) {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        ContentValues wordCatRow = new ContentValues();
        wordCatRow.put(LINK_FIELD_ID_CAT, category.getId());
        wordCatRow.put(LINK_FIELD_ID_DICT, dictionary.getId());
        db.insert(LINK_TABLE_NAME, null, wordCatRow);
        dbManager.close();
    }

    @Override
    public String getTableName() {
        return LINK_TABLE_NAME;
    }
}
