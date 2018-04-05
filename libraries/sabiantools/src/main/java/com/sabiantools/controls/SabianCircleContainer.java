package com.sabiantools.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.sabiantools.R;
import com.sabiantools.utilities.SabianUtilities;

/**
 * Created by Brian Sabana on 13/02/2017.
 */

//Not fucking working
public class SabianCircleContainer extends RelativeLayout {

    protected static int DEFAULT_TITLE_COLOR = Color.CYAN;
    protected static int DEFAULT_SUBTITLE_COLOR = Color.WHITE;

    protected static String DEFAULT_TITLE = "Title";
    protected static String DEFAULT_SUBTITLE = "Subtitle";

    protected static boolean DEFAULT_SHOW_TITLE = true;
    protected static boolean DEFAULT_SHOW_SUBTITLE = true;

    protected static float DEFAULT_TITLE_SIZE = 25f;
    protected static float DEFAULT_SUBTITLE_SIZE = 20f;

    protected static int DEFAULT_STROKE_COLOR = Color.CYAN;
    protected static int DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    protected static int DEFAULT_FILL_COLOR = Color.DKGRAY;

    protected static float DEFAULT_STROKE_WIDTH = 5f;
    protected static float DEFAULT_FILL_RADIUS = 0.9f;

    protected static final int DEFAULT_VIEW_SIZE = 96;

    protected int mTitleColor = DEFAULT_TITLE_COLOR;
    protected int mSubtitleColor = DEFAULT_SUBTITLE_COLOR;
    protected int mStrokeColor = DEFAULT_STROKE_COLOR;
    protected int mBackgroundColor = DEFAULT_BACKGROUND_COLOR;
    protected int mFillColor = DEFAULT_FILL_COLOR;

    protected String mTitleText = DEFAULT_TITLE;
    protected String mSubtitleText = DEFAULT_SUBTITLE;

    protected float mTitleSize = DEFAULT_TITLE_SIZE;
    protected float mSubtitleSize = DEFAULT_SUBTITLE_SIZE;
    protected float mStrokeWidth = DEFAULT_STROKE_WIDTH;
    protected float mFillRadius = DEFAULT_FILL_RADIUS;

    protected boolean mShowTitle = DEFAULT_SHOW_TITLE;
    protected boolean mShowSubtitle = DEFAULT_SHOW_SUBTITLE;

    protected TextPaint mTitleTextPaint;
    protected TextPaint mSubTextPaint;

    protected Paint mStrokePaint;
    protected Paint mBackgroundPaint;
    protected Paint mFillPaint;

    protected RectF mInnerRectF;

    protected int mViewSize;

    public SabianCircleContainer(Context context) {
        super(context);
        init(null, 0);
    }

    public SabianCircleContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SabianCircleContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        setWillNotDraw(false);

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.SabianCircleContainer, defStyle, 0);

        if(a.hasValue(R.styleable.SabianCircleContainer_scc_titleText)){
            mTitleText = a.getString(R.styleable.SabianCircleContainer_scc_titleText);
        }

        if(a.hasValue(R.styleable.SabianCircleContainer_scc_subtitleText)){
            mSubtitleText = a.getString(R.styleable.SabianCircleContainer_scc_subtitleText);
        }

        mBackgroundColor = a.getColor(R.styleable.SabianCircleContainer_scc_backgroundColorValue,DEFAULT_BACKGROUND_COLOR);
        mStrokeColor = a.getColor(R.styleable.SabianCircleContainer_scc_strokeColorValue,DEFAULT_STROKE_COLOR);
        mFillColor = a.getColor(R.styleable.SabianCircleContainer_scc_fillColor,DEFAULT_FILL_COLOR);

        mStrokeWidth = a.getFloat(R.styleable.SabianCircleContainer_scc_strokeWidthSize,DEFAULT_STROKE_WIDTH);
        mFillRadius = a.getFloat(R.styleable.SabianCircleContainer_scc_fillRadius,DEFAULT_FILL_RADIUS);

        a.recycle();

        //Stroke Paint
        mStrokePaint = new Paint();
        mStrokePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(mStrokeColor);
        mStrokePaint.setStrokeWidth(mStrokeWidth);

        //Background Paint
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setColor(mBackgroundColor);

        //Fill Paint
        mFillPaint = new Paint();
        mFillPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mFillPaint.setStyle(Paint.Style.FILL);
        mFillPaint.setColor(mFillColor);

        mInnerRectF = new RectF();

    }

    private void invalidateTextPaints(){
        mTitleTextPaint.setTextSize(mTitleSize);
        mSubTextPaint.setTextSize(mSubtitleSize);
        invalidate();
    }

    private void invalidatePaints(){
        mBackgroundPaint.setColor(mBackgroundColor);
        mStrokePaint.setColor(mStrokeColor);
        mFillPaint.setColor(mFillColor);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float radius=Math.min(getWidth(),getHeight()/2);

        canvas.drawCircle(getWidth()/2, getHeight()/2, radius , mFillPaint);
    }

    /**
     * Gets the stroke color attribute value.
     * @return The stroke color attribute value.
     */
    public int getStrokeColor() {
        return mStrokeColor;
    }

    /**
     * Gets the background color attribute value.
     * @return The background color attribute value.
     */
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    /**
     * Gets the fill color attribute value.
     * @return The fill color attribute value.
     */
    public int getFillColor() {
        return mStrokeColor;
    }

    /**
     * Sets the view's stroke color attribute value.
     * @param strokeColor The stroke color attribute value to use.
     */
    public void setStrokeColor(int strokeColor) {
        mStrokeColor = strokeColor;
        invalidatePaints();
    }

    /**
     * Sets the view's background color attribute value.
     * @param backgroundColor The background color attribute value to use.
     */
    public void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
        invalidatePaints();
    }

    /**
     * Sets the view's fill color attribute value.
     * @param fillColor The fill color attribute value to use.
     */
    public void setFillColor(int fillColor) {
        mFillColor = fillColor;
        invalidatePaints();
    }

    /**
     * Gets the stroke width dimension attribute value.
     * @return The stroke width dimension attribute value.
     */
    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    /**
     * Sets the view's stroke width attribute value.
     * @param strokeWidth The stroke width attribute value to use.
     */
    public void setBackgroundColor(float strokeWidth) {
        mStrokeWidth = strokeWidth;
        invalidate();
    }

    /**
     * Gets the fill radius dimension attribute value.
     * @return The fill radius dimension attribute value.
     */
    public float getFillRadius() {
        return mFillRadius;
    }

    /**
     * Sets the view's fill radius attribute value.
     * @param fillRadius The fill radius attribute value to use.
     */
    public void setFillRadius(float fillRadius) {
        mFillRadius = fillRadius;
        invalidate();
    }

    /**
     * Gets the title size dimension attribute value.
     * @return The title size dimension attribute value.
     */
    public float getTitleSize() {
        return mTitleSize;
    }

    /**
     * Sets the view's title size dimension attribute value.
     * @param titleSize The title size dimension attribute value to use.
     */
    public void setTitleSize(float titleSize) {
        mTitleSize = titleSize;
        invalidateTextPaints();
    }

    /**
     * Gets the subtitle size dimension attribute value.
     * @return The subtitle size dimension attribute value.
     */
    public float getSubtitleSize() {
        return mSubtitleSize;
    }

    /**
     * Sets the view's subtitle size dimension attribute value.
     * @param subtitleSize The subtitle size dimension attribute value to use.
     */
    public void setSubtitleSize(float subtitleSize) {
        mSubtitleSize = subtitleSize;
        invalidateTextPaints();
    }
}
