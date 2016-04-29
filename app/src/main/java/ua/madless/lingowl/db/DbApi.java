package ua.madless.lingowl.db;

import android.content.Context;

import java.util.ArrayList;

import ua.madless.lingowl.db.dao.link.DaoCatWord;
import ua.madless.lingowl.db.dao.link.DaoDictCat;
import ua.madless.lingowl.db.dao.link.DaoDictWord;
import ua.madless.lingowl.db.dao.real.DaoCategory;
import ua.madless.lingowl.db.dao.real.DaoDictionary;
import ua.madless.lingowl.db.dao.real.DaoWord;
import ua.madless.lingowl.core.model.db_model.Category;
import ua.madless.lingowl.core.model.db_model.Dictionary;
import ua.madless.lingowl.core.model.db_model.Word;

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

    private static DbApi instance;
    private Context context;

    public static DbApi getInstance() {
        if(instance == null) {
            instance = new DbApi();
        }
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
        this.dbManager = new DBManager(context);
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
        daoDictionary.incrementWordCount(dictionary, 1);
        daoCategory.incrementWordCount(category, 1);
        daoCatWord.linkCategoryWithWord(category, word);
        daoDictWord.linkDictionaryWithWord(dictionary, word);
    }

    public void addWord(Dictionary dictionary, ArrayList<? extends Category> categories, Word word) {
        int wordId = daoWord.getNextId();
        word.setId(wordId);
        daoWord.addWord(word);
        daoDictionary.incrementWordCount(dictionary, 1);
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            daoCategory.incrementWordCount(category, 1);
            daoCatWord.linkCategoryWithWord(category, word);
        }
        daoDictWord.linkDictionaryWithWord(dictionary, word);
    }

    public void removeWordFromAllCategories(Dictionary dictionary, Word word) {
        ArrayList<Category> categories = daoCategory.getCategoriesInWord(word);
        for (Category category: categories) {
            daoCategory.decrementWordCount(category, 1);
            daoCatWord.unlinkCategoryWithWord(category, word);
        }
        daoDictionary.decrementWordCount(dictionary, 1);
        daoDictWord.unlinkDictionaryWithWord(dictionary, word);
        daoWord.deleteWord(word);
    }

    public void removeWordFromCurrentCategory(Dictionary dictionary, Category category, Word word) {
        daoCategory.decrementWordCount(category, 1);
        daoCatWord.unlinkCategoryWithWord(category, word);
        ArrayList<Category> categories = daoCategory.getCategoriesInWord(word);
        if(categories.isEmpty()) {
            daoDictionary.decrementWordCount(dictionary, 1);
            daoDictWord.unlinkDictionaryWithWord(dictionary, word);
            daoWord.deleteWord(word);
        }
    }

    public void removeCategoryWithAllWords(Dictionary dictionary, Category category) {
        ArrayList<Word> words = daoWord.getWordsInCategory(category);
        for (Word word: words) {
            removeWordFromCurrentCategory(dictionary, category, word);
        }
        daoDictCat.unlinkDictionaryWithCategory(dictionary, category);
        daoCategory.deleteCategory(category);
    }

    public void removeCategoryWithoutAllWords(Dictionary dictionary, Category category) {
        ArrayList<Word> words = daoWord.getWordsInCategory(category);
        for (Word word: words) {
            daoCatWord.unlinkCategoryWithWord(category, word);
        }
        daoDictCat.unlinkDictionaryWithCategory(dictionary, category);
        daoCategory.deleteCategory(category);
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

    public Dictionary getDictionaryById(int id) {
        return daoDictionary.getDictionaryById(id);
    }

}
