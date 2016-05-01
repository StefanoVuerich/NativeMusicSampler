package com.vuric.nativemusicsampler.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vuric.nativemusicsampler.R;
import com.vuric.nativemusicsampler.adapters.SamplesCursorAdapter;
import com.vuric.nativemusicsampler.database.EffectsSamplerContentProvider;
import com.vuric.nativemusicsampler.database.helpers.SamplesHelper;
import com.vuric.nativemusicsampler.utils.FileHasher;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by stefano on 4/25/2016.
 */
public class SlotLoadFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String _TAG = SlotLoadFragment.class.getSimpleName();
    public static final String CURRENT_SELECTED_SLOT_ID = "CURRENT_SELECTED_SLOT_ID";
    private int _currentSelectedSlotID;

    public static SlotLoadFragment getInstance() {
        return new SlotLoadFragment();
    }
    private SamplesCursorAdapter cursorAdapter;

    public static Fragment newInstance(int currentSelectedSlotID) {

        Bundle args = new Bundle();
        args.putInt(CURRENT_SELECTED_SLOT_ID, currentSelectedSlotID);
        Fragment fragment = new SlotLoadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _currentSelectedSlotID = getArguments().getInt(CURRENT_SELECTED_SLOT_ID);
        checkFiles();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.slot_load_fragment_layout, container, false);

        ListView l = (ListView) rootView.findViewById(R.id.samplesListView);
        cursorAdapter = new SamplesCursorAdapter(getActivity(), null, _currentSelectedSlotID);
        l.setAdapter(cursorAdapter);
        //samplerSlotsBaseContainer.setMyAdapter(cursorAdapter);
        getLoaderManager().initLoader(0, null, this);

        return rootView;
    }

    private void checkFiles() {

        File samplesFolder = new File(Environment.getExternalStorageDirectory() + "/SamplerEffects");

        if (samplesFolder.exists() && samplesFolder.isDirectory()) {

                File[] samples = samplesFolder.listFiles();

                Set<Integer> IDS = new HashSet<Integer>();
                ContentResolver contentResolver = getActivity().getContentResolver();

                for (File sample : samples) {

                    String cSampleHash = FileHasher.getHash(sample.getAbsolutePath());

                    Cursor cursor = contentResolver.query(EffectsSamplerContentProvider.SAMPLES_URI,
                            new String[] { SamplesHelper.NAME, SamplesHelper._ID }, SamplesHelper.MD5 + " = ?",
                            new String[] { cSampleHash }, null);
                    if (cursor.moveToFirst()) {
                        // sample is already in database
                        IDS.add(cursor.getInt(cursor.getColumnIndex(SamplesHelper._ID)));
                        cursor.close();
                    } else {
                        cursor.close();
                        String sampleName = sample.getName();

                        Log.v("jajaja", String.format("Sample %s, path %s, is not in db", sampleName,
                                sample.getAbsolutePath()));

                        ContentValues values = new ContentValues();
                        values.put(SamplesHelper.MD5, cSampleHash);
                        values.put(SamplesHelper.NAME, sampleName.substring(0, sampleName.lastIndexOf(".")));
                        values.put(SamplesHelper.PATH, sample.getAbsolutePath());
                        values.put(SamplesHelper.SIZE, sample.length());
                        values.put(SamplesHelper.RATE, 0);
                        values.put(SamplesHelper.PLAYED, 0);
                        values.put(SamplesHelper.FORMAT, sampleName.substring(sampleName.lastIndexOf(".") + 1));

                        Uri inserted = contentResolver.insert(EffectsSamplerContentProvider.SAMPLES_URI, values);

                        String id = inserted.toString();

                        Log.v("jajaja", String.format("Inserted new File %s, id is %s", inserted.toString(), id));

                        IDS.add(Integer.parseInt(id));
                    }
                }

                Cursor allDBFiles = contentResolver.query(EffectsSamplerContentProvider.SAMPLES_URI, null, null, null,
                        null);

                while (allDBFiles.moveToNext()) {

                    int id = allDBFiles.getInt(allDBFiles.getColumnIndex(SamplesHelper._ID));
                    if (IDS.contains(id)) {
                        // do nothing
                    } else {
                        int rowDeleted = contentResolver.delete(EffectsSamplerContentProvider.SAMPLES_URI,
                                SamplesHelper._ID + " = ?", new String[] { "" + id });
                        Log.v("jajaja", "deleted " + rowDeleted);
                    }
                }
                allDBFiles.close();

        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(getActivity(), EffectsSamplerContentProvider.SAMPLES_URI, null, null, null, null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.changeCursor(null);
    }
}
