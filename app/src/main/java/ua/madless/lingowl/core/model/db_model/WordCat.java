package ua.madless.lingowl.core.model.db_model;

/**
 * Created by madless on 02.01.2016.
 */
public class WordCat {
    int idWord;
    int idCat;

    public WordCat(int idWord, int idCat) {
        this.idWord = idWord;
        this.idCat = idCat;
    }
}
