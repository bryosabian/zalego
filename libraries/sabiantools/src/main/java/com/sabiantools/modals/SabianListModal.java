package com.sabiantools.modals;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import com.sabiantools.utilities.SabianUtilities;

import java.util.ArrayList;

/**
 * Created by Brian Sabana on 29/01/2017.
 */
public class SabianListModal extends DialogFragment {

    protected String title = "Message";

    public OnActionSelectedListener onActionSelectedListener = null;

    protected ArrayList<String> choices;

    protected boolean allowCancel=true;


    @Override
    public Dialog onCreateDialog(Bundle instance) {

        AlertDialog.Builder modal = new AlertDialog.Builder(getActivity());

        modal.setTitle(title);

        if(choices==null)
            return modal.create();

        String[] choiceArr=choices.toArray(new String[ choices.size() ]);

        modal.setTitle(title);

        modal.setItems(choiceArr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (onActionSelectedListener != null)
                    onActionSelectedListener.onSelected(i);
                dismiss();


            }
        });

        if(allowCancel) {
            modal.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dismiss();
                }
            });
        }

        return modal.create();
    }

    public boolean isAllowCancel() {
        return allowCancel;
    }

    public void setAllowCancel(boolean allowCancel) {
        this.allowCancel = allowCancel;
    }

    public SabianListModal setTitle(String inputMessage) {
        this.title = inputMessage;
        return this;
    }

    public void setOnActionSelectedListener(OnActionSelectedListener onActionSelectedListener) {
        this.onActionSelectedListener = onActionSelectedListener;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public SabianListModal setChoices(ArrayList<String> choices) {
        this.choices = choices;
        return this;
    }
    public void show(FragmentManager manager){

        String tag="SLM_"+ SabianUtilities.GetCurrentTimestamp();

        this.show(manager,tag);
    }
    public interface OnActionSelectedListener {
        void onSelected(int i);
    }

}
