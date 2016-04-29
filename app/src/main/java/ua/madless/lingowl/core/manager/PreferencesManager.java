package ua.madless.lingowl.core.manager;

import android.content.Context;
import android.content.SharedPreferences;

import ua.madless.lingowl.core.constants.Constants;
import ua.madless.lingowl.core.model.db_model.Dictionary;

/**
 * Created by User on 29.04.2016.
 */
public class PreferencesManager {
    private static PreferencesManager instance;
    private SharedPreferences preferences;
    private Context context;

    private PreferencesManager() {}

    public static PreferencesManager getInstance() {
        if(instance == null) {
            instance = new PreferencesManager();
        }
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void saveDictionaryId(Dictionary dictionary) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.APP_PREFERENCES_DICTIONARY_ID, dictionary.getId());
        editor.apply();
    }

    public int loadDictionaryId() {
        return preferences.getInt(Constants.APP_PREFERENCES_DICTIONARY_ID, -1);
    }

    public boolean isFirstLaunch() {
        boolean isFirstLaunch = preferences.getBoolean(Constants.APP_PREFERENCES_IS_FIRST_LAUNCH, true);
        if(isFirstLaunch) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(Constants.APP_PREFERENCES_IS_FIRST_LAUNCH, false);
            editor.apply();
        }
        return  isFirstLaunch;
    }
}
