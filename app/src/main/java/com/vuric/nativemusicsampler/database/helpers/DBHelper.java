package com.vuric.nativemusicsampler.database.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by stefano on 4/25/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "effectsamplerdb.db";
    private final static int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SamplesHelper.CREATE_QUERY);
        //db.execSQL(SlotsHelper.CREATE_QUERY);
        //db.execSQL(DecksHelper.CREATE_QUERY);
        //db.execSQL(EqualizerHelper.CREATE_QUERY);
        //db.execSQL(MainMixerHelper.CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
