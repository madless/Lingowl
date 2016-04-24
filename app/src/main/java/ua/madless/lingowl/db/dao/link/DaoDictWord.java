package ua.madless.lingowl.db.dao.link;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import ua.madless.lingowl.db.DBManager;
import ua.madless.lingowl.db.dao.BaseDao;
import ua.madless.lingowl.core.model.db_model.Dictionary;
import ua.madless.lingowl.core.model.db_model.Word;

/**
 * Created by madless on 02.01.2016.
 */
public class DaoDictWord extends BaseDao {
    DBManager dbManager;
    public final static String LINK_TABLE_NAME = "dict_word";
    public final static String LINK_FIELD_ID_WORD = "id_word";
    public final static String LINK_FIELD_ID_DICT = "id_dict";
    public final static String QUERY_CREATE_LINK_TABLE = "CREATE TABLE " + LINK_TABLE_NAME +
            " ( " +
            LINK_FIELD_ID_DICT + " integer, " +
            LINK_FIELD_ID_WORD + " integer " +
            " );";

    public DaoDictWord(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public void linkDictionaryWithWord(Dictionary dictionary, Word word) {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        ContentValues wordCatRow = new ContentValues();
        wordCatRow.put(LINK_FIELD_ID_WORD, word.getId());
        wordCatRow.put(LINK_FIELD_ID_DICT, dictionary.getId());
        db.insert(LINK_TABLE_NAME, null, wordCatRow);
        dbManager.close();
    }

    @Override
    public String getTableName() {
        return LINK_TABLE_NAME;
    }
}
