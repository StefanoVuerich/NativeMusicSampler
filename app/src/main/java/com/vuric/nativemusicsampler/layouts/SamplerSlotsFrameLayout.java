package com.vuric.nativemusicsampler.layouts;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;


/**
 * Created by stefano on 4/5/2016.
 */
public class SamplerSlotsFrameLayout extends FrameLayout {

    private int childDimension;

    public SamplerSlotsFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        setBackgroundColor(Color.parseColor("#000000"));
        //setBackground(ContextCompat.getDrawable(context, R.drawable.slots_background_colors));
        setId(View.generateViewId());
        //setPadding(20, 20, 20, 20);

        /*for (int i = 0; i < Constants.SLOTS; ++i) {

            SlotView slotView = new SlotView(context, i);
            slotView.setId(i);
            addView(slotView);
        }*/
    }
}