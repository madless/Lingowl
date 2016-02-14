package ua.madless.lingowl.model.converted_server_response;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 14.02.2016.
 */
public class ConvertedTranslation {
    private String word;
    private ArrayList<String> translations;
    private HashMap<Integer, String> translationsToPartsOfSpeech;
    private HashMap<Integer, String> translationsToGenders;

    public void addNewTranslation(String translation, String partOfSpeech, String gender) {
        translations.add(translation);
        int translationId = translations.size() - 1;
        translationsToPartsOfSpeech.put(translationId, partOfSpeech);
        translationsToGenders.put(translationId, gender);
    }

    // TODO: 14.02.2016 ALL GETTERS!! (GET PART AND GENDER BY TRANSLATION)


}
