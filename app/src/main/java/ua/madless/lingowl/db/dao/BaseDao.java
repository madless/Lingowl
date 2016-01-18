package ua.madless.lingowl.db.dao;

import ua.madless.lingowl.db.DBManager;

/**
 * Created by User on 18.01.2016.
 */
public abstract class BaseDao {
    public DBManager dbManager;
    public abstract String getTableName();
}
