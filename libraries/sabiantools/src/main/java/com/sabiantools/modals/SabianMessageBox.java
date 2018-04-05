package com.sabiantools.modals;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.sabiantools.utilities.SabianUtilities;

/**
 * Created by Brian Sabana on 21/12/2015.
 */
public class SabianMessageBox extends DialogFragment {
    public String title ="Message";
    public String body="Message Content";
    public OnChoiceMadeListener choiceMadeListener=null;
    private boolean allowCancel=true;
    private View _view=null;
    public String posButton="Ok";
    public String nButton="Cancel";


    @Override
    public Dialog onCreateDialog(Bundle instance)
    {
        AlertDialog.Builder modal=new AlertDialog.Builder(getActivity());


        if(_view!=null){
            modal.setView(_view);
        }
        else {
            modal.setTitle(title);
            modal.setMessage(body);
        }


        modal.setPositiveButton(posButton,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(choiceMadeListener!=null)
                    choiceMadeListener.onOkayClicked(_view,getThis());
                //dismiss();
            }
        });
        if(allowCancel) {
            modal.setNegativeButton(nButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (choiceMadeListener != null)
                        choiceMadeListener.onCancelClicked(_view,getThis());
                   // dismiss();
                }
            });
        }
        return modal.create();
    }
    public SabianMessageBox getThis()
    {
        return this;
    }
    public void setTitle(String inputMessage)
    {
        this.title=inputMessage;
    }
    public void setMessage(String inputBody)
    {
        this.body=inputBody;
    }
    public void setAllowCancel(boolean allow)
    {
        this.allowCancel=allow;
    }

    public void addView(View view)
    {
        this._view=view;
    }

    public interface OnChoiceMadeListener
    {
        public void onOkayClicked(View view, SabianMessageBox messageBox);

        public void onCancelClicked(View view, SabianMessageBox messageBox);
    }

    public void setOnChoiceMadeListener(OnChoiceMadeListener listener)
    {
        choiceMadeListener=listener;
    }
    public void setPositiveButtonText(String text){
        this.posButton=text;
    }
    public void setNegativeButtonText(String text){
        this.nButton=text;
    }
    @Override
    public void show(FragmentManager manager,String tag){
        try{

            FragmentTransaction ft=manager.beginTransaction();

            ft.add(this,tag);

            ft.commit();

        }catch (IllegalStateException ex){
            //Ignore.Its a bug
            Log.e("SAB_DIALOG"+ SabianUtilities.GetCurrentTimestamp(),ex.getMessage());
        }
    }
    public void show(FragmentManager manager){
        this.show(manager,"SSHPR_MSG_" + SabianUtilities.GetCurrentTimestamp());
    }

}


