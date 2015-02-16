package com.audreychentech.wordtracker.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.audreychentech.wordtracker.persistence.LetterGroupDbHelper;

/**
 * Created by audrey on 2/14/15.
 */
public class LetterGroupsProvider extends ContentProvider {

    private LetterGroupDbHelper letterGroupDbHelper;


    // constants used by UriMatcher
    private static final int LETTER_GROUPS = 1;
    private static final int LETTER_GROUP_ID = 2;
    private static final int LETTER_GROUP_WORDS = 3;
    private static final int LETTER_GROUP_WORD_ID = 4;

    private static final String LETTER_GROUPS_BASE_PATH = "letterGroups";
    private static final String LETTER_GROUP_WORDS_BASE_PATH = "letterGroupWords";


    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(LetterGroupsContract.AUTHORITY, LetterGroupsContract.LetterGroup.BASE_PATH, LETTER_GROUPS);
        sUriMatcher.addURI(LetterGroupsContract.AUTHORITY, LetterGroupsContract.LetterGroup.BASE_PATH + "/#", LETTER_GROUP_ID);
        //sUriMatcher.addURI(LetterGroupsContract.AUTHORITY, LetterGroupsContract.LetterGroupWord.BASE_PATH, LETTER_GROUP_WORDS);
        //sUriMatcher.addURI(LetterGroupsContract.AUTHORITY, LetterGroupsContract.LetterGroupWord.BASE_PATH + "/#", LETTER_GROUP_WORD_ID);
    }


    @Override
    public boolean onCreate() {
        letterGroupDbHelper = new LetterGroupDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        int uriType = sUriMatcher.match(uri);
        switch (uriType) {
            case LETTER_GROUPS:
                queryBuilder.setTables(LetterGroupsContract.LetterGroup.TABLE_NAME);
                break;
            case LETTER_GROUP_ID:
                queryBuilder.setTables(LetterGroupsContract.LetterGroup.TABLE_NAME);
                queryBuilder.appendWhere(LetterGroupsContract.LetterGroup.COLUMN_NAME_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = letterGroupDbHelper.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = letterGroupDbHelper.getWritableDatabase();
        long id = -1;
        int uriType = sUriMatcher.match(uri);
        switch (uriType) {
            case LETTER_GROUPS:
                id = db.insertOrThrow(LetterGroupsContract.LetterGroup.TABLE_NAME, null, values);
                break;
            case LETTER_GROUP_WORDS:
                id = db.insertOrThrow(LetterGroupsContract.LetterGroupWord.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = letterGroupDbHelper.getWritableDatabase();
        int nDeletedRows = 0;
        int uriType = sUriMatcher.match(uri);
        switch (uriType) {
            case LETTER_GROUPS:
                nDeletedRows = db.delete(LetterGroupsContract.LetterGroup.TABLE_NAME, selection, selectionArgs);
                break;
            case LETTER_GROUP_ID:
                String id = uri.getLastPathSegment();
                String idSelection = LetterGroupsContract.LetterGroup.COLUMN_NAME_ID + " = " + id;
                if (TextUtils.isEmpty(selection)) {
                    nDeletedRows = db.delete(LetterGroupsContract.LetterGroup.TABLE_NAME, idSelection, null);

                } else {
                    nDeletedRows = db.delete(LetterGroupsContract.LetterGroup.TABLE_NAME,
                            idSelection + " AND " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }
        getContext().getContentResolver().notifyChange(uri,null);
        return nDeletedRows;
    }

    @Override
    public int update(Uri uri,ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = letterGroupDbHelper.getWritableDatabase();
        int nUpdatedRows = 0;
        int uriType = sUriMatcher.match(uri);
        switch (uriType) {
            case LETTER_GROUPS:
                nUpdatedRows = db.update(LetterGroupsContract.LetterGroup.TABLE_NAME, values, selection, selectionArgs);
                break;
            case LETTER_GROUP_ID:
                String id = uri.getLastPathSegment();
                String idSelection = LetterGroupsContract.LetterGroup.COLUMN_NAME_ID + " = " + id;
                if (TextUtils.isEmpty(selection)) {
                    nUpdatedRows = db.update(LetterGroupsContract.LetterGroup.TABLE_NAME, values, idSelection, null);
                } else {
                    nUpdatedRows = db.update(LetterGroupsContract.LetterGroup.TABLE_NAME, values,
                            idSelection +  " AND " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return nUpdatedRows;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public void shutdown() {
        if (letterGroupDbHelper != null) {
            letterGroupDbHelper.close();
        }
    }
}
