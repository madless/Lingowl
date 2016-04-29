package ua.madless.lingowl.ui.app;

import android.app.Application;

import ua.madless.lingowl.R;
import ua.madless.lingowl.core.constants.Constants;
import ua.madless.lingowl.core.manager.PreferencesManager;
import ua.madless.lingowl.core.model.db_model.Dictionary;
import ua.madless.lingowl.db.DbApi;

/**
 * Created by User on 29.04.2016.
 */
public class LingowlApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesManager.getInstance().setContext(this);
        DbApi dbApi = DbApi.getInstance();
        dbApi.setContext(this);
        if(PreferencesManager.getInstance().isFirstLaunch()) {
            Dictionary[] dictionaries = new Dictionary[]{
                    new Dictionary(getString(R.string.dictionary_english), Constants.LANG_ENGLISH, Constants.LANG_RUSSIAN, 0, 0),
                    new Dictionary(getString(R.string.dictionary_german), Constants.LANG_GERMAN, Constants.LANG_RUSSIAN, 1, 0),
                    new Dictionary(getString(R.string.dictionary_spanish), Constants.LANG_SPANISH, Constants.LANG_RUSSIAN, 4, 0),
                    new Dictionary(getString(R.string.dictionary_french), Constants.LANG_FRENCH, Constants.LANG_RUSSIAN, 2, 0)
            };
            for (int i = 0; i < dictionaries.length; i++) {
                dbApi.addDictionary(dictionaries[i]);
            }
        }
    }
}
