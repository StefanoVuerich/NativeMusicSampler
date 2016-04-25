package com.vuric.nativemusicsampler.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.vuric.nativemusicsampler.R;
import com.vuric.nativemusicsampler.fragments.ConsoleFragment;
import com.vuric.nativemusicsampler.interfaces.IPlayer;
import com.vuric.nativemusicsampler.layouts.PlayerView;
import com.vuric.nativemusicsampler.utils.Constants;

public class SanplerSlotsAdapter extends BaseAdapter {

    private Context _context;
    private IPlayer[] _players;
    private ConsoleFragment _consoleFragment;

    public SanplerSlotsAdapter(Context context) {

        _context = context;
        _consoleFragment = (ConsoleFragment) ((Activity)context).getFragmentManager().findFragmentByTag(Constants.CONSOLE_FRAGMENT);
        if(_consoleFragment != null) {
            _players = _consoleFragment.getPlayers();
        }
    }

    public int getCount() {
        return _players.length;
    }

    public IPlayer getItem(int position) {
        return _players[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        PlayerView view;
        if (convertView == null) {
            view = (PlayerView) LayoutInflater.from(_context).inflate(R.layout.slot_view, parent, false);
            //view.setPlayer(_players[position]);
        } else {
            view = (PlayerView) convertView;
        }
        return view;
    }
}