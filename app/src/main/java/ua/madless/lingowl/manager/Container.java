package ua.madless.lingowl.manager;

import android.content.Context;

import ua.madless.lingowl.db.DbApi;
import ua.madless.lingowl.holder.SettingsHolder;
import ua.madless.lingowl.net.QueryManager;

/**
 * Created by User on 14.02.2016.
 */
public class Container {
    private static Container instance;
    private DbApi dbApi;
    private QueryManager queryManager;
    private ServerResponseConverter responseConverter;

    private SettingsHolder settings;

    private Container() {}

    public static Container getInstance() {
        if(instance == null) {
            instance = new Container();
        }
        return instance;
    }

    public DbApi getDbApi(Context context) {
        if(dbApi == null) {
            dbApi = DbApi.getInstance(context);
        }
        return dbApi;
    }

    public QueryManager getQueryManager() {
        if(queryManager == null) {
            queryManager = QueryManager.getInstance();
        }
        return queryManager;
    }

    public SettingsHolder getSettings() {
        if(settings == null) {
            settings = new SettingsHolder();
        }
        return settings;
    }

    public ServerResponseConverter getResponseConverter() {
        if(responseConverter == null) {
            responseConverter = ServerResponseConverter.getInstance();
        }
        return responseConverter;
    }
}
