package ua.madless.lingowl.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import ua.madless.lingowl.db.DBManager;
import ua.madless.lingowl.model.Category;
import ua.madless.lingowl.model.Word;

/**
 * Created by madless on 02.01.2016.
 */
public class DaoCategory {
    public final static String TABLE_NAME = "category";
    public final static String FIELD_ID = "_ID";
    public final static String FIELD_NAME = "name";
    public final static String FIELD_ICON_ID = "icon_id";
    public final static String FIELD_WORD_COUNTER = "word_counter";
    public final static String QUERY_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            " ( " +
                FIELD_ID + " integer primary key, " +
                FIELD_NAME + " text " +
                FIELD_ICON_ID + " integer " +
                FIELD_WORD_COUNTER + " integer " +
            " );";

    DBManager dbManager;

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

    public void addCategory(Word word, Category category) {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();

        ContentValues categoryRow = new ContentValues();
        categoryRow.put(FIELD_ID, category.getId());
        categoryRow.put(FIELD_NAME, category.getName());
        categoryRow.put(FIELD_ICON_ID, category.getIconId());
        categoryRow.put(FIELD_WORD_COUNTER, category.getWordCounter());
        db.insert(TABLE_NAME, null, categoryRow);

        ContentValues wordCatRow = new ContentValues();
        wordCatRow.put(DaoWordCat.LINK_FIELD_ID_WORD, word.getId());
        wordCatRow.put(DaoWordCat.LINK_FIELD_ID_CAT, category.getId());
        db.insert(DaoDictWord.LINK_TABLE_NAME, null, wordCatRow);

        Log.d("mylog", "category inserted: " + category.toString());
        dbManager.close();
    }

    public void joinCategoryAndWord(Category category, Word word) {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        ContentValues wordCatRow = new ContentValues();
        wordCatRow.put(DaoWordCat.LINK_FIELD_ID_WORD, word.getId());
        wordCatRow.put(DaoWordCat.LINK_FIELD_ID_CAT, category.getId());
        db.insert(DaoDictWord.LINK_TABLE_NAME, null, wordCatRow);
        Log.d("mylog", "word " + word.getText() + " joined to category: " + category.getName());
        dbManager.close();
    }

    public ArrayList<Category> getCategories(Word word) {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        ArrayList<Category> categories = new ArrayList<>();
        String selection = "SELECT c." + FIELD_ID + ", c." + FIELD_NAME +
                " FROM " + TABLE_NAME + " as c, " + DaoWordCat.LINK_TABLE_NAME + " as wc " +
                " WHERE wc." + DaoWordCat.LINK_FIELD_ID_WORD + " = ? AND c." + FIELD_ID + " = wc." + DaoWordCat.LINK_FIELD_ID_CAT;
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

    public void incrementWordCount(Category category) {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        ContentValues dictionaryRow = new ContentValues();
        dictionaryRow.put(FIELD_WORD_COUNTER, category.getWordCounter() + 1);
        String whereClause = FIELD_ID + " = ";
        String[] whereArgs = new String[]{String.valueOf(category.getId())};
        db.update(TABLE_NAME, dictionaryRow, whereClause, whereArgs);
        Log.d("mylog", "dictionary incremented: " + category.toString());
        dbManager.close();
    }

}
