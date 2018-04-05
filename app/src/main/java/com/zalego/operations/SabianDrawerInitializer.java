package com.zalego.operations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.zalego.R;
import com.sabiantools.utilities.SabianUtilities;

import java.util.ArrayList;

/**
 * Created by Brian Sabana on 13/02/2017.
 */
public class SabianDrawerInitializer {

    private DrawerLayout mDrawer;

    private ViewGroup mDrawerContainer, mMainContainer;

    private Toolbar mToolBar;

    private Activity mActivity;

    private ActionBarDrawerToggle drawerToggle;

    private onDrawerListClickListener listListener;

    public boolean drawerExpanded = true;

    private Context context;

    private ViewGroup container;

    private View drawerView;

    private RecyclerView drawerItems;

    private boolean isUnderDevelopment = false;

    private boolean allowAnimation=false;

    public SabianDrawerInitializer(Activity activity, Toolbar toolbar, DrawerLayout drawer, ViewGroup mainContainer, ViewGroup drawerContainer) {
        this.mDrawer = drawer;

        this.mDrawerContainer = drawerContainer;

        this.mMainContainer = mainContainer;

        this.mToolBar = toolbar;

        this.mActivity = activity;

        this.context = activity.getApplicationContext();

        init_drawer();

    }

    public void init_drawer() {
        this.drawerToggle = new ActionBarDrawerToggle(mActivity, mDrawer, mToolBar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                super.onDrawerSlide(drawerView, slideOffset);

                mMainContainer.setTranslationX(slideOffset * drawerView.getWidth());

                mMainContainer.bringChildToFront(drawerView);

                mMainContainer.requestLayout();
            }
        };

        if(isAllowAnimation())
        mDrawer.addDrawerListener(drawerToggle);

        drawerToggle.syncState();
    }

    public void load_drawer_list(ViewGroup group) {
        LayoutInflater inflater = LayoutInflater.from(context);

        drawerView = inflater.inflate(R.layout.sabian_drawer_layout, group, true);
    }

    public interface onDrawerListClickListener {
        void onMenuItemClicked(int position, Object source);
    }

    public void setOnDrawerListClickListener(onDrawerListClickListener listener) {
        this.listListener = listener;
    }

    public boolean DrawerClosed() {

        if (mDrawer != null && mDrawerContainer != null) {
            if (mDrawer.isDrawerOpen(mDrawerContainer)) {
                mDrawer.closeDrawer(mDrawerContainer);

                return false;
            }
            return true;
        }
        return true;


    }
    public void init_drawer_list(RecyclerView list) {


    }
    /**
     * @deprecated
     * @param list
     */
    public void init_drawer_list(ListView list) {




    }
    public void closeDrawer(){

        mDrawer.closeDrawer(mDrawerContainer);
    }

    public void setListView(ListView list) {
        //this.drawerItems = list;
    }

    public void setRecyclerView(RecyclerView list) {
        this.drawerItems = list;
    }

    public boolean isAllowAnimation() {
        return allowAnimation;
    }

    public void setAllowAnimation(boolean allowAnimation) {
        this.allowAnimation = allowAnimation;
    }
}
