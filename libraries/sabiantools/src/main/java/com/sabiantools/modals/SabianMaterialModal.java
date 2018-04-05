package com.sabiantools.modals;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sabiantools.R;
import com.gc.materialdesign.views.ButtonFlat;
import com.sabiantools.controls.SabianCondensedText;
import com.sabiantools.controls.SabianFitImage;

/**
 * Created by Brian Sabana on 22/02/2017.
 */
public class SabianMaterialModal extends SabianViewModal {

    private ViewGroup vg_Header,vg_Content;

    private SabianFitImage sfi_HeaderIcon,sfi_HeaderIconClose;

    private ButtonFlat btn_Ok,btn_Cancel,btn_NotSure;

    private SabianCondensedText sct_Title;

    private Context context;

    private LayoutInflater inflater;

    private int headerIcon;

    private String buttonAcceptText,buttonCancelText;

    private int buttonAcceptColor,buttonCancelColor;

    private int headerColor;

    private int headerSize;

    private String title;

    private View content;

    private View.OnClickListener onAcceptButtonClickListener;

    private View.OnClickListener onCancelButtonClickListener;

    private boolean iconVectorDrawable=true;

    private boolean displayCloseIcon=true;

    private int headerTextColor;

    private boolean displayHeaderText=true;

    private boolean displayHeaderIcon=true;

    private Drawable headerIconDrawable;

    private int headerHeight=Integer.MIN_VALUE;

