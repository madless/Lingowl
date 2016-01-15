package ua.madless.lingowl.db.dao;

/**
 * Created by madless on 02.01.2016.
 */
public class DaoCatWord {
    public final static String LINK_TABLE_NAME = "cat_word";
    public final static String LINK_FIELD_ID_CAT = "id_cat";
    public final static String LINK_FIELD_ID_WORD = "id_word";
    public final static String QUERY_CREATE_LINK_TABLE = "CREATE TABLE " + LINK_TABLE_NAME +
            " ( " +
                LINK_FIELD_ID_CAT + " integer, " +
                LINK_FIELD_ID_WORD + " integer " +
            " );";
}
