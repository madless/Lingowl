package ua.madless.lingowl.core.util;

/**
 * Created by User on 21.02.2016.
 */
public class StringConverter {

    public static String getShorterPartOfSpeech(String partOfSpeech) {
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
                return "NOT IMPL";
            }
        }
    }

    public static String getShorterGender(String gender) {
        return gender;
    }
}
