package com.audreychentech.wordtracker.persistence;

import android.provider.BaseColumns;

/**
 * Created by audrey on 1/30/15.
 */
public class LetterGroupReaderContract {

    private LetterGroupReaderContract() {}

    /* Inner class defines LETTER_GROUPS table contents */
    public static abstract class LetterGroupEntry implements BaseColumns {
        public static final String TABLE_NAME = "LETTER_GROUPS";
        public static final String COLUMN_NAME_ID = "_ID";
        public static final String COLUMN_NAME_LETTER_GROUP = "LETTER_GROUP_CODE";
    }

    /* Inner class defines LETTER_GROUP_WORDS table contents */
    public static abstract class LetterGroupWordEntry implements BaseColumns {
        public static final String TABLE_NAME = "LETTER_GROUP_WORDS";
        public static final String COLUMN_NAME_ID = "_ID";
        public static final String COLUMN_NAME_LETTER_GROUP = "LETTER_GROUP";
        public static final String COLUMN_NAME_WORD = "WORD";

    }

}
