package ua.madless.lingowl.core.util;

/**
 * Created by User on 21.02.2016.
 */
public class StringConverter {

    public static String getShorterPartOfSpeech(String partOfSpeech) {
        // TODO: 29.04.2016 Translate all returns
        switch (partOfSpeech) {
            case "существительное": {
                return "сущ.";
            }
            case "прилагательное": {
                return "прил.";
            }
            case "глагол" : {
                return "глаг.";
            }
            default: {
                return partOfSpeech; // TODO: 29.04.2016 IMPLEMENT ALL 
            }
        }
    }

    public static String getShorterGender(String gender) {
        return gender;
    }
}
