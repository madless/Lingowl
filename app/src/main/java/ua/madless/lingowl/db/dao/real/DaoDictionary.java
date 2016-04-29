package ua.madless.lingowl.db.dao.real;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import ua.madless.lingowl.core.model.db_model.Dictionary;
import ua.madless.lingowl.db.DBManager;

/**
 * Created by madless on 02.01.2016.
 */
public class DaoDictionary extends RealModelDao {
    private final static String TABLE_NAME = "dictionary";
    private final static String FIELD_NAME = "name";
    private final static String FIELD_CODE_TARGET_LANGUAGE = "code_target_language";
    private final static String FIELD_CODE_NATIVE_LANGUAGE = "code_native_language";
    private final static String FIELD_ICON_ID = "icon_id";
    private final static String FIELD_WORD_COUNTER = "word_counter";
    private final static String FIELD_DICT_TYPE = "dict_type";

    public final static String QUERY_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            " ( " +
                FIELD_ID + " integer primary key, " +
                FIELD_NAME + " text, " +
                FIELD_CODE_TARGET_LANGUAGE + " text, " +
                FIELD_CODE_NATIVE_LANGUAGE + " text, " +
                FIELD_ICON_ID + " integer, " +
                FIELD_WORD_COUNTER  + " integer, " +
                FIELD_DICT_TYPE + " integer " +
            " );";

    public DaoDictionary(DBManager dbManager) {
        this.dbManager = dbManager;
        dbManager.createDatabase();
    }

    public void addDictionary(Dictionary dictionary) {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        ContentValues dictionaryRow = new ContentValues();
        dictionaryRow.put(FIELD_ID, dictionary.getId());
        dictionaryRow.put(FIELD_NAME, dictionary.getName());
        dictionaryRow.put(FIELD_CODE_TARGET_LANGUAGE, dictionary.getCodeTargetLanguage());
        dictionaryRow.put(FIELD_CODE_NATIVE_LANGUAGE, dictionary.getCodeNativeLanguage());
        dictionaryRow.put(FIELD_ICON_ID, dictionary.getIconId());
        dictionaryRow.put(FIELD_WORD_COUNTER, dictionary.getWordCounter());
        dictionaryRow.put(FIELD_DICT_TYPE, dictionary.getDictType());
        db.insert(TABLE_NAME, null, dictionaryRow);
        Log.d("mylog", "dictionary inserted: " + dictionary.toString());
        dbManager.close();
    }

    public ArrayList<Dictionary> getAllDictionaries() {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        ArrayList<Dictionary> dictionaries = new ArrayList<>();
        Cursor dictionaryCursor = db.query(TABLE_NAME, null, null, null, null, null, null); //(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
        if(dictionaryCursor.moveToFirst()) {
            int idColIndex = dictionaryCursor.getColumnIndex(FIELD_ID);
            int nameColIndex = dictionaryCursor.getColumnIndex(FIELD_NAME);
            int codeTargetLanguageColIndex = dictionaryCursor.getColumnIndex(FIELD_CODE_TARGET_LANGUAGE);
            int codeNativeLanguageColIndex = dictionaryCursor.getColumnIndex(FIELD_CODE_NATIVE_LANGUAGE);
            int iconIdColIndex = dictionaryCursor.getColumnIndex(FIELD_ICON_ID);
            int wordCounterColIndex = dictionaryCursor.getColumnIndex(FIELD_WORD_COUNTER);
            int dictTypeColIndex = dictionaryCursor.getColumnIndex(FIELD_DICT_TYPE);
            do {
                int id = dictionaryCursor.getInt(idColIndex);
                String name = dictionaryCursor.getString(nameColIndex);
                String codeTargetLanguage = dictionaryCursor.getString(codeTargetLanguageColIndex);
                String codeNativeLanguage = dictionaryCursor.getString(codeNativeLanguageColIndex);
                int iconId = dictionaryCursor.getInt(iconIdColIndex);
                int wordCounter = dictionaryCursor.getInt(wordCounterColIndex);
                int dictType = dictionaryCursor.getInt(dictTypeColIndex);
                Dictionary dictionary = new Dictionary(id, name, codeTargetLanguage, codeNativeLanguage, iconId, wordCounter, dictType);
                dictionaries.add(dictionary);
            } while (dictionaryCursor.moveToNext());
        } else {
            Log.d("mylog", "NO ROWS IN TABLE " + TABLE_NAME);
        }
        dictionaryCursor.close();
        dbManager.close();
        Log.d("mylog", "Dictionaries: " + dictionaries);
        return dictionaries;
    }

    public void incrementWordCount(Dictionary dictionary) {
        countOperation(dictionary, 1);
    }

    public void decrementWordCount(Dictionary dictionary) {
        countOperation(dictionary, -1);
    }

    public void countOperation(Dictionary dictionary, int i) {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        ContentValues dictionaryRow = new ContentValues();
        dictionaryRow.put(FIELD_WORD_COUNTER, dictionary.getWordCounter() + i);
        String whereClause = FIELD_ID + " = ?";
        String[] whereArgs = new String[]{String.valueOf(dictionary.getId())};
        db.update(TABLE_NAME, dictionaryRow, whereClause, whereArgs);
        dbManager.close();
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
