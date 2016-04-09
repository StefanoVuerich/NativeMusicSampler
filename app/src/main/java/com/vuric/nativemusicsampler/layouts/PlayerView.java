package com.vuric.nativemusicsampler.layouts;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.vuric.nativemusicsampler.R;
import com.vuric.nativemusicsampler.interfaces.IPlayer;

/**
 * Created by stefano on 4/5/2016.
 */
public class PlayerView extends RelativeLayout {

    private int _index;
    private IPlayer _player;
    private PlayButton _playButton;
    private View playButton;
    private View activeButton;

    public PlayerView(Context context) {
        super(context);
        init();
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(Color.parseColor("#0000FF"));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();

        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                _player.play();
            }
        });
        playButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                _player.load("");
                return true;
            }
        });

        activeButton = findViewById(R.id.selectButton);
        activeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                _player.loop();
            }
        });
    }

    public void setPlayer(IPlayer player) {
        _player = player;
    }
}
