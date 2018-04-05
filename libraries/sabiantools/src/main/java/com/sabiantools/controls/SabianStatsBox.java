package com.sabiantools.controls;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;


import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.gc.materialdesign.views.Card;
import com.sabiantools.R;

/**
 * Created by Dannick on 7/22/2016.
 */
public class SabianStatsBox extends FrameLayout{

    private Card cd_stat;

    private SabianCondensedText txt_stat;

    private FontAwesomeText ft_stat;

    private Context _context;

    public SabianStatsBox(Context context)
    {
        super(context);

        init_elements(context);
    }
    public SabianStatsBox(Context context, AttributeSet set)
    {
        super(context,set);

        init_elements(context);

        init_attributes(set);
    }
    public SabianStatsBox(Context context, AttributeSet set, int defStyle)
    {
        super(context,set,defStyle);

        init_elements(context);

        init_attributes(set);
    }
    private void init_elements(Context context)
    {
        _context=context;

        LayoutInflater inflater=LayoutInflater.from(context);

        View view=inflater.inflate(R.layout.control_sabian_stat_box,this,true);

        cd_stat=(Card)view.findViewById(R.id.cdv_SabianStatsContainer);

        txt_stat=(SabianCondensedText)view.findViewById(R.id.txt_SabianStatsText);

        ft_stat=(FontAwesomeText)view.findViewById(R.id.ft_SabianStatsIcon);
    }
    public void setStatColor(int color)
    {
        this.cd_stat.setBackgroundColor(color);

        //this.cd_stat.setBackgroundResource(color);
    }
    public void setStatText(String text)
    {
        this.txt_stat.setText(text);
    }
    public void setStatIcon(String icon)
    {
        this.ft_stat.setIcon(icon);
    }
    public SabianCondensedText getTextView()
    {
        return txt_stat;
    }
    private void init_attributes(AttributeSet set)
    {
        TypedArray a=_context.obtainStyledAttributes(set,R.styleable.SabianStatsBox);

        int aCount=a.getIndexCount();

        for(int i=0;i<aCount;++i)
        {
            int attr=a.getIndex(i);

            if (attr == R.styleable.SabianStatsBox_sabianStatColor) {
                Resources res = _context.getResources();

                int def = R.color.sabian_notification_inverse;

                this.setStatColor(def);


            } else if (attr == R.styleable.SabianStatsBox_sabianStatIcon) {
                this.setStatIcon(a.getString(attr));


            } else if (attr == R.styleable.SabianStatsBox_sabianStatText) {
                this.setStatText(a.getString(attr));

            }
        }
    }

}
