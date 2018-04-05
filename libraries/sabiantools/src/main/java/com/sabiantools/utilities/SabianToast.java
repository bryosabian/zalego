package com.sabiantools.utilities;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sabiantools.R;
import com.sabiantools.controls.SabianCondensedText;
import com.beardedhen.androidbootstrap.FontAwesomeText;

/**
 * Created by Brian Sabana on 10/02/2017.
 */
public class SabianToast {

    private View view;

    private Context context;

    private FontAwesomeText toastIcon;

    private ViewGroup toastContainer;

    private SabianCondensedText toastText;

    private boolean displayIcon = true;

    public SabianToast(Context context) {
        this.context = context;
        getView();
    }

    public View getView() {

        view = LayoutInflater.from(context).inflate(R.layout.sabian_toast_layout, null);

        this.toastContainer = (RelativeLayout) view.findViewById(R.id.ll_ToastContainer);

        this.toastIcon = (FontAwesomeText) view.findViewById(R.id.ft_ToastIcon);

        this.toastText = (SabianCondensedText) view.findViewById(R.id.sct_ToastText);

        return view;

    }

    public SabianToast setText(String text) {
        this.toastText.setText(text);
        return this;
    }

    public SabianToast setIcon(String icon) {
        this.toastIcon.setIcon(icon);
        return this;
    }

    public SabianToast setDisplayIcon(boolean display) {

        toastIcon.setVisibility((display) ? View.VISIBLE : View.GONE);

        return this;
    }

    public SabianToast setSuccess() {

        toastContainer.setBackgroundResource(R.drawable.sabian_toast_layout_success);

        return this;
    }

    public SabianToast setDanger() {

        toastContainer.setBackgroundResource(R.drawable.sabian_toast_layout_danger);

        return this;
    }

    public SabianToast setPrimary() {

        toastContainer.setBackgroundResource(R.drawable.sabian_toast_layout_primary);

        return this;
    }

    public SabianToast setCustomType(@DrawableRes int backgroundResource) {

        toastContainer.setBackgroundResource(backgroundResource);

        return this;
    }

    public void display(int duration) {

        Toast toast = new Toast(context);

        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, 0);

        toast.setDuration(duration);

        toast.setView(view);

        toast.show();
    }

}
