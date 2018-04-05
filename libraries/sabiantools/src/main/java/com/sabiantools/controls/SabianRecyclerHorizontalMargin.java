package com.sabiantools.controls;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Dannick on 9/26/2016.
 */
public class SabianRecyclerHorizontalMargin extends RecyclerView.ItemDecoration {

    private int marginRightSize;

    public SabianRecyclerHorizontalMargin(int marginRightSize) {
        this.marginRightSize = marginRightSize;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // Add top margin only for all items (Except the last)
        if (parent.getChildLayoutPosition(view)==parent.getAdapter().getItemCount()-1) {
            super.getItemOffsets(outRect,view,parent,state);
            return;
        }
        outRect.right= marginRightSize;
    }
}
