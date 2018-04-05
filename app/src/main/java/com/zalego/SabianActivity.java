package com.zalego;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.zalego.operations.SabianDrawerInitializer;
import com.zalego.operations.SabianMenuInitializer;
import com.sabiantools.controls.SabianMenuBar;
import com.sabiantools.utilities.SabianUtilities;

import java.util.ArrayList;

/**
 * Created by Dannick on 7/3/2016.
 */
abstract public class SabianActivity extends AppCompatActivity {

    protected SabianMenuInitializer menuInitializer;

    protected SabianDrawerInitializer drawerInitializer = null;

    protected SabianMenuBar menuBar;

    protected Toolbar toolbar;

    protected DrawerLayout drawerLayout;

    protected ViewGroup mainContainer;

    protected ViewGroup drawerContainer;

    protected ListView drawerOptionList;

    protected View headerView;

    protected boolean isViewInitialized = false;

    @Override
    protected void onCreate(Bundle instance) {

        super.onCreate(instance);

        isViewInitialized = true;

        this.init_elements();
    }

    protected void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.tlb_SabianToolBar);
    }

    protected void setMenuBar() {
        menuBar = (SabianMenuBar) findViewById(R.id.smb_SabianHomeMenu);
    }

    protected void init_menu() {

        setToolbar();

        setMenuBar();

        boolean allow_back_navigation = allowBackNavigation();

        menuInitializer.init_main_menu(this, toolbar, menuBar, allow_back_navigation);

        if (initDrawer())
            getDrawer();
    }

    protected void getDrawer() {

        if (drawerInitializer != null)
            return;

        this.drawerInitializer = new SabianDrawerInitializer(this, toolbar, drawerLayout, mainContainer, drawerContainer);

        init_drawer_expandable_list();
    }

    protected void init_drawer_expandable_list() {

        this.drawerInitializer.init_drawer_list(drawerOptionList);
    }

    public SabianDrawerInitializer getDrawerInitializer() {
        return drawerInitializer;
    }

    public void setDrawerInitializer(SabianDrawerInitializer drawerInitializer) {
        this.drawerInitializer = drawerInitializer;
    }

    private void init_elements() {

        SabianUtilities.SetContext(getApplicationContext());

        this.menuInitializer = new SabianMenuInitializer(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (menuInitializer != null) {
            menuInitializer.init_menu_inflator(this, menu);
        }

        refresh_menus();

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (menuInitializer != null)
            return menuInitializer.init_onselect_menu_items(this, item);

        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerInitializer != null) {
            if (drawerInitializer.DrawerClosed()) {

                super.onBackPressed();

                return;
            }
            return;
        }
        super.onBackPressed();
    }



    protected boolean allowBackNavigation() {
        return true;
    }

    protected boolean initDrawer() {
        return false;
    }

    protected Activity getThisActivity() {
        return this;
    }

    protected void init_transparent_status_bar() {

    /*Sets translucent background status bar with no theming. Only works for Kitkat and above*/
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

            int sHeight=getStatusBarHeight();

            sHeight=sHeight-(int)(Math.ceil(13*getResources().getDisplayMetrics().density));

            toolbar.setPadding(toolbar.getPaddingLeft(), sHeight, toolbar.getPaddingRight(), toolbar.getPaddingBottom());

            SabianUtilities.WriteLog("Window translucent flags set nigga");
        }
    }

    private int getStatusBarHeight(){

        int res=0;

        int resID=getResources().getIdentifier("status_bar_height","dimen","android");

        if(resID>0){
            res=getResources().getDimensionPixelSize(resID);
        }else{
            res=(int)Math.ceil((Build.VERSION.SDK_INT>= Build.VERSION_CODES.M?24:25)*getResources().getDisplayMetrics().density);
        }


        SabianUtilities.WriteLog("Status bar height is " + res + " nigga");

        return res;
    }

    @Override
    public void onResume() {

        super.onResume();

        if (isViewInitialized) {

            refresh_menus();
        }
    }

    protected boolean displaySearch() {
        return false;
    }

    protected boolean allowSearch() {
        return false;
    }

    protected void refresh_menus() {
        Menu menu = this.menuInitializer.getMenu();
    }
}
