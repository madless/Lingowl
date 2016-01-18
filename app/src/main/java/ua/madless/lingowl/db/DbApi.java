package ua.madless.lingowl.db;

import java.util.ArrayList;

import ua.madless.lingowl.db.dao.DaoCatWord;
import ua.madless.lingowl.db.dao.DaoCategory;
import ua.madless.lingowl.db.dao.DaoDictCat;
import ua.madless.lingowl.db.dao.DaoDictWord;
import ua.madless.lingowl.db.dao.DaoDictionary;
import ua.madless.lingowl.db.dao.DaoWord;
import ua.madless.lingowl.model.Category;
import ua.madless.lingowl.model.Dictionary;
import ua.madless.lingowl.model.Word;

/**
 * Created by madless on 07.01.2016.
 */
public class DbApi {
    DBManager dbManager;
    DaoWord daoWord;
    DaoDictionary daoDictionary;
    DaoCategory daoCategory;
    DaoCatWord daoCatWord;
    DaoDictCat daoDictCat;
    DaoDictWord daoDictWord;

    public DbApi(DBManager dbManager) {
        this.dbManager = dbManager;
        daoDictionary = new DaoDictionary(dbManager);
        daoCategory = new DaoCategory(dbManager);
        daoWord = new DaoWord(dbManager);
        daoCatWord = new DaoCatWord(dbManager);
        daoDictCat = new DaoDictCat(dbManager);
        daoDictWord = new DaoDictWord(dbManager);
    }

    public void addWord(Dictionary dictionary, Category category, Word word) {
        int wordId = daoWord.getNextId();
        word.setId(wordId);
        daoWord.addWord(word);
        daoDictionary.incrementWordCount(dictionary);
        daoCategory.incrementWordCount(category);
        daoCatWord.linkCategoryWithWord(category, word);
        daoDictWord.linkDictionaryWithWord(dictionary, word);
    }

    public void addCategory(Dictionary dictionary, Category category) {
        int categoryId = daoCategory.getNextId();
        category.setId(categoryId);
        daoCategory.addCategory(category);
        daoDictCat.linkDictionaryWithCategory(dictionary, category);
    }

    public void addDictionary(Dictionary dictionary) {
        int dictionaryId = daoDictionary.getNextId();
        dictionary.setId(dictionaryId);
        daoDictionary.addDictionary(dictionary);
    }

    public ArrayList<Dictionary> getAllDictionaries() {
        ArrayList<Dictionary> dictionaries = daoDictionary.getAllDictionaries();
        return dictionaries;
    }

    public ArrayList<Category> getCategoriesInDictionary(Dictionary dictionary) {
        ArrayList<Category> categories = daoCategory.getCategoriesInDictionary(dictionary);
        return categories;
    }

    public ArrayList<Category> getCategoriesInWord(Word word) {
        ArrayList<Category> categories = daoCategory.getCategoriesInWord(word);
        return categories;
    }

    public ArrayList<Word> getWordsInCategory(Category category) {
        ArrayList<Word> words = daoWord.getWordsInCategory(category);
        return words;
    }

    public ArrayList<Word> getWordsInDictionary(Dictionary dictionary) {
        ArrayList<Word> words = daoWord.getWordsInDictionary(dictionary);
        return words;
    }

}
