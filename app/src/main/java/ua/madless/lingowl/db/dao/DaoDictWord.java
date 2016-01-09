package ua.madless.lingowl.db.dao;

/**
 * Created by madless on 02.01.2016.
 */
public class DaoDictWord {
    public final static String LINK_TABLE_NAME = "dict_word";
    public final static String LINK_FIELD_ID_WORD = "id_word";
    public final static String LINK_FIELD_ID_DICT = "id_dict";
    public final static String QUERY_CREATE_LINK_TABLE = "CREATE TABLE " + LINK_TABLE_NAME +
            " ( " +
            LINK_FIELD_ID_DICT + " integer, " +
            LINK_FIELD_ID_WORD + " integer " +
            " );";
}
