package com.audreychentech.wordtracker.persistence;

import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;
import android.test.mock.MockContext;

/**
 * Created by audrey on 1/30/15.
 */
public class LetterGroupDbHelperTest extends AndroidTestCase {

    @Override
    public void setUp() throws Exception {
        final String testPrefix = "test.";
        super.setUp();
        MockContentResolver resolver = new MockContentResolver();
        RenamingDelegatingContext contextWrapper = new RenamingDelegatingContext(new MockContext(), getContext(), testPrefix);
        setContext(new IsolatedContext(resolver, contextWrapper));
    }

    public void testCreateAndQueryLetterGroup() {
        LetterGroupDbHelper dbHelper = new LetterGroupDbHelper(getContext());
        final String LETTER_GROUP = "ish";

        try {
            long rowId = dbHelper.createLetterGroup(LETTER_GROUP);
            assertTrue("Row not created, rowId: " + rowId, rowId > -1);

            String queryLetterGroup = dbHelper.queryLetterGroup(LETTER_GROUP);
            assertEquals("Incorrect value from query", LETTER_GROUP, queryLetterGroup);
        } finally {

            dbHelper.deleteLetterGroup(LETTER_GROUP);
        }
    }

    public void testQueryLetterGroupNotExists() {
        LetterGroupDbHelper dbHelper = new LetterGroupDbHelper(getContext());
        String letterGroup = dbHelper.queryLetterGroup("hullo");
        assertNull(letterGroup);
    }
}
