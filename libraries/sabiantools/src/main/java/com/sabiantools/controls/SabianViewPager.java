package com.sabiantools.controls;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Brian Sabana on 06/02/2017.
 */
public class SabianViewPager extends ViewPager {

    private boolean allowSwipe =true;

    private boolean allowInterceptTouch=allowSwipe;

    public SabianViewPager(Context context)
    {
        super(context);
    }

    public SabianViewPager(Context context, AttributeSet attrs)
    {
        super(context,attrs);
    }

    public void setAllowSwipe(boolean activateSwipe)
    {
        this.allowSwipe=activateSwipe;
    }

    public void setAllowInterceptTouch(boolean allowInterceptTouch) {
        this.allowInterceptTouch = allowInterceptTouch;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        if(!allowSwipe)
        {
            return false;
        }
        else {
            return super.onTouchEvent(event);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(!allowSwipe)
        {
            return false;
        }
        else {
            return super.onTouchEvent(event);
        }

    }
}
