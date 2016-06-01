package com.vuric.nativemusicsampler.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vuric.nativemusicsampler.BusStation;
import com.vuric.nativemusicsampler.R;
import com.vuric.nativemusicsampler.database.helpers.SamplesHelper;
import com.vuric.nativemusicsampler.events.SampleLoadedEvt;
import com.vuric.nativemusicsampler.models.SampleModel;
import com.vuric.nativemusicsampler.nativeaudio.NativeWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefano on 4/25/2016.
 */
public class SamplesCursorAdapter extends CursorAdapter {

    private List<SampleModel> samples;
    private int _currentSelectedSlotID;

    public SamplesCursorAdapter(Context context, Cursor c, int currentSelectedSlotID) {
        super(context, c, 0);
        samples = new ArrayList<SampleModel>();
        _currentSelectedSlotID = currentSelectedSlotID;
        BusStation.getBus().register(this);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        BusStation.getBus().unregister(this);
    }

    static class ViewHolder {
        public LinearLayout container;
        public TextView sampleName, samplePath;
        public ImageView selectedItem;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater vInflater = LayoutInflater.from(context);
        View vView = vInflater.inflate(R.layout.sample_item_layout, null);

        ViewHolder holder = new ViewHolder();
        holder.container = (LinearLayout) vView.findViewById(R.id.sampleItemContainer);
        holder.sampleName = (TextView) vView.findViewById(R.id.sampleName);
        holder.samplePath = (TextView) vView.findViewById(R.id.samplePath);
        holder.selectedItem = (ImageView) vView.findViewById(R.id.selectedItem);

        vView.setTag(holder);

        return vView;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {

        int idColumnIndex = cursor.getColumnIndex(SamplesHelper._ID);
        int nameColumIndex = cursor.getColumnIndex(SamplesHelper.NAME);
        int pathColumIndex = cursor.getColumnIndex(SamplesHelper.PATH);
        int sizeColumIndex = cursor.getColumnIndex(SamplesHelper.SIZE);

        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.container.setId(/*cursor.getInt(idColumnIndex)*/cursor.getPosition());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String path = holder.samplePath.getText().toString();

                if(NativeWrapper.loadSample(_currentSelectedSlotID, path)) {
                    BusStation.getBus().post(new SampleLoadedEvt(_currentSelectedSlotID, getItem(holder.container.getId())));
                }
            }
        });
        holder.sampleName.setText(cursor.getString(nameColumIndex));
        holder.samplePath.setText(cursor.getString(pathColumIndex));
    }

    @Override
    public SampleModel getItem(int position) {
        Cursor cursor = getCursor();
        SampleModel tmp = null;
        if (cursor.moveToPosition(position)) {
            tmp = new SampleModel();
            tmp.setID(cursor.getInt(cursor.getColumnIndex(SamplesHelper._ID)));
            tmp.setName(cursor.getString(cursor.getColumnIndex(SamplesHelper.NAME)));
            tmp.setArtist(cursor.getString(cursor.getColumnIndex(SamplesHelper.ARTIST)));
            tmp.setTitle(cursor.getString(cursor.getColumnIndex(SamplesHelper.TITLE)));
            tmp.setPath(cursor.getString(cursor.getColumnIndex(SamplesHelper.PATH)));
            tmp.setHours(cursor.getInt(cursor.getColumnIndex(SamplesHelper.HOURS)));
            tmp.setMinutes(cursor.getInt(cursor.getColumnIndex(SamplesHelper.MINUTES)));
            tmp.setSeconds(cursor.getInt(cursor.getColumnIndex(SamplesHelper.SECONDS)));
            tmp.setBitrate(cursor.getInt(cursor.getColumnIndex(SamplesHelper.BITRATE)));
            tmp.setSize(cursor.getLong(cursor.getColumnIndex(SamplesHelper.SIZE)));
            tmp.setRate(cursor.getFloat(cursor.getColumnIndex(SamplesHelper.RATE)));
            tmp.setPlayed(cursor.getInt(cursor.getColumnIndex(SamplesHelper.PLAYED)));
            tmp.setFormat(cursor.getString(cursor.getColumnIndex(SamplesHelper.FORMAT)));
        }
        //cursor.close();
        return tmp;
    }

    @Override
    public int getCount() {
        Cursor cursor = getCursor();
        if(cursor != null) {
            int count =  cursor.getCount();
            //cursor.close();
            return count;
        }
        return 0;
    }
}
