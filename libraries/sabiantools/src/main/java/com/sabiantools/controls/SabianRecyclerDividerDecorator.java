package com.sabiantools.controls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Dannick on 9/20/2016.
 */
public class SabianRecyclerDividerDecorator extends RecyclerView.ItemDecoration {

    private Drawable drawable;

    private Context context;

    public SabianRecyclerDividerDecorator(Context context, int resID) {
        this.context = context;
        this.drawable = ContextCompat.getDrawable(context, resID);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + drawable.getIntrinsicHeight();

            drawable.setBounds(left, top, right, bottom);

            drawable.draw(c);
        }
    }
}
