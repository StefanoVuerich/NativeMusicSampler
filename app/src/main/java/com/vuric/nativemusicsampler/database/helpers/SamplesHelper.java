package com.vuric.nativemusicsampler.database.helpers;

    import android.provider.BaseColumns;

    /**
     * Created by stefano on 4/25/2016.
     */
    public class SamplesHelper implements BaseColumns {

        public static final String TABLE_NAME = "samples";
        public static final String MD5 = "md5";
        public static final String NAME = "name";
        public static final String ARTIST = "artist";
        public static final String TITLE = "title";
        public static final String PATH = "path";
        public static final String SIZE = "size";
        public static final String RATE = "rate";
        public static final String BITRATE = "bitrate";
        public static final String PLAYED = "played";
        public static final String FORMAT = "format";
        public static final String HOURS = "hours";
        public static final String MINUTES = "minutes";
        public static final String SECONDS = "seconds";

    public static final String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MD5 + " TEXT NOT NULL, "
            + NAME + " TEXT NOT NULL, "
            + ARTIST + " TEXT, "
            + TITLE + " TEXT, "
            + PATH + " TEXT NOT NULL, "
            + SIZE + " INTEGER NOT NULL, "
            + BITRATE + " INTEGER NOT NULL, "
            + HOURS + " INTEGER, "
            + MINUTES + " INTEGER, "
            + SECONDS + " INTEGER NOT NULL, "
            + RATE + " REAL NOT NULL, "
            + PLAYED + " INTEGER NOT NULL, "
            + FORMAT + " TEXT NOT NULL )";
}
