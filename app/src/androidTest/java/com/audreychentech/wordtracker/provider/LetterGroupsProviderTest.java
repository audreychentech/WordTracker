package com.audreychentech.wordtracker.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.util.Log;

/**
 * Created by audrey on 2/15/15.
 */
public class LetterGroupsProviderTest  extends ProviderTestCase2<LetterGroupsProvider> {

    private final String TAG = LetterGroupsProviderTest.class.getName();

    public LetterGroupsProviderTest() {
        super(LetterGroupsProvider.class, LetterGroupsContract.AUTHORITY);
    }

    public void testInsertQueryDeleteLetterGroup() {
        Log.v(TAG, "Testing Create Letter Group");

        String letterGroup = "ish";
        ContentResolver resolver = this.getMockContentResolver();
        ContentValues values = new ContentValues();
        values.put(LetterGroupsContract.LetterGroup.COLUMN_NAME_LETTER_GROUP, letterGroup);
        Uri insertUri = resolver.insert(LetterGroupsContract.LetterGroup.CONTENT_URI, values);
        int id = Integer.valueOf(insertUri.getLastPathSegment());
        assertTrue("Insert letter group failed: " + letterGroup, id > 0);

        Log.v(TAG, "Testing Query Letter Group");
        String[] projections = {LetterGroupsContract.LetterGroup.COLUMN_NAME_ID, LetterGroupsContract.LetterGroup.COLUMN_NAME_LETTER_GROUP};
        String selection = LetterGroupsContract.LetterGroup.COLUMN_NAME_LETTER_GROUP + " LIKE ?";
        String[] selectionArgs = {letterGroup};
        Cursor c = resolver.query(LetterGroupsContract.LetterGroup.CONTENT_URI,
                projections, selection, selectionArgs, null);
        if (!c.moveToFirst()) {
            fail("Query for letter group failed: " + letterGroup);
        } else {
            assertEquals(letterGroup, c.getString(c.getColumnIndexOrThrow(LetterGroupsContract.LetterGroup.COLUMN_NAME_LETTER_GROUP)));
        }

        Log.v(TAG, "Testing Delete Letter Group");
        int nDeleted = resolver.delete(LetterGroupsContract.LetterGroup.CONTENT_URI, selection, selectionArgs);
        Log.v(TAG, "Deleted: " + nDeleted);
        assertEquals(nDeleted, 1);


    }

}
