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

import com.vuric.nativemusicsampler.R;
import com.vuric.nativemusicsampler.database.helpers.SamplesHelper;
import com.vuric.nativemusicsampler.models.SampleObj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefano on 4/25/2016.
 */
public class SamplesCursorAdapter extends CursorAdapter {

    private List<SampleObj> samples;

    public SamplesCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
        samples = new ArrayList<SampleObj>();
    }

    static class ViewHolder {
        public LinearLayout container;
        public TextView sampleName;
        public ImageView selectedItem;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater vInflater = LayoutInflater.from(context);
        View vView = vInflater.inflate(R.layout.sample_item_layout, null);

        ViewHolder holder = new ViewHolder();
        holder.container = (LinearLayout) vView.findViewById(R.id.sampleItemContainer);
        holder.sampleName = (TextView) vView.findViewById(R.id.sampleName);
        holder.selectedItem = (ImageView) vView.findViewById(R.id.selectedItem);

        vView.setTag(holder);

        return vView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        int idColumnIndex = cursor.getColumnIndex(SamplesHelper._ID);
        int nameColumIndex = cursor.getColumnIndex(SamplesHelper.NAME);
        int sizeColumIndex = cursor.getColumnIndex(SamplesHelper.SIZE);

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.container.setId(/*cursor.getInt(idColumnIndex)*/cursor.getPosition());
        holder.sampleName.setText(cursor.getString(nameColumIndex));
    }

    @Override
    public SampleObj getItem(int position) {
        Cursor cursor = getCursor();
        SampleObj tmp = null;
        if (cursor.moveToPosition(position)) {
            tmp = new SampleObj();
            tmp.setID(cursor.getInt(cursor.getColumnIndex(SamplesHelper._ID)));
            tmp.setName(cursor.getString(cursor.getColumnIndex(SamplesHelper.NAME)));
            tmp.setPath(cursor.getString(cursor.getColumnIndex(SamplesHelper.PATH)));
            tmp.setSize(cursor.getLong(cursor.getColumnIndex(SamplesHelper.SIZE)));
            tmp.setRate(cursor.getFloat(cursor.getColumnIndex(SamplesHelper.RATE)));
            tmp.setPlayed(cursor.getInt(cursor.getColumnIndex(SamplesHelper.PLAYED)));
            tmp.setFormat(cursor.getString(cursor.getColumnIndex(SamplesHelper.FORMAT)));
        }
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
