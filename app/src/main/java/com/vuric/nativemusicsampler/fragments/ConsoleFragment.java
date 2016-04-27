package com.vuric.nativemusicsampler.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vuric.nativemusicsampler.controllers.Console;
import com.vuric.nativemusicsampler.controllers.Mixer;
import com.vuric.nativemusicsampler.controllers.Player;
import com.vuric.nativemusicsampler.interfaces.IConsole;
import com.vuric.nativemusicsampler.interfaces.IMixer;
import com.vuric.nativemusicsampler.interfaces.IPlayer;
import com.vuric.nativemusicsampler.utils.Constants;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        _console = new Console();
        _mixer = new Mixer();
        _players = generatePlayers();
        _console.init();
    }

    private IPlayer[] generatePlayers() {
        Player[] players = new Player[Constants.SLOTS];
        for(int i = 0; i < players.length; ++i) {
            players[i] = new Player();
        }
        return players;
    }

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
}
