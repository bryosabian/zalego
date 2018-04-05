package com.zalego.operations;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zalego.R;
import com.sabiantools.controls.SabianMenuBar;

/**
 * Created by edith on 19/02/2018.
 */
public class SabianMenuInitializer {

    private Context _context;

    private String _title;

    private OnBackArrowPressedListener _listener = null;

    private Menu menu;

    private boolean allowSearch = false;

    private boolean displaySearch = true;

    private SabianMenuBar sabianMenuBar;

    private ActionBar actionBar;

    private TextView cartBadgeText;

    public SabianMenuInitializer(Context context) {

        this._context = context;

        this.setTitle(_context.getResources().getString(R.string.app_name));
    }

    public void init_main_menu(final AppCompatActivity activity, Toolbar toolbar, SabianMenuBar menuBar, boolean allowBackNavigation) {

        activity.setSupportActionBar(toolbar);

        this.sabianMenuBar = menuBar;

        toolbar.setTitleTextColor(activity.getResources().getColor(R.color.sabian_actionbar_text_color));

        //VectorDrawableCompat compat = VectorDrawableCompat.create(activity.getResources(), R.drawable.ic_sabian_arrow_back_24dp, activity.getTheme());

        if (allowBackNavigation) {

            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_18dp);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (_listener == null) {
                        activity.onBackPressed();
                        return;
                    }
                    _listener.onBackArrowPressed();

                }
            });
        }
        actionBar = activity.getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(true);

        actionBar.setTitle(getActivityTitle(activity));

        setTitle(getActivityTitle(activity));

    }

    public String getActivityTitle(AppCompatActivity activity) {

        String title;

        try {
            ActivityInfo activityInfo = activity.getPackageManager().getActivityInfo(
                    activity.getComponentName(), PackageManager.GET_META_DATA);
            title = activityInfo.loadLabel(activity.getPackageManager())
                    .toString();
        } catch (PackageManager.NameNotFoundException ex) {
            title = activity.getTitle().toString();
        }

        return title;
    }

    public void setOnBackArrowPressedListener(OnBackArrowPressedListener listener) {
        _listener = listener;
    }

    public void setTitle(String title) {

        this._title = title;

        if (sabianMenuBar != null)
            sabianMenuBar.setTitle(title);
    }

    public void init_menu_inflator(final AppCompatActivity activity, Menu menu) {

        int menuL = R.menu.menu_home;

        activity.getMenuInflater().inflate(menuL, menu);

        this.menu = menu;

        init_menu(activity);


    }

    private void init_menu(final AppCompatActivity activity) {




    }

    public boolean init_onselect_menu_items(final AppCompatActivity activity, MenuItem menu) {

        int id = menu.getItemId();

        switch (id){

            case R.id.action_db:

                //activity.startActivity(new Intent(_context, DatabaseManager.class));

                return true;

            case R.id.action_settings:

                //activity.startActivity(new Intent(_context, SettingsActivity.class));

                return true;
        }

        return activity.onOptionsItemSelected(menu);
    }

    public interface OnBackArrowPressedListener {
        void onBackArrowPressed();
    }

    public Menu getMenu() {
        return menu;
    }

    public boolean isAllowSearch() {
        return allowSearch;
    }

    public void setAllowSearch(boolean allowSearch) {
        this.allowSearch = allowSearch;
    }

    public SabianMenuBar getSabianMenuBar() {
        return sabianMenuBar;
    }

    public ActionBar getActionBar() {
        return actionBar;
    }


}
