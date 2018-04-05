package com.sabiantools.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.sabiantools.R;
import com.sabiantools.utilities.SabianUtilities;

/**
 * Created by Brian Sabana on 17/01/2017.
 */
public class SabianNumberSelector extends FrameLayout {

    private Button btn_Add,btn_Minus;

    private EditText txt_Number;

    private LayoutInflater inflater;

    private Context _context;

    private int value=0;

    private int maxValue=Integer.MAX_VALUE;

    private int minValue=Integer.MIN_VALUE;

    private OnValueChangedListener onValueChangedListener=null;

    public SabianNumberSelector(Context context) {
        super(context);
        this._context=context;
        init_elements();
    }

    public SabianNumberSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        this._context=context;
        init_elements();
        init_attributes(attrs);
    }

    public SabianNumberSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this._context=context;
        init_elements();
        init_attributes(attrs);
    }

    private void init_elements()
    {
        this.inflater=LayoutInflater.from(_context);

        View view=inflater.inflate(R.layout.sabian_number_selector,this,true);

        btn_Add=(Button)view.findViewById(R.id.btn_SabianNumberSelectorPlus);

        btn_Minus=(Button)view.findViewById(R.id.btn_SabianNumberSelectorMinus);

        txt_Number=(EditText)findViewById(R.id.txt_SabianNumberSelectorText);

        txt_Number.setTypeface(Typeface.createFromAsset(_context.getAssets(), "fonts/RobotoCondensed-Regular.ttf"));

        btn_Add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addValue();
            }
        });
        btn_Minus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                subtractValue();
            }
        });

        txt_Number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                if (s.length() <= 0)
                    return;

                int newValue;

                int oldValue = getValue();

                String text = s.toString();

                if(text.isEmpty())
                    return;

                newValue = Integer.parseInt(text);

                if(newValue<minValue)
                    return;

                if(newValue>maxValue)
                    return;

                value=newValue;

                SabianUtilities.WriteLog("Text changed bro " + getValue());

                if(onValueChangedListener==null)
                return;

                //Handled by onTextChangedEvent
                onValueChangedListener.ValueChanged(oldValue, value);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public int getValue() {

        value=Integer.parseInt(txt_Number.getText().toString());

        return value;

    }
    public void setValue(int value) {

        this.value = value;

        this.txt_Number.setText(value+"");
    }
    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public OnValueChangedListener getOnValueChangedListener() {
        return onValueChangedListener;
    }

    public void setOnValueChangedListener(OnValueChangedListener onValueChangedListener) {
        this.onValueChangedListener = onValueChangedListener;
    }
    private void changeValue(){

        if(txt_Number.getText().toString().isEmpty())
            return;

        int oldValue=value;

        int value=Integer.parseInt(txt_Number.getText().toString());

        if(value<minValue)
            return;

        if(value>maxValue)
            return;

       this.setValue(value);

        //if(onValueChangedListener==null)
        //return;

        //Handled by onTextChangedEvent
        //onValueChangedListener.ValueChanged(oldValue,value);
    }
    private void changeValue(int old,int newval){

        if(txt_Number.getText().toString().isEmpty())
            return;

        int oldValue=old;

        int value=newval;

        if(value<minValue)
            return;

        if(value>maxValue)
            return;

        this.setValue(value);

        //if(onValueChangedListener==null)
            //return;

        //Handled by onTextChangedEvent
       //onValueChangedListener.ValueChanged(oldValue,value);
    }
    private void addValue(){

        int old=value;

        value+=1;

        changeValue(old,value);
    }
    private void subtractValue(){

        int old=value;

        value-=1;

        changeValue(old,value);
    }
    private void init_attributes(AttributeSet set) {

        TypedArray a = _context.obtainStyledAttributes(set, R.styleable.SabianNumberSelector);

        int aCount = a.getIndexCount();

        for (int i = 0; i < aCount; ++i) {

            int attr = a.getIndex(i);

            if (attr == R.styleable.SabianNumberSelector_sabian_value) {
                setValue(a.getInteger(attr, value));

            } else if (attr == R.styleable.SabianNumberSelector_sabian_max_value) {
                setMaxValue(a.getInteger(attr, maxValue));

            } else if (attr == R.styleable.SabianNumberSelector_sabian_min_value) {
                setMinValue(a.getInteger(attr, minValue));

            }
        }
    }

    public interface OnValueChangedListener{

        void ValueChanged(int oldValue, int newValue);
    }
}
