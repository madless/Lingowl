package ua.madless.lingowl.db.dao.real;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import ua.madless.lingowl.db.DBManager;
//import ua.madless.lingowl.db.dao.link.DaoDictWord;
import ua.madless.lingowl.db.dao.link.DaoCatWord;
import ua.madless.lingowl.db.dao.link.DaoDictWord;
import ua.madless.lingowl.model.Category;
import ua.madless.lingowl.model.Dictionary;
import ua.madless.lingowl.model.Word;

public class DaoWord extends RealModelDao {
    public final static String TABLE_NAME = "words";
    public final static String FIELD_TEXT = "text";
    public final static String FIELD_TRANSLATION = "translation";
    public final static String FIELD_PART_OF_SPEECH = "part_of_speech";
    public final static String FIELD_GENDER = "gender";
    public final static String FIELD_NUMBER = "number";
    final static String FIELD_IS_FAVORITE = "is_favorite";
    public final static String QUERY_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            " ( " +
                FIELD_ID + " integer primary key, " +
                FIELD_TEXT + " text, " +
                FIELD_TRANSLATION + " text, " +
                FIELD_PART_OF_SPEECH + " text, " +
                FIELD_GENDER + " text, " +
                FIELD_NUMBER + " text, " +
                FIELD_IS_FAVORITE + " integer " +
            " );";

    public DaoWord(DBManager dbManager) {
        this.dbManager = dbManager;
        dbManager.createDatabase();
    }

    public void addWord(Word word) {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        ContentValues wordRow = new ContentValues();
        wordRow.put(FIELD_ID, word.getId());
        wordRow.put(FIELD_TEXT, word.getText());
        wordRow.put(FIELD_TRANSLATION, word.getTranslation());
        wordRow.put(FIELD_PART_OF_SPEECH, word.getPartOfSpeech());
        wordRow.put(FIELD_GENDER, word.getGender());
        wordRow.put(FIELD_NUMBER, word.getNumber());
        wordRow.put(FIELD_IS_FAVORITE, word.isFavorite() ? 1 : 0);
        db.insert(TABLE_NAME, null, wordRow);
        Log.d("mylog", "word inserted: " + word.toString());
        dbManager.close();
    }

    public ArrayList<Word> getWordsInCategory(Category category) {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        ArrayList<Word> words = new ArrayList<>();
        String selection = "SELECT w." + FIELD_ID + ", w." + FIELD_TEXT + ", w." + FIELD_TRANSLATION + ", w." + FIELD_PART_OF_SPEECH + ", w." + FIELD_GENDER + ", w." + FIELD_NUMBER + ", w." + FIELD_IS_FAVORITE +
                " FROM " + TABLE_NAME + " as w, " + DaoCatWord.LINK_TABLE_NAME + " as dw " +
                " WHERE dw." + DaoCatWord.LINK_FIELD_ID_CAT + " = ? AND w." + FIELD_ID + " = dw." + DaoCatWord.LINK_FIELD_ID_WORD;
        String[] selectionArgs = {String.valueOf(category.getId())};
        Cursor wordCursor = db.rawQuery(selection, selectionArgs);
        if(wordCursor.moveToFirst()) {
            int idColIndex = wordCursor.getColumnIndex(FIELD_ID);
            int textColIndex = wordCursor.getColumnIndex(FIELD_TEXT);
            int translationColIndex = wordCursor.getColumnIndex(FIELD_TRANSLATION);
            int partOfSpeechColIndex = wordCursor.getColumnIndex(FIELD_PART_OF_SPEECH);
            int genderColIndex = wordCursor.getColumnIndex(FIELD_GENDER);
            int numberColIndex = wordCursor.getColumnIndex(FIELD_NUMBER);
            int isFavoriteIndex = wordCursor.getColumnIndex(FIELD_IS_FAVORITE);
            do {
                int id = wordCursor.getInt(idColIndex);
                String text = wordCursor.getString(textColIndex);
                String translation = wordCursor.getString(translationColIndex);
                String partOfSpeech = wordCursor.getString(partOfSpeechColIndex);
                String gender = wordCursor.getString(genderColIndex);
                String number = wordCursor.getString(numberColIndex);
                boolean isFavorite = wordCursor.getInt(isFavoriteIndex) > 0;
                Word word = new Word(id, text, translation, partOfSpeech, gender, number, isFavorite);
                words.add(word);
            } while (wordCursor.moveToNext());
        } else {
            Log.d("mylog", "NO ROWS IN TABLE " + TABLE_NAME);
        }
        wordCursor.close();
        dbManager.close();
        Log.d("mylog", "words in category: " + words);
        return words;
    }

