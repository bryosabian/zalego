package com.sabiantools.utilities;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.sabiantools.R;

import com.gc.materialdesign.widgets.SnackBar;

/**
 * Created by Dannick on 6/20/2016.
 */
public class SabianFragment extends Fragment {

    protected View root;

    protected boolean isStarted=false;
    protected boolean isVisible=false;

    protected OnSabianFragmentAnimationLoadListener onAnimationLoaded;

    @Nullable
    protected View findViewById(@IdRes int id)
    {
        return root.findViewById(id);
    }
    public String getTitle()
    {
        return "Sabian Fragment";
    }
    public static Fragment getInstance()
    {
        return new SabianFragment();
    }
    protected void displayShortMessage(String message)
    {
        SnackBar snackBar=new SnackBar(getActivity(),message);

        snackBar.show();
    }
    protected void init_dependancies()
    {
        //sabianSystem=new SabianSystem(getContext());
    }
    protected void setRootView(View view){
        this.root=view;
    }

    @Override
    public void onStart() {
        super.onStart();
        isStarted=true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isStarted=false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible=isVisibleToUser;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof OnSabianFragmentAnimationLoadListener) {
            onAnimationLoaded = (OnSabianFragmentAnimationLoadListener) activity;
        }
    }
    @Override
    public Animation onCreateAnimation(int transit, final boolean enter, int nextAnim) {

        if(!loadAnimation())
            return super.onCreateAnimation(transit,enter,nextAnim);

        final int animID = (enter) ? getAnimationStart() : getAnimationExit();

        final Animation animation = AnimationUtils.loadAnimation(getActivity(), animID);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                if(enter)
                if(onAnimationLoaded!=null)
                    onAnimationLoaded.onEnterAnimationStarted();
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if(enter)
                    if(onAnimationLoaded!=null)
                        onAnimationLoaded.onEnterAnimationFinished();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        return animation;
    }

    protected int getAnimationStart(){
        return R.anim.sabian_login_popup_show;
    }
    protected int getAnimationExit(){
        return R.anim.sabian_fade_out;
    }
    protected boolean loadAnimation(){
        return false;
    }

    public interface OnSabianFragmentAnimationLoadListener {
        void onEnterAnimationStarted();
        void onEnterAnimationFinished();
    }
}
