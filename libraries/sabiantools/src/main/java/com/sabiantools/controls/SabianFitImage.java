package com.sabiantools.controls;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sabiantools.R;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Dannick on 3/25/2016.
 */
public class SabianFitImage extends ImageView {

    public ImageView returnImage;
    public Context mContext;
    public boolean useAnimation =false;
    public boolean scaleImage=false;


    public SabianFitImage(Context context)
    {
        super(context);
        returnImage=this;
        mContext=context;
    }
    public SabianFitImage(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
        returnImage=this;
        mContext=context;

    }
    @Override
    public void onMeasure(int widthMeasureSpec,int heightMeasureSpec)
    {
        if(this.scaleImage) {
            try {
                Drawable d = getDrawable();

                if (d != null) {
                    float new_width = (float) MeasureSpec.getSize(widthMeasureSpec);
                    float intrinsicWidth = (float) d.getIntrinsicWidth();
                    float intrinsicHeight = (float) d.getIntrinsicHeight();
                    int new_height = (int) Math.ceil(new_width * (intrinsicHeight / intrinsicWidth));
                    setMeasuredDimension((int) new_width, new_height);

                } else {
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                }
            } catch (Exception ex) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }
        else
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    public void ScaleImage(boolean scaleImage)
    {
        this.scaleImage=scaleImage;
    }

    public void SabianLoadWithPicasso(Uri uri)
    {
        Picasso.with(mContext).load(uri).into(returnImage);
    }

    public void SabianLoadAsynchronousUri(Uri uri,boolean UseAnimation)
    {
        if(UseAnimation)
        {
            this.useAnimation=true;
        }
        new SabianLoadAsyncUrl().execute(uri);
    }
    private class SabianLoadAsyncUrl extends AsyncTask<Uri,String,Bitmap>
    {
        private boolean is_Loaded=false;
        @Override
        protected Bitmap doInBackground(Uri...url)
        {
            Uri uri=url[0];
            InputStream stream=null;
            Bitmap bitmap=null;

            try {
                stream = mContext.getContentResolver().openInputStream(uri);
                bitmap= BitmapFactory.decodeStream(stream);
                stream.close();
                this.is_Loaded=true;

            }
            catch(FileNotFoundException ex)
            {
                ex.printStackTrace();
                is_Loaded=false;
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
                is_Loaded=false;
            }
            return bitmap;

        }
        @Override
        protected void onPostExecute(final Bitmap bitmap)
        {
            try {
                if (useAnimation) {

                    Animation fadeIn = AnimationUtils.loadAnimation(mContext, R.anim.sabian_fade_in);
                    fadeIn.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            returnImage.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    returnImage.startAnimation(fadeIn);
                } else {
                    returnImage.setImageBitmap(bitmap);
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }


        }
        @Override
        protected void onPreExecute()
        {
            returnImage.setBackgroundResource(R.drawable.background);

        }


    }
}
