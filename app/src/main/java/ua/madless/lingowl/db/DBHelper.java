package ua.madless.lingowl.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.sql.SQLException;

import ua.madless.lingowl.db.dao.DaoCategory;
import ua.madless.lingowl.db.dao.DaoDictCat;
import ua.madless.lingowl.db.dao.DaoDictWord;
import ua.madless.lingowl.db.dao.DaoDictionary;
import ua.madless.lingowl.db.dao.DaoWord;
import ua.madless.lingowl.db.dao.DaoCatWord;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    private SQLiteDatabase database;
    private String dbPath;
    private final static String DB_DEBUG_NAME = "lingowl_debug0.sqlite";
    private final static String DB_RELEASE_NAME = "lingowl.sqlite";
    private final static String DB_NAME = DB_DEBUG_NAME;


    private final static int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        dbPath = context.getApplicationInfo().dataDir + "/databases/";
        this.context = context;
    }

    public void createDataBase() throws IOException {
        Log.d("mylog", "createDataBase()");
        if(!isDatabaseExist()) {
            Log.d("mylog", "!isDatabaseExist()");
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFromExternal();
            }
            catch (IOException mIOException) {
                //throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    public void createDataBaseFromScratch() {
        if(!isDatabaseExist()) {
            this.getReadableDatabase();
            this.close();
        }
    }

    private boolean isDatabaseExist() {
        File dbFile = new File(dbPath + DB_NAME);
        return dbFile.exists();
    }

    private void copyDBFromExternal() throws IOException {
        InputStream mInput = context.getAssets().open(DB_NAME);
        OutputStream mOutput = new FileOutputStream(dbPath + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    private void copyDBToExternal() throws IOException {
        File sd = Environment.getExternalStorageDirectory();
        if (sd.canWrite()) {
            if (isDatabaseExist()) {
                FileChannel src = new FileInputStream(dbPath + DB_NAME).getChannel();
                FileChannel dst = new FileOutputStream(Environment.getExternalStorageDirectory()).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Log.d("mylog", "copied!");
            }
        }

    }

    public boolean openDatabase() throws SQLException {
        String mPath = dbPath + DB_NAME;
        database = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return database != null;
    }

    @Override
    public synchronized void close() {
        if(database != null) {
            database.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("mylog", "On create db");
        db.execSQL(DaoDictionary.QUERY_CREATE_TABLE);
        db.execSQL(DaoWord.QUERY_CREATE_TABLE);
        db.execSQL(DaoCategory.QUERY_CREATE_TABLE);
        db.execSQL(DaoDictWord.QUERY_CREATE_LINK_TABLE);
        db.execSQL(DaoDictCat.QUERY_CREATE_LINK_TABLE);
        db.execSQL(DaoCatWord.QUERY_CREATE_LINK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
