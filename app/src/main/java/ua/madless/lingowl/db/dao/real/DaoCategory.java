package ua.madless.lingowl.db.dao.real;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import ua.madless.lingowl.db.DBManager;
import ua.madless.lingowl.db.dao.link.DaoCatWord;
import ua.madless.lingowl.db.dao.link.DaoDictCat;
import ua.madless.lingowl.core.model.db_model.Category;
import ua.madless.lingowl.core.model.db_model.Dictionary;
import ua.madless.lingowl.core.model.db_model.Word;

/**
 * Created by madless on 02.01.2016.
 */
public class DaoCategory extends RealModelDao {
    public final static String TABLE_NAME = "category";
    public final static String FIELD_NAME = "name";
    public final static String FIELD_ICON_ID = "icon_id";
    public final static String FIELD_WORD_COUNTER = "word_counter";
    public final static String QUERY_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            " ( " +
                FIELD_ID + " integer primary key, " +
                FIELD_NAME + " text, " +
                FIELD_ICON_ID + " integer, " +
                FIELD_WORD_COUNTER + " integer " +
            " );";



    public DaoCategory(DBManager dbManager) {
        this.dbManager = dbManager;
        dbManager.createDatabase();
    }

    public void addCategory(Category category) {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        ContentValues categoryRow = new ContentValues();
        categoryRow.put(FIELD_ID, category.getId());
        categoryRow.put(FIELD_NAME, category.getName());
        categoryRow.put(FIELD_ICON_ID, category.getIconId());
        categoryRow.put(FIELD_WORD_COUNTER, category.getWordCounter());
        db.insert(TABLE_NAME, null, categoryRow);
        Log.d("mylog", "category inserted: " + category.toString());
        dbManager.close();
    }

    public ArrayList<Category> getCategoriesInWord(Word word) {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        ArrayList<Category> categories = new ArrayList<>();
        String selection = "SELECT c." + FIELD_ID + ", c." + FIELD_NAME + ", c." + FIELD_ICON_ID + ", c." +FIELD_WORD_COUNTER +
                " FROM " + TABLE_NAME + " as c, " + DaoCatWord.LINK_TABLE_NAME + " as wc " +
                " WHERE wc." + DaoCatWord.LINK_FIELD_ID_WORD + " = ? AND c." + FIELD_ID + " = wc." + DaoCatWord.LINK_FIELD_ID_CAT;
        String[] selectionArgs = {String.valueOf(word.getId())};
        Cursor categoryCursor = db.rawQuery(selection, selectionArgs);
        if(categoryCursor.moveToFirst()) {
            int idColIndex = categoryCursor.getColumnIndex(FIELD_ID);
            int nameColIndex = categoryCursor.getColumnIndex(FIELD_NAME);
            int iconIdColIndex = categoryCursor.getColumnIndex(FIELD_ICON_ID);
            int wordCounterColIndex = categoryCursor.getColumnIndex(FIELD_WORD_COUNTER);
            do {
                int id = categoryCursor.getInt(idColIndex);
                String name = categoryCursor.getString(nameColIndex);
                int iconId = categoryCursor.getInt(iconIdColIndex);
                int wordCounter = categoryCursor.getInt(wordCounterColIndex);
                Category category = new Category(id, name, iconId, wordCounter);
                categories.add(category);
            } while (categoryCursor.moveToNext());
        } else {
            Log.d("mylog", "NO ROWS IN TABLE " + TABLE_NAME);
        }
        categoryCursor.close();
        dbManager.close();
        Log.d("mylog", "Category in word " + word.getText() + ": " + categories);
        return categories;
    }

    public ArrayList<Category> getCategoriesInDictionary(Dictionary dictionary) {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        ArrayList<Category> categories = new ArrayList<>();
        String selection = "SELECT c." + FIELD_ID + ", c." + FIELD_NAME + ", c." + FIELD_ICON_ID + ", c." + FIELD_WORD_COUNTER +
                " FROM " + TABLE_NAME + " as c, " + DaoDictCat.LINK_TABLE_NAME + " as dc " +
                " WHERE dc." + DaoDictCat.LINK_FIELD_ID_DICT + " = ? AND c." + FIELD_ID + " = dc." + DaoDictCat.LINK_FIELD_ID_CAT;
        String[] selectionArgs = {String.valueOf(dictionary.getId())};
        Cursor categoryCursor = db.rawQuery(selection, selectionArgs);
        if(categoryCursor.moveToFirst()) {
            int idColIndex = categoryCursor.getColumnIndex(FIELD_ID);
            int nameColIndex = categoryCursor.getColumnIndex(FIELD_NAME);
            int iconIdColIndex = categoryCursor.getColumnIndex(FIELD_ICON_ID);
            int wordCounterColIndex = categoryCursor.getColumnIndex(FIELD_WORD_COUNTER);
            do {
                int id = categoryCursor.getInt(idColIndex);
                String name = categoryCursor.getString(nameColIndex);
                int iconId = categoryCursor.getInt(iconIdColIndex);
                int wordCounter = categoryCursor.getInt(wordCounterColIndex);
                Category category = new Category(id, name, iconId, wordCounter);
                categories.add(category);
            } while (categoryCursor.moveToNext());
        } else {
            Log.d("mylog", "NO ROWS IN TABLE " + TABLE_NAME);
        }
        categoryCursor.close();
        dbManager.close();
        return categories;
    }

    public ArrayList<Category> getAllCategories() {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        ArrayList<Category> categories = new ArrayList<>();
        Cursor categoryCursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if(categoryCursor.moveToFirst()) {
            int idColIndex = categoryCursor.getColumnIndex(FIELD_ID);
            int nameColIndex = categoryCursor.getColumnIndex(FIELD_NAME);
            int iconIdColIndex = categoryCursor.getColumnIndex(FIELD_ICON_ID);
            int wordCounterColIndex = categoryCursor.getColumnIndex(FIELD_WORD_COUNTER);
            do {
                int id = categoryCursor.getInt(idColIndex);
                String name = categoryCursor.getString(nameColIndex);
                int iconId = categoryCursor.getInt(iconIdColIndex);
                int wordCounter = categoryCursor.getInt(wordCounterColIndex);
                Category category = new Category(id, name, iconId, wordCounter);
                categories.add(category);
            } while (categoryCursor.moveToNext());
        } else {
            Log.d("mylog", "NO ROWS IN TABLE " + TABLE_NAME);
        }
        categoryCursor.close();
        dbManager.close();
        Log.d("mylog", "All categories: " + categories);
        return categories;
    }

    public void incrementWordCount(Category category, int count) {
        countOperation(category, count);
        Log.d("mylog", "category incremented: " + category.toString());
    }

    public void decrementWordCount(Category category, int count) {
        countOperation(category, -count);
        Log.d("mylog", "category decremented: " + category.toString());
    }

    public void countOperation(Category category, int i) {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        ContentValues dictionaryRow = new ContentValues();
        dictionaryRow.put(FIELD_WORD_COUNTER, category.getWordCounter() + i);
        String whereClause = FIELD_ID + " = ?";
        String[] whereArgs = new String[]{String.valueOf(category.getId())};
        db.update(TABLE_NAME, dictionaryRow, whereClause, whereArgs);
        dbManager.close();
    }

    public void deleteCategory(Category category) {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        String whereClause = FIELD_ID + " = ? ";
        String[] whereArgs = new String[]{String.valueOf(category.getId())};
        db.delete(TABLE_NAME, whereClause, whereArgs);
        dbManager.close();
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
