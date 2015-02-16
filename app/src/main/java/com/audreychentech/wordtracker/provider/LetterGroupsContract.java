package com.audreychentech.wordtracker.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by audrey on 1/30/15.
 */
public class LetterGroupsContract {

    private LetterGroupsContract() {}

    public static final String AUTHORITY = "com.audreychentech.wordtracker.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    /* Inner class defines LETTER_GROUPS table contents */
    public static abstract class LetterGroup implements BaseColumns {

        static final String BASE_PATH = "letterGroups";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(LetterGroupsContract.CONTENT_URI, BASE_PATH);

        public static final String TABLE_NAME = "LETTER_GROUPS";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_LETTER_GROUP = "LETTER_GROUP_CODE";
    }

    /* Inner class defines LETTER_GROUP_WORDS table contents */
    public static abstract class LetterGroupWord implements BaseColumns {
        static final String BASE_PATH = "letterGroupWords";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(LetterGroupsContract.CONTENT_URI, BASE_PATH);
        public static final String TABLE_NAME = "LETTER_GROUP_WORDS";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_LETTER_GROUP = "LETTER_GROUP";
        public static final String COLUMN_NAME_WORD = "WORD";

    }

}
