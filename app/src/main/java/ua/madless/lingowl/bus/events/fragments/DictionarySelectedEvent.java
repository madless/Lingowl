package ua.madless.lingowl.bus.events.fragments;

import ua.madless.lingowl.core.model.db_model.Dictionary;

/**
 * Created by User on 29.04.2016.
 */
public class DictionarySelectedEvent {
    private Dictionary dictionary;

    public DictionarySelectedEvent(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }
}
