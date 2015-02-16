package com.audreychentech.wordtracker.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.audreychentech.wordtracker.provider.LetterGroupsContract;

/**
 * Created by audrey on 1/30/15.
 */
public class LetterGroupDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "LetterGroup.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String NOT_NULL = "";
    private static final String PRIMARY_KEY_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String SQL_CREATE_LETTER_GROUP_ENTRIES =
            "CREATE TABLE " + LetterGroupsContract.LetterGroup.TABLE_NAME + " (" +
                    LetterGroupsContract.LetterGroup.COLUMN_NAME_ID + PRIMARY_KEY_TYPE + COMMA_SEP +
                    LetterGroupsContract.LetterGroup.COLUMN_NAME_LETTER_GROUP + TEXT_TYPE + NOT_NULL +
            " )";

    private static final String SQL_CREATE_LETTER_GROUP_WORD_ENTRIES =
            "CREATE TABLE " + LetterGroupsContract.LetterGroupWord.TABLE_NAME + " (" +
                    LetterGroupsContract.LetterGroupWord.COLUMN_NAME_ID + PRIMARY_KEY_TYPE + COMMA_SEP +
                    LetterGroupsContract.LetterGroupWord.COLUMN_NAME_LETTER_GROUP + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    LetterGroupsContract.LetterGroupWord.COLUMN_NAME_WORD + TEXT_TYPE + NOT_NULL +
                    " )";

    private static final String SQL_DELETE_LETTER_GROUP_ENTRIES =
            "DROP TABLE IF EXISTS " + LetterGroupsContract.LetterGroup.TABLE_NAME;

    private static final String SQL_DELETE_LETTER_GROUP_WORD_ENTRIES =
            "DROP TABLE IF EXISTS " + LetterGroupsContract.LetterGroupWord.TABLE_NAME;


    public LetterGroupDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_LETTER_GROUP_ENTRIES);
        db.execSQL(SQL_DELETE_LETTER_GROUP_WORD_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_LETTER_GROUP_ENTRIES);
        db.execSQL(SQL_DELETE_LETTER_GROUP_WORD_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long createLetterGroup(String letterGroup) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LetterGroupsContract.LetterGroup.COLUMN_NAME_LETTER_GROUP, letterGroup);
        return db.insert(LetterGroupsContract.LetterGroup.TABLE_NAME, null, values);
    }

    public String queryLetterGroup(String letterGroup) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projections = {LetterGroupsContract.LetterGroup.COLUMN_NAME_LETTER_GROUP};
        String selection = LetterGroupsContract.LetterGroup.COLUMN_NAME_LETTER_GROUP + " LIKE ?";
        String[] selectionArgs = {letterGroup};
        Cursor c = db.query(LetterGroupsContract.LetterGroup.TABLE_NAME,
                projections, selection, selectionArgs, null, null, null);
        if (!c.moveToFirst()) {
            return null;
        }
        return c.getString(c.getColumnIndexOrThrow(LetterGroupsContract.LetterGroup.COLUMN_NAME_LETTER_GROUP));
    }

    public void deleteLetterGroup(String letterGroup) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = LetterGroupsContract.LetterGroup.COLUMN_NAME_LETTER_GROUP + " LIKE ?";
        String[] selectionArgs = {letterGroup};
        db.delete(LetterGroupsContract.LetterGroup.TABLE_NAME, selection, selectionArgs);
    }

    public Cursor queryAllLetterGroups() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projections = {LetterGroupsContract.LetterGroup.COLUMN_NAME_LETTER_GROUP};
        Cursor c = db.query(LetterGroupsContract.LetterGroup.TABLE_NAME,
                projections, null, null, null, null, null);
        return c;

    }

}
