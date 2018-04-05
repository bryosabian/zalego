package com.sabiantools.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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
 * Created by Brian Sabana on 27/10/2015.
 */
public class SabianHeaderBanner extends FrameLayout {

    private Context mContext;
    private LinearLayout ll_IconsLeft, ll_IconsRight;
    private TextView txt_HeaderTitle;

    private int totalIcons = 3;
    private String icon;
    private String title;
    private final static String ICON_COLOR = "#000000";
    private final static float ICON_SIZE = 12f;
    private View dividerLeft, dividerRight;


    public SabianHeaderBanner(Context context) {
        super(context);
        mContext = context;

    }

    public SabianHeaderBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init_elements(attrs);

    }

    public SabianHeaderBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init_elements(attrs);


    }

    private void init_elements(AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View rootView = inflater.inflate(R.layout.sabian_header_banner, this, true);
        txt_HeaderTitle = (TextView) rootView.findViewById(R.id.txt_SabianHederBanner);
        ll_IconsLeft = (LinearLayout) rootView.findViewById(R.id.ll_SabianHeaderBannerIconsLeft);
        ll_IconsRight = (LinearLayout) rootView.findViewById(R.id.ll_SabianHeaderBannerIconsRight);
        dividerLeft = rootView.findViewById(R.id.v_DividerLeft);
        dividerRight = rootView.findViewById(R.id.v_DividerRight);
        init_attributes(attrs);
        attach_icons();
        attach_title();

    }

    public void setIcon(String fa_icon) {
        this.icon = fa_icon;
    }

    public void setTitle(String Title) {
        this.title = Title;
        this.txt_HeaderTitle.setText(title);
    }

    public void setNumIcons(int icons) {
        this.totalIcons = icons;
    }


    private void attach_icons() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //params.topMargin=1;

        for (int i = 0; i < totalIcons; i++) {
            FontAwesomeText fontAwesomeText = new FontAwesomeText(mContext);
            fontAwesomeText.setIcon(this.icon);
            fontAwesomeText.setTextSize(ICON_SIZE);
            fontAwesomeText.setTextColor(Color.parseColor(ICON_COLOR));
            fontAwesomeText.setLayoutParams(params);
            ll_IconsRight.addView(fontAwesomeText);

        }
        for (int i = 0; i < totalIcons; i++) {
            FontAwesomeText fontAwesomeText = new FontAwesomeText(mContext);
            fontAwesomeText.setIcon(this.icon);
            fontAwesomeText.setTextSize(ICON_SIZE);
            fontAwesomeText.setTextColor(Color.parseColor(ICON_COLOR));
            fontAwesomeText.setLayoutParams(params);
            ll_IconsLeft.addView(fontAwesomeText);

        }

    }

    public void hideIcons() {
        ll_IconsRight.removeAllViews();
        ll_IconsLeft.removeAllViews();
    }

    public void hideDividers() {
        dividerRight.setVisibility(GONE);
        dividerLeft.setVisibility(GONE);
    }

    private void attach_title() {
        txt_HeaderTitle.setText(this.title);
    }

    private void init_attributes(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.SabianHeaderBanner);
        int aCount = a.getIndexCount();

        for (int i = 0; i < aCount; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SabianHeaderBanner_sabianHeaderIcon) {
                this.setIcon(a.getString(i));

            } else if (attr == R.styleable.SabianHeaderBanner_sabianNumIcons) {
                this.setNumIcons(a.getInt(i, 3));

            } else if (attr == R.styleable.SabianHeaderBanner_sabianTitle) {
                this.setTitle(a.getString(i));

            } else if (attr == R.styleable.SabianHeaderBanner_sabianDisplayDividers) {
                boolean ok = a.getBoolean(i, false);

                if (ok)
                    this.hideDividers();


            } else if (attr == R.styleable.SabianHeaderBanner_sabianHideIcons) {
                boolean ok2 = a.getBoolean(i, false);

                if (ok2)
                    this.hideIcons();

            }
        }
    }
}
