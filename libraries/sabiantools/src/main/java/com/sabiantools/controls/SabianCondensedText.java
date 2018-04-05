package com.sabiantools.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.sabiantools.R;

/**
 * Created by Dannick on 6/21/2016.
 */
public class SabianCondensedText extends TextView {

    private Typeface typeface;

    private Context _context;

    private String _condensed="RobotoCondensed-Regular.ttf";

    private String _roboto="";

    public SabianCondensedText(Context context)
    {
        super(context);

        _context=context;

        init_elements();
    }
    public SabianCondensedText(Context context,AttributeSet set)
    {
        super(context,set);

        _context=context;

        init_elements();

        init_attributes(set);
    }
    public SabianCondensedText(Context context,AttributeSet set,int defStyle)
    {
        super(context,set,defStyle);

        _context=context;

        init_elements();

        init_attributes(set);
    }
    private void init_elements()
    {
        typeface=Typeface.createFromAsset(_context.getAssets(),"fonts/"+this._condensed);

        this.setTypeface(typeface);
    }
    public void setCondensed(String condensed)
    {
        this._condensed=condensed;

        this.setTypeface(Typeface.createFromAsset(_context.getAssets(),"fonts/RobotoCondensed-"+condensed+".ttf"));
    }
    public void setRoboto(String roboto)
    {
        this._roboto=roboto;

        this.setTypeface(Typeface.createFromAsset(_context.getAssets(),"fonts/Roboto-"+roboto+".ttf"));
    }
    private void init_attributes(AttributeSet attrs) {

        TypedArray a = _context.obtainStyledAttributes(attrs, R.styleable.SabianCondensedText);

        int aCount = a.getIndexCount();

        for (int i = 0; i < aCount; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SabianCondensedText_SabianCondensedType) {
                this.setCondensed(a.getString(attr));

            } else if (attr == R.styleable.SabianCondensedText_SabianRobotoType) {
                this.setRoboto(a.getString(attr));

            }
        }
    }
}
