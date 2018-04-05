package com.sabiantools.controls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.iangclifton.android.floatlabel.FloatLabel;

/**
 * Created by Dannick on 7/6/2016.
 */
public class SabianFloatLabel extends FloatLabel {

    private Context _context;

    private Typeface typeface;

    public SabianFloatLabel(Context context)
    {
        super(context);

        this._context=context;

        this.init_type_face();
    }
    public SabianFloatLabel(Context context, AttributeSet set)
    {
        super(context,set);

        this._context=context;

        this.init_type_face();
    }
    public SabianFloatLabel(Context context, AttributeSet set, int defStyle)
    {
        super(context,set,defStyle);

        this._context=context;

        this.init_type_face();
    }
    private void init_type_face()
    {
        typeface=Typeface.createFromAsset(_context.getAssets(),"fonts/RobotoCondensed-Regular.ttf");

        this.getEditText().setTypeface(typeface);

    }
    public String getText(){
        return this.getEditText().getText().toString();
    }
    public void setText(String text){
        this.getEditText().setText(text);
    }
}
