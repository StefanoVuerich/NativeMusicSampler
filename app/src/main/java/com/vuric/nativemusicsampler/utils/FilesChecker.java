package com.vuric.nativemusicsampler.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.vuric.nativemusicsampler.BusStation;
import com.vuric.nativemusicsampler.database.EffectsSamplerContentProvider;
import com.vuric.nativemusicsampler.database.helpers.SamplesHelper;
import com.vuric.nativemusicsampler.events.SamplesDirectoryCheckedEvt;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Check the files in the app samples foleder
 */
public class FilesChecker extends AsyncTask<Void, Void, Boolean> {

    private Context context;

    public FilesChecker(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        BusStation.getBus().register(this);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return checkFiles();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        BusStation.getBus().post(new SamplesDirectoryCheckedEvt());

        BusStation.getBus().unregister(this);

        Log.v(Constants.APP_TAG,"Finish checking files");
    }

    public boolean checkFiles() {

        Log.v(Constants.APP_TAG,"Start checking files");

        File samplesFolder = new File(Environment.getExternalStorageDirectory() + "/SamplerEffects");

        if (samplesFolder.exists() && samplesFolder.isDirectory()) {

            File[] samples = samplesFolder.listFiles();

            Set<Integer> IDS = new HashSet<Integer>();
            ContentResolver contentResolver = context.getContentResolver();
            if(contentResolver == null)
                return false;

            for (File sample : samples) {

                String nextSampleHash = FileHasher.getHash(sample.getAbsolutePath());
                Cursor nextSampleCursor = null;
                try {
                    nextSampleCursor = contentResolver.query(EffectsSamplerContentProvider.SAMPLES_URI,
                            new String[] { SamplesHelper.NAME, SamplesHelper._ID }, SamplesHelper.MD5 + " = ?",
                            new String[] { nextSampleHash }, null);

                    if(nextSampleCursor == null) {
                        continue;
                    }

                    if (nextSampleCursor.moveToFirst()) {
                        // sample is already in database
                        IDS.add(nextSampleCursor.getInt(nextSampleCursor.getColumnIndex(SamplesHelper._ID)));

                    } else {
                        // sample is still not in the database, add it


                        String sampleName = sample.getName();

                        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                        mediaMetadataRetriever.setDataSource(context, Uri.fromFile(sample));

                        String artist = (String) mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                        String title = (String) mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                        String bitRate = (String) mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);
                        String durationMs = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                        String mime = (String) mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);

                        long duration = Long.parseLong(durationMs) / 1000;
                        long hours = duration / 3600;
                        long minutes = (duration - hours * 3600) / 60;
                        long seconds = duration - (hours * 3600 + minutes * 60);

                        ContentValues values = new ContentValues();
                        values.put(SamplesHelper.MD5, nextSampleHash);
                        values.put(SamplesHelper.NAME, sampleName.substring(0, sampleName.lastIndexOf(".")));
                        values.put(SamplesHelper.ARTIST, artist);
                        values.put(SamplesHelper.TITLE, title);
                        values.put(SamplesHelper.PATH, sample.getAbsolutePath());
                        values.put(SamplesHelper.SIZE, sample.length());
                        values.put(SamplesHelper.BITRATE, Integer.parseInt(bitRate));
                        if(hours > 0) {
                            values.put(SamplesHelper.HOURS, hours);
                        }
                        if(minutes > 0) {
                            values.put(SamplesHelper.MINUTES, minutes);
                        }
                        values.put(SamplesHelper.SECONDS, seconds);
                        values.put(SamplesHelper.RATE, 0);
                        values.put(SamplesHelper.PLAYED, 0);
                        values.put(SamplesHelper.FORMAT, sampleName.substring(sampleName.lastIndexOf(".") + 1));

                        Uri insertedRowId = contentResolver.insert(EffectsSamplerContentProvider.SAMPLES_URI, values);
                        String id = insertedRowId.toString();
                        IDS.add(Integer.parseInt(id));
                    }
                } finally {
                    if(nextSampleCursor != null) {
                        nextSampleCursor.close();
                    }
                }
            }

            Cursor allFilesCursor = null;
            try {
                allFilesCursor = contentResolver.query(EffectsSamplerContentProvider.SAMPLES_URI, null, null, null,null);

                while (allFilesCursor.moveToNext()) {

                    int id = allFilesCursor.getInt(allFilesCursor.getColumnIndex(SamplesHelper._ID));
                    if (IDS.contains(id)) {
                        // do nothing
                    } else {
                        int rowDeleted = contentResolver.delete(EffectsSamplerContentProvider.SAMPLES_URI,
                                SamplesHelper._ID + " = ?", new String[] { "" + id });
                    }
                }
            } finally {
                if(allFilesCursor != null) {
                    allFilesCursor.close();
                }
            }
        }

        return true;
    }
}
