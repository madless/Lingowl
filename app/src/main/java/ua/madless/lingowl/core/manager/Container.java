package ua.madless.lingowl.core.manager;

import ua.madless.lingowl.core.holder.SettingsHolder;
import ua.madless.lingowl.core.util.ServerResponseConverter;
import ua.madless.lingowl.db.DbApi;
import ua.madless.lingowl.net.QueryManager;

/**
 * Created by User on 14.02.2016.
 */
public class Container {
    private static Container instance;

    private Container() {}

    public static Container getInstance() {
        if(instance == null) {
            instance = new Container();
        }
        return instance;
    }

    public DbApi getDbApi() {
        return DbApi.getInstance();
    }

    public QueryManager getQueryManager() {
        return QueryManager.getInstance();
    }

    public SettingsHolder getSettings() {
        return SettingsHolder.getInstance();
    }

    public ServerResponseConverter getResponseConverter() {
        return ServerResponseConverter.getInstance();
    }

    public PreferencesManager getPreferencesManager() {
        return PreferencesManager.getInstance();
    }
}
