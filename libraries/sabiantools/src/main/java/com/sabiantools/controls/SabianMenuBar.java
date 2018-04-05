package com.sabiantools.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.sabiantools.R;

/**
 * Created by Dannick on 3/25/2016.
 */
public class SabianMenuBar extends FrameLayout {

    private Context mContext;
    private RelativeLayout mainContainer;
    private TextView title;
    private FontAwesomeText cart,wishlist;
    private int cartItems,wishlistItems;
    private boolean displayWishlist=false;
    private RelativeLayout wishlistContainer,cartContainer;


    public SabianMenuBar(Context context)
    {
        super(context);
        this.mContext=context;
        init_children();
    }
    public SabianMenuBar(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
        this.mContext=context;
        init_children();
    }
    public SabianMenuBar(Context context, AttributeSet attributeSet, int defStyle)
    {
        super(context, attributeSet, defStyle);
        this.mContext=context;
        init_children();
    }
    private void init_children()
    {
        LayoutInflater inflater=LayoutInflater.from(this.mContext);
        View root=inflater.inflate(R.layout.sabian_actionbar,this,true);
        this.mainContainer=(RelativeLayout)root.findViewById(R.id.rll_SabianActionBarContainer);
        this.title=(TextView)root.findViewById(R.id.txt_SabianActionBarTitle);
    }
    public TextView getTitle()
    {
        return this.title;
    }

    public void setTitle(String text)
    {
        this.title.setText(text);
    }
}
