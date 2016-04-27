package com.vuric.nativemusicsampler.utils;

import android.os.Environment;

/**
 * Created by stefano on 4/5/2016.
 */
public class Constants {

    public static final int SLOTS = 9;
    public static final int DEFAULT_RATE = 50;
    public static final int DEFAULT_VOLUME = 100;
    public static final int SAMPLE_LIST_LEFT_MARGIN = 40;

    public final static String SAMPLE_EFFECT_FOLDER = "SamplerEffects";
    public final static String SAMPLE_EFFECT_FOLDER_PATH = Environment.getExternalStorageDirectory() + "/"
            + SAMPLE_EFFECT_FOLDER;

	/*
	 * SlotCustomLayout
	 */

    public static final int MARGIN = 10;

	/*
	 * REST
	 */

    public static final String ENDPOINT = "http://192.168.1.17/api";
    public static final String IMAGE_ENDPOINT = "/sampleimages";
    public static final String SAMPLES_PATH = "/samples";
    public static final String SAMPLES_INFO_PATH = "/samplesinfo";
    public static final String HASHES_PATH = "/hashes/{hash}";

    public static final String WAKE_LOCK = "WAKE_LOCK";
    public static final String APP_TAG = "NATIIVE_SAMPLER";

    public static final int SWIPE_THRESHOLD = 100;
    public static final int SWIPE_VELOCITY_THRESHOLD = 100;
}