    public SabianMaterialModal(){


    }
    public SabianMaterialModal init(Context context){

        this.context=context;

        inflater=LayoutInflater.from(context);

        init_elements();

        return this;
    }
    private void init_elements(){

        view=inflater.inflate(R.layout.sabian_material_modal_layout, null);

        vg_Header=(ViewGroup)findViewById(R.id.rll_SabianMaterialModalHeader);

        vg_Content=(ViewGroup)findViewById(R.id.ll_SabianMaterialModalContent);

        sfi_HeaderIcon=(SabianFitImage)findViewById(R.id.sfi_SabianMaterialModalIcon);

        sfi_HeaderIconClose=(SabianFitImage)findViewById(R.id.sfi_SabianMaterialModalIconClose);

        sct_Title=(SabianCondensedText)findViewById(R.id.sct_SabianMaterialModalTitle);

        btn_Ok=(ButtonFlat)findViewById(R.id.btn_SabianMaterialModalOk);

        btn_Cancel=(ButtonFlat)findViewById(R.id.btn_SabianMaterialModalCancel);

        btn_NotSure=(ButtonFlat)findViewById(R.id.btn_SabianMaterialModalMiddle);

        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(onAcceptButtonClickListener!=null) {
                    onAcceptButtonClickListener.onClick(view);
                    return;
                }

                dismiss();

            }
        });

        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(onCancelButtonClickListener!=null)
                    onCancelButtonClickListener.onClick(view);

                dismiss();
            }
        });

        sfi_HeaderIconClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public SabianMaterialModal setView(View view) {
        content=view;
        this.vg_Content.addView(view);
        return this;
    }

    public Dialog onCreateDialog(Bundle instance){

        setStyle(STYLE_NO_FRAME,0);

        if(getButtonCancelText()==null)
            btn_Cancel.setVisibility(View.GONE);

        if(!isDisplayCloseIcon())
            sfi_HeaderIconClose.setVisibility(View.GONE);

        if(!displayHeaderText)
            sct_Title.setVisibility(View.GONE);

        if(!displayHeaderIcon)
            sfi_HeaderIcon.setVisibility(View.GONE);

        AlertDialog.Builder modal=new AlertDialog.Builder(getActivity());

        modal.setView(view);

        return modal.create();
    }
    @Nullable
    protected View findViewById(@IdRes int id)
    {
        return view.findViewById(id);
    }


    public int getHeaderIcon() {
        return headerIcon;
    }

    public SabianMaterialModal setHeaderIcon(@DrawableRes int headerIcon) {

        this.headerIcon = headerIcon;

        if(isIconVectorDrawable()){

            VectorDrawableCompat compat= VectorDrawableCompat.create(context.getResources(), headerIcon, null);

            sfi_HeaderIcon.setImageDrawable(compat);

            return this;
        }

        sfi_HeaderIcon.setImageResource(headerIcon);

        return this;
    }

    public String getButtonAcceptText() {
        return buttonAcceptText;
    }

    public SabianMaterialModal setButtonAcceptText(String buttonAcceptText) {
        this.buttonAcceptText = buttonAcceptText;
        this.btn_Ok.setText(buttonAcceptText);
        return this;
    }

    public String getButtonCancelText() {
        return buttonCancelText;
    }

    public SabianMaterialModal setButtonCancelText(String buttonCancelText) {

        this.buttonCancelText = buttonCancelText;

        this.btn_Cancel.setText(buttonCancelText);

        return this;
    }

    public int getButtonAcceptColor() {
        return buttonAcceptColor;
    }

    public SabianMaterialModal setButtonAcceptColor(int buttonAcceptColor) {
        this.buttonAcceptColor = buttonAcceptColor;
        this.btn_Ok.setBackgroundColor(buttonAcceptColor);
        return this;
    }

    public int getButtonCancelColor() {
        return buttonCancelColor;
    }

    public SabianMaterialModal setButtonCancelColor(int buttonCancelColor) {
        this.buttonCancelColor = buttonCancelColor;
        this.btn_Cancel.setBackgroundColor(buttonCancelColor);
        return this;
    }

    public int getHeaderColor() {
        return headerColor;
    }

    public SabianMaterialModal setHeaderColor(int headerColor) {
        this.headerColor = headerColor;
        this.vg_Header.setBackgroundColor(headerColor);
        return this;
    }

    public int getHeaderSize() {
        return headerSize;
    }

    public SabianMaterialModal setHeaderSize(int headerSize) {
        this.headerSize = headerSize;
        this.vg_Header.getLayoutParams().height=headerSize;
        vg_Header.requestLayout();
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SabianMaterialModal setTitle(String title) {
        this.title = title;
        this.sct_Title.setText(title);
        return this;
    }

    public View.OnClickListener getOnAcceptButtonClickListener() {
        return onAcceptButtonClickListener;
    }

    public SabianMaterialModal setOnAcceptButtonClickListener(View.OnClickListener onAcceptButtonClickListener) {
        this.onAcceptButtonClickListener = onAcceptButtonClickListener;
        return this;
    }

    public View.OnClickListener getOnCancelButtonClickListener() {
        return onCancelButtonClickListener;
    }

    public SabianMaterialModal setOnCancelButtonClickListener(View.OnClickListener onCancelButtonClickListener) {
        this.onCancelButtonClickListener = onCancelButtonClickListener;
        return this;
    }

    public boolean isIconVectorDrawable() {
        return iconVectorDrawable;
    }

    public SabianMaterialModal setIconVectorDrawable(boolean iconVectorDrawable) {
        this.iconVectorDrawable = iconVectorDrawable;
        return this;
    }

    public boolean isDisplayCloseIcon() {
        return displayCloseIcon;
    }

    public SabianMaterialModal setDisplayCloseIcon(boolean displayCloseIcon) {
        this.displayCloseIcon = displayCloseIcon;
        return this;
    }
    public SabianMaterialModal setButtonOkVisibility(int visiblity){

        this.btn_Ok.setVisibility(visiblity);

        return this;
    }

    public int getHeaderTextColor() {
        return headerTextColor;
    }

    public SabianMaterialModal setHeaderTextColor(int headerTextColor) {
        this.headerTextColor = headerTextColor;
        this.sct_Title.setTextColor(headerTextColor);
        return this;
    }

    public boolean isDisplayHeaderIcon() {
        return displayHeaderIcon;
    }

    public SabianMaterialModal setDisplayHeaderIcon(boolean displayHeaderIcon) {
        this.displayHeaderIcon = displayHeaderIcon;
        return this;
    }

    public boolean isDisplayHeaderText() {
        return displayHeaderText;
    }

    public SabianMaterialModal setDisplayHeaderText(boolean displayHeaderText) {
        this.displayHeaderText = displayHeaderText;
        return this;
    }

    public Drawable getHeaderIconDrawable() {
        return headerIconDrawable;
    }

    public SabianMaterialModal setHeaderIconDrawable(Drawable headerIconDrawable) {
        this.headerIconDrawable = headerIconDrawable;
        this.sfi_HeaderIcon.setImageDrawable(headerIconDrawable);
        return this;
    }

    public int getHeaderHeight() {
        return headerHeight;
    }

    public SabianMaterialModal setHeaderHeight(int headerHeight) {

        this.headerHeight = headerHeight;

        vg_Header.getLayoutParams().height=headerHeight;

        vg_Header.requestLayout();

        return this;
    }
    public SabianMaterialModal setButtonOkEnabled(boolean enabled){

        btn_Ok.setEnabled(enabled);

        return this;
    }
    public SabianMaterialModal setButtonCancelEnabled(boolean enabled){

        btn_Cancel.setEnabled(enabled);

        return this;
    }
}
