package com.sabiantools.modals;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.widgets.Dialog;
import com.sabiantools.R;

/**
 * Created by Brian Sabana on 23/02/2017.
 */
public class SabianDialog extends Dialog {

    private View rootView;

    public SabianDialog(Context context, String title, String message) {

        super(context, title, message);

        rootView= LayoutInflater.from(context).inflate(R.layout.sabian_dialog,null);

        init_elements();
    }
    private void init_elements(){
        view = (RelativeLayout)findViewById(R.id.contentDialog);
        backView = (RelativeLayout)findViewById(R.id.dialog_rootView);
        backView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getX() < view.getLeft()
                        || event.getX() >view.getRight()
                        || event.getY() > view.getBottom()
                        || event.getY() < view.getTop()) {
                    dismiss();
                }
                return false;
            }
        });

        this.titleTextView = (TextView) findViewById(R.id.title);
        setTitle(title);

        this.messageTextView = (TextView) findViewById(R.id.message);
        setMessage(message);

        this.buttonAccept = (ButtonFlat) findViewById(R.id.button_accept);
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(onAcceptButtonClickListener != null)
                    onAcceptButtonClickListener.onClick(v);
            }
        });

        if(buttonCancelText != null){
            this.buttonCancel = (ButtonFlat) findViewById(R.id.button_cancel);
            this.buttonCancel.setVisibility(View.VISIBLE);
            this.buttonCancel.setText(buttonCancelText);
            buttonCancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dismiss();
                    if(onCancelButtonClickListener != null)
                        onCancelButtonClickListener.onClick(v);
                }
            });
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(rootView);


    }
    public SabianDialog setButtonAcceptColor(int colorRes){

        buttonAccept.setBackgroundColor(colorRes);

        return this;
    }
    public SabianDialog setButtonCancelColor(int colorRes){

        if(buttonCancel!=null)
            buttonCancel.setBackgroundColor(colorRes);

        return this;
    }
    public SabianDialog setButtonAcceptText(String text){

        buttonAccept.setText(text);

        return this;
    }
    public SabianDialog setButtonCancelText(String text){

        buttonCancelText=text;

        buttonCancel.setText(text);

        return this;
    }

    @Override
    public View findViewById(int id) {
        return rootView.findViewById(id);
    }
}
