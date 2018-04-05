package com.sabiantools.modals;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.sabiantools.utilities.SabianUtilities;

/**
 * Created by Dannick on 8/18/2016.
 */
public class SabianViewModal extends DialogFragment {

    public View view;

    public Dialog onCreateDialog(Bundle instance){

        AlertDialog.Builder modal=new AlertDialog.Builder(getActivity());

        modal.setView(view);

        return modal.create();
    }

    public SabianViewModal setView(View view) {
        this.view = view;
        return this;
    }
    public void show(FragmentManager manager){

        String tag="SVM_"+SabianUtilities.GetCurrentTimestamp();

        show(manager,tag);
    }

    /**
     * Allows the reuse of dialogs without exceptions
     * @param manager
     * @param tag
     */
    @Override
    public void show(FragmentManager manager,String tag){
        try{

            FragmentTransaction ft=manager.beginTransaction();

            ft.add(this,tag);

            ft.commit();

        }catch (IllegalStateException ex){
            //Ignore.Its a bug
            Log.e("SAB_DIALOG" + SabianUtilities.GetCurrentTimestamp(), ex.getMessage());
        }
    }
}
