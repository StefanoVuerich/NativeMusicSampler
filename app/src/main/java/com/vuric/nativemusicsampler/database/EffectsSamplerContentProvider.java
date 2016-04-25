package com.vuric.nativemusicsampler.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.vuric.nativemusicsampler.database.helpers.DBHelper;
import com.vuric.nativemusicsampler.database.helpers.SamplesHelper;

/**
 * Created by stefano on 4/25/2016.
 */
public class EffectsSamplerContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.vuric.nativemusicsampler.database.effectssamplercontentprovider";

    // Paths
    public static final String SAMPLES_PATH = "samples";
    /*public static final String SLOTS_PATH = "slots";
    public static final String DECKS_PATH = "decks";
    public static final String EQUALIZER_PATH = "equalizer";
    public static final String MAIN_MIXER_PATH = "mainmixer";*/

    // Uris
    public static final Uri SAMPLES_URI = Uri
            .parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + SAMPLES_PATH);
    /*public static final Uri SLOTS_URI = Uri
            .parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + SLOTS_PATH);
    public static final Uri DECKS_URI = Uri
            .parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + DECKS_PATH);
    public static final Uri EQUALIZER_URI = Uri
            .parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + EQUALIZER_PATH);
    public static final Uri MAIN_MIXER_URI = Uri
            .parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + MAIN_MIXER_PATH);*/

    // Uri's case
    private final static int FULL_SAMPLES_TABLE = 0;
    /*private final static int SINGLE_SAMPLE = 1;
    private final static int FULL_SLOTS_TABLE = 2;
    private final static int SINGLE_SLOT = 3;
    private final static int FULL_DECKS_TABLE = 4;
    private final static int SINGLE_DECK = 5;
    private final static int FULL_EQUALIZER_TABLE = 6;
    private final static int FULL_MAIN_MIXER_TABLE = 7;*/

    private final static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(AUTHORITY, SAMPLES_PATH, FULL_SAMPLES_TABLE);
        /*mUriMatcher.addURI(AUTHORITY, SAMPLES_PATH + "/#", SINGLE_SAMPLE);
        mUriMatcher.addURI(AUTHORITY, SLOTS_PATH, FULL_SLOTS_TABLE);
        mUriMatcher.addURI(AUTHORITY, SLOTS_PATH + "/#", SINGLE_SLOT);
        mUriMatcher.addURI(AUTHORITY, DECKS_PATH, FULL_DECKS_TABLE);
        mUriMatcher.addURI(AUTHORITY, DECKS_PATH + "/#", SINGLE_DECK);
        mUriMatcher.addURI(AUTHORITY, EQUALIZER_PATH, FULL_EQUALIZER_TABLE);
        mUriMatcher.addURI(AUTHORITY, MAIN_MIXER_PATH, FULL_MAIN_MIXER_TABLE);*/
    }

    private DBHelper mHelper;

    // Mime types
    public static final String MIME_TYPE_SAMPLES = ContentResolver.CURSOR_DIR_BASE_TYPE + "/samples";
    public static final String MIME_TYPE_SAMPLE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/sample";
    /*public static final String MIME_TYPE_SLOTS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/slots";
    public static final String MIME_TYPE_SLOT = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/slot";
    public static final String MIME_TYPE_DECKS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/decks";
    public static final String MIME_TYPE_DECK = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/deck";
    public static final String MIME_TYPE_SLOTS_DECK = ContentResolver.CURSOR_DIR_BASE_TYPE + "/slots_decks";
    public static final String MIME_TYPE_SLOT_DECK = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/slot_deck";
    public static final String MIME_TYPE_EQUALIZER = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/equalizer";
    public static final String MIME_TYPE_MAIN_MIXER = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/mainmixer";*/

    @Override
    public boolean onCreate() {
        mHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (mUriMatcher.match(uri)) {

            case FULL_SAMPLES_TABLE:
                queryBuilder.setTables(SamplesHelper.TABLE_NAME);
                break;

            /*case FULL_SLOTS_TABLE:
                queryBuilder.setTables(SlotsHelper.TABLE_NAME);
                break;

            case SINGLE_SLOT:
                queryBuilder.setTables(SlotsHelper.TABLE_NAME);
                queryBuilder.appendWhere(SlotsHelper._ID + "=" + uri.getLastPathSegment());
                break;

            case FULL_DECKS_TABLE:
                queryBuilder.setTables(DecksHelper.TABLE_NAME);
                break;

            case SINGLE_DECK:
                queryBuilder.setTables(DecksHelper.TABLE_NAME);
                break;

            case FULL_EQUALIZER_TABLE:
                queryBuilder.setTables(EqualizerHelper.TABLE_NAME);
                break;

            case FULL_MAIN_MIXER_TABLE:
                queryBuilder.setTables(MainMixerHelper.TABLE_NAME);
                break;*/
        }
        SQLiteDatabase vDB = mHelper.getWritableDatabase();
        Cursor vCursor = queryBuilder.query(vDB, projection, selection, selectionArgs, null, null, sortOrder);
        vCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return vCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase vDB = null;
        long vResult = 0;

        switch (mUriMatcher.match(uri)) {

            case FULL_SAMPLES_TABLE:
                vDB = mHelper.getWritableDatabase();
                vResult = vDB.insert(SamplesHelper.TABLE_NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                break;

            /*case FULL_SLOTS_TABLE:
                vDB = mHelper.getWritableDatabase();
                vResult = vDB.insert(SlotsHelper.TABLE_NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                break;

            case FULL_DECKS_TABLE:
                vDB = mHelper.getWritableDatabase();
                vResult = vDB.insert(DecksHelper.TABLE_NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                break;

            case FULL_EQUALIZER_TABLE:
                vDB = mHelper.getWritableDatabase();
                vResult = vDB.insert(EqualizerHelper.TABLE_NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                break;

            case FULL_MAIN_MIXER_TABLE:
                vDB = mHelper.getWritableDatabase();
                vResult = vDB.insert(MainMixerHelper.TABLE_NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                break;*/
        }
        return Uri.parse("" + vResult);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int vResult = 0;
        SQLiteDatabase vDB = mHelper.getWritableDatabase();

        switch (mUriMatcher.match(uri)) {

            case FULL_SAMPLES_TABLE:
                vResult = vDB.update(SamplesHelper.TABLE_NAME, values, selection, selectionArgs);
                break;

            /*case SINGLE_SAMPLE:
                String vTmp = SamplesHelper._ID + " = " + uri.getLastPathSegment();
                vResult = vDB.update(SamplesHelper.TABLE_NAME, values, selection + " AND " + vTmp, selectionArgs);
                break;

            case FULL_SLOTS_TABLE:
                vResult = vDB.update(SlotsHelper.TABLE_NAME, values, selection, selectionArgs);
                break;

            case FULL_DECKS_TABLE:
                vResult = vDB.update(DecksHelper.TABLE_NAME, values, DecksHelper._ID + " = ?",
                        new String[] { selectionArgs[0] });
                break;

            case FULL_EQUALIZER_TABLE:
                vResult = vDB.update(EqualizerHelper.TABLE_NAME, values, selection, selectionArgs);
                break;

            case FULL_MAIN_MIXER_TABLE:
                vResult = vDB.update(MainMixerHelper.TABLE_NAME, values, selection, selectionArgs);
                break;*/
        }
        if (vResult > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return vResult;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase vDB = mHelper.getWritableDatabase();
        int vResult = 0;

        switch (mUriMatcher.match(uri)) {

            case FULL_SAMPLES_TABLE:
                vResult = vDB.delete(SamplesHelper.TABLE_NAME, selection, selectionArgs);
                break;

            /*case SINGLE_SAMPLE:
                String vTmp = SamplesHelper._ID + " = " + uri.getLastPathSegment();
                vResult = vDB.delete(SamplesHelper.TABLE_NAME, selection + " AND " + vTmp, selectionArgs);
                break;*/
        }
        if (vResult > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return vResult;
    }

    @Override
    public String getType(Uri uri) {
        String vResult = "";
        switch (mUriMatcher.match(uri)) {
            case FULL_SAMPLES_TABLE:
                vResult = MIME_TYPE_SAMPLES;
                break;
            /*case SINGLE_SAMPLE:
                vResult = MIME_TYPE_SAMPLE;
                break;*/
        }
        return vResult;
    }
}