    public ArrayList<Word> getWordsInDictionary(Dictionary dictionary) {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        ArrayList<Word> words = new ArrayList<>();
        String selection = "SELECT w." + FIELD_ID + ", w." + FIELD_TEXT + ", w." + FIELD_TRANSLATION + ", w." + FIELD_PART_OF_SPEECH + ", w." + FIELD_GENDER + ", w." + FIELD_NUMBER +
                " FROM " + TABLE_NAME + " as w, " + DaoDictWord.LINK_TABLE_NAME + " as dw " +
                " WHERE dw." + DaoDictWord.LINK_FIELD_ID_DICT + " = ? AND w." + FIELD_ID + " = dw." + DaoDictWord.LINK_FIELD_ID_WORD;
        String[] selectionArgs = {String.valueOf(dictionary.getId())};
        Cursor wordCursor = db.rawQuery(selection, selectionArgs);
        if(wordCursor.moveToFirst()) {
            int idColIndex = wordCursor.getColumnIndex(FIELD_ID);
            int textColIndex = wordCursor.getColumnIndex(FIELD_TEXT);
            int translationColIndex = wordCursor.getColumnIndex(FIELD_TRANSLATION);
            int partOfSpeechColIndex = wordCursor.getColumnIndex(FIELD_PART_OF_SPEECH);
            int genderColIndex = wordCursor.getColumnIndex(FIELD_GENDER);
            int numberColIndex = wordCursor.getColumnIndex(FIELD_NUMBER);
            int isFavoriteIndex = wordCursor.getColumnIndex(FIELD_IS_FAVORITE);
            do {
                int id = wordCursor.getInt(idColIndex);
                String text = wordCursor.getString(textColIndex);
                String translation = wordCursor.getString(translationColIndex);
                String partOfSpeech = wordCursor.getString(partOfSpeechColIndex);
                String gender = wordCursor.getString(genderColIndex);
                String number = wordCursor.getString(numberColIndex);
                boolean isFavorite = wordCursor.getInt(isFavoriteIndex) > 0;
                Word word = new Word(id, text, translation, partOfSpeech, gender, number, isFavorite);
                words.add(word);
            } while (wordCursor.moveToNext());
        } else {
            Log.d("mylog", "NO ROWS IN TABLE " + TABLE_NAME);
        }
        wordCursor.close();
        dbManager.close();
        Log.d("mylog", "words in dictionary: " + words);
        return words;
    }

    public int getWordsCountInCategory(Category category) {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        ArrayList<Word> words = new ArrayList<>();
        String selection = "SELECT COUNT(w." + FIELD_ID + ") " +
                " FROM " + TABLE_NAME + " as w, " + DaoCatWord.LINK_TABLE_NAME + " as dw " +
                " WHERE dw." + DaoCatWord.LINK_FIELD_ID_CAT + " = ? AND w." + FIELD_ID + " = dw." + DaoCatWord.LINK_FIELD_ID_WORD;
        String[] selectionArgs = {String.valueOf(category.getId())};
        Cursor wordCursor = db.rawQuery(selection, selectionArgs);
        wordCursor.moveToFirst();
        int wordCount = wordCursor.getInt(0);
        wordCursor.close();
        dbManager.close();
        Log.d("mylog", "word count in category: " + wordCount);
        return wordCount;
    }

    public ArrayList<Word> getAllWords() {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        ArrayList<Word> words = new ArrayList<>();
        Cursor wordCursor = db.query(TABLE_NAME, null, null, null, null, null, null); //(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
        if(wordCursor.moveToFirst()) {
            int idColIndex = wordCursor.getColumnIndex(FIELD_ID);
            int textColIndex = wordCursor.getColumnIndex(FIELD_TEXT);
            int translationColIndex = wordCursor.getColumnIndex(FIELD_TRANSLATION);
            int partOfSpeechColIndex = wordCursor.getColumnIndex(FIELD_PART_OF_SPEECH);
            int genderColIndex = wordCursor.getColumnIndex(FIELD_GENDER);
            int numberColIndex = wordCursor.getColumnIndex(FIELD_NUMBER);
            int isFavoriteIndex = wordCursor.getColumnIndex(FIELD_IS_FAVORITE);
            do {
                int id = wordCursor.getInt(idColIndex);
                String text = wordCursor.getString(textColIndex);
                String translation = wordCursor.getString(translationColIndex);
                String partOfSpeech = wordCursor.getString(partOfSpeechColIndex);
                String gender = wordCursor.getString(genderColIndex);
                String number = wordCursor.getString(numberColIndex);
                boolean isFavorite = wordCursor.getInt(isFavoriteIndex) > 0;
                Word word = new Word(id, text, translation, partOfSpeech, gender, number, isFavorite);
                words.add(word);
            } while (wordCursor.moveToNext());
        } else {
            Log.d("mylog", "NO ROWS IN TABLE " + TABLE_NAME);
        }
        wordCursor.close();
        dbManager.close();
        Log.d("mylog", "All words: " + words);
        return words;
    }

    public int getNextId() {
        dbManager.open();
        SQLiteDatabase db = dbManager.getDatabase();
        String selection =
                "SELECT MAX(w." + FIELD_ID + ") " +
                "FROM " + TABLE_NAME;
        String[] selectionArgs = null;
        Cursor wordCursor = db.rawQuery(selection, selectionArgs);
        wordCursor.moveToFirst();
        int maxId = wordCursor.getInt(0);
        wordCursor.close();
        dbManager.close();
        return maxId + 1;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
