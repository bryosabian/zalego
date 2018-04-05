package com.sabiantools.controls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Dannick on 6/21/2016.
 */
public class SabianCondensedEditText extends EditText {

    private Context _context;

    private Typeface typeface;

    private String _condensed="RobotoCondensed-Regular.ttf";

    public SabianCondensedEditText(Context context)
    {
        super(context);

        _context=context;

        init_elements();
    }
    public SabianCondensedEditText(Context context,AttributeSet set)
    {
        super(context,set);

        _context=context;

        init_elements();
    }
    public SabianCondensedEditText(Context context,AttributeSet set,int defStyle)
    {
        super(context,set,defStyle);

        _context=context;

        init_elements();
    }
    private void init_elements()
    {
        typeface=Typeface.createFromAsset(_context.getAssets(),"fonts/"+_condensed);

        this.setTypeface(typeface);
    }
    public void setCondensed(String condensed)
    {
        this._condensed=condensed;

        this.setTypeface(Typeface.createFromAsset(_context.getAssets(),"fonsts/"+condensed));
    }
}
