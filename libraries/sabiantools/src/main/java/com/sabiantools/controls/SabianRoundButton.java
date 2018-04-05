package com.sabiantools.controls;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.sabiantools.R;

/**
 * Created by edith on 14/03/2018.
 */
public class SabianRoundButton extends FrameLayout {

    private ViewGroup ll_Container;

    private TextView txt_Text;

    private FontAwesomeText fat_Icon;

    private Context context;

    private View root;

    private static final int NO_COLOR=-1;

    private static final int NO_DRAWABLE=-1;

    public SabianRoundButton(Context context) {
        super(context);
        init(context);
    }

    public SabianRoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        init_attrs(attrs);
    }

    public SabianRoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        init_attrs(attrs);
    }
    private void init(Context context){
        this.context=context;
        root= LayoutInflater.from(context).inflate(R.layout.control_sabian_round_button,this,true);
        ll_Container=(ViewGroup)root.findViewById(R.id.ll_ControlSabianRoundButtonContainer);
        txt_Text=(TextView)root.findViewById(R.id.txt_ControlSabianRoundButtonText);
        fat_Icon=(FontAwesomeText)root.findViewById(R.id.fat_ControlSabianRoundButtonIcon);
    }

    private void init_attrs(AttributeSet attrs){

        TypedArray theAttrs=context.obtainStyledAttributes(attrs, R.styleable.SabianRoundButton);

        int count=theAttrs.getIndexCount();

        for(int i=0; i<count;i++){

            int index=theAttrs.getIndex(i);

            if(index==R.styleable.SabianRoundButton_srb_Text){
                setText(theAttrs.getString(index));
            }else if(index==R.styleable.SabianRoundButton_srb_TextColor){
                setTextColor(theAttrs.getColor(index, NO_COLOR));
            } else if(index==R.styleable.SabianRoundButton_srb_Icon){
                setIcon(theAttrs.getString(index));
            } else if(index==R.styleable.SabianRoundButton_srb_TypeDrawable){
                setTypeDrawable(theAttrs.getResourceId(index,NO_DRAWABLE));
            }

        }

    }

    public void setText(String text){
        txt_Text.setText(text);
    }

    public void setTextColor(int color){

        if(color==NO_COLOR)
            return;

        txt_Text.setTextColor(color);
        fat_Icon.setTextColor(color);
    }
    public void setIcon(String faIcon) {
        fat_Icon.setVisibility(VISIBLE);
        fat_Icon.setIcon(faIcon);
    }
    public void setTypeDrawable(int resourceID){

        if(resourceID==NO_DRAWABLE)
            return;

        ll_Container.setBackgroundResource(resourceID);
    }
    public void setOnClickListener(OnClickListener onClickListener){
        ll_Container.setOnClickListener(onClickListener);
    }

}
