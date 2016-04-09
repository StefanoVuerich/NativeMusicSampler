package com.vuric.nativemusicsampler.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.vuric.nativemusicsampler.R;
import com.vuric.nativemusicsampler.interfaces.IPlayer;
import com.vuric.nativemusicsampler.controllers.Player;
import com.vuric.nativemusicsampler.layouts.PlayerView;

public class SanplerSlotsAdapter extends BaseAdapter {

    private Context mContext;
    private IPlayer[] players;

    public SanplerSlotsAdapter(Context c, int slots) {

        mContext = c;
        players = new IPlayer[slots];
        init(slots);
    }

    private void init(int slots) {

        for(int i = 0; i < slots; ++i) {
            players[i] = new Player(mContext);
        }
    }

    public int getCount() {
        return players.length;
    }

    public IPlayer getItem(int position) {
        return players[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        PlayerView view;
        if (convertView == null) {
            view = (PlayerView) LayoutInflater.from(mContext).inflate(R.layout.slot_view, parent, false);
            view.setPlayer(players[position]);
        } else {
            view = (PlayerView) convertView;
        }
        return view;
    }
}