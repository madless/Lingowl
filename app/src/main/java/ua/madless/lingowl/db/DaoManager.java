package ua.madless.lingowl.db;

import ua.madless.lingowl.db.dao.DaoCategory;
import ua.madless.lingowl.db.dao.DaoDictionary;
import ua.madless.lingowl.db.dao.DaoWord;
import ua.madless.lingowl.model.Category;
import ua.madless.lingowl.model.Dictionary;
import ua.madless.lingowl.model.Word;

/**
 * Created by madless on 07.01.2016.
 */
public class DaoManager {
    DBManager dbManager;
    DaoWord daoWord;
    DaoDictionary daoDictionary;
    DaoCategory daoCategory;


    public DaoManager(DBManager dbManager) {
        this.dbManager = dbManager;
        daoDictionary = new DaoDictionary(dbManager);
        daoCategory = new DaoCategory(dbManager);
        daoWord = new DaoWord(dbManager);
    }

    public void addWord(Dictionary dictionary, Word word, Category category) {
        daoWord.addWord(dictionary, word);
        daoDictionary.incrementWordCount(dictionary);
        daoCategory.joinCategoryAndWord(category, word);
        daoCategory.incrementWordCount(category);
    }

}
