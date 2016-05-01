package com.vuric.nativemusicsampler.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;
import com.vuric.nativemusicsampler.BusStation;
import com.vuric.nativemusicsampler.controllers.Console;
import com.vuric.nativemusicsampler.controllers.Mixer;
import com.vuric.nativemusicsampler.events.SampleSelectedEvt;
import com.vuric.nativemusicsampler.interfaces.IConsole;
import com.vuric.nativemusicsampler.interfaces.IMixer;
import com.vuric.nativemusicsampler.interfaces.IPlayer;

/**
 * Created by stefano on 4/9/2016.
 */
public class ConsoleFragment extends Fragment {

    private IConsole _console;
    private IMixer _mixer;
    private IPlayer[] _players;
    public static final String _TAG = ConsoleFragment.class.getSimpleName();

    public static ConsoleFragment get() {
        return new ConsoleFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        BusStation.getBus().register(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        _console = new Console();
        _mixer = new Mixer();
        //_players = generatePlayers();
        _console.init();
    }

    @Override
    public void onPause() {
        super.onPause();
        BusStation.getBus().unregister(this);
    }

    public void setPlayers(IPlayer[] players) {
        _players = players;
    }

    /*private IPlayer[] generatePlayers() {
        PlayerController[] players = new PlayerController[Constants.SLOTS];
        for(int i = 0; i < players.length; ++i) {
            players[i] = new PlayerController(_model);
        }
        return players;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onDestroy() {
        _console.shutdown();
    }

    public IConsole getConsole() {
        return _console;
    }

    public IMixer getMixer() {
        return _mixer;
    }

    public IPlayer getPlayer(int index) {
        return _players[index];
    }

    public IPlayer[] getPlayers() {
        return _players;
    }

    @Subscribe
    public void receiveMessage(SampleSelectedEvt evt) {

        _players[evt.get_slotID()].load(evt.get_path());
    }
}
