package com.sabiantools.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.sabiantools.R;

import org.joda.time.DateTime;
import org.jsoup.Jsoup;

import java.lang.reflect.Modifier;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Brian Sabana on 12/01/2017.
 */


public class SabianUtilities {

    private static Context context;

    public final static String DEFAULT_DATE_FORMAT="%1$tY-%1$tm-%1$tdT%1$tH:%1$tM:%1$tS";

    public static void SetContext(Context context) {
        SabianUtilities.context = context;
    }

    public static void DisplayMessage(String message){
        DisplayMessage(message,TYPE_MESSAGE.ERROR);
    }

    public static void DisplayMessage(String message, SabianUtilities.TYPE_MESSAGE type_message) {

        SabianToast toast=new SabianToast(context);

        toast.setText(message);

        toast.setIcon(type_message.getIcon());

        toast.setCustomType(type_message.getDrawableResource());

        toast.display(Toast.LENGTH_SHORT);
    }

    public static void WriteLog(String message){
        Log.e("SABLog_" + GetCurrentTimestamp(), new Date().toString() + " : " + message);
    }

    public static String GetCurrentDateFormat() {
        Date date = new Date();
        String format = String.format("%te %tB %tY%s%tT%s", date, date, date, "T", date, "Z");
        return format;
    }
    public static String EscapeHtml(String string){

        if(string==null || string.isEmpty())
            return string;

        Pattern tagPattern=Pattern.compile("<.+?>");

        Matcher mRemover=tagPattern.matcher(string);

        return mRemover.replaceAll("").trim();
    }
    public static String ParseHtml(String html){

        return Jsoup.parse(html).text();
    }
    public static boolean IsConnected() {
        boolean wifi_connected, mobile_connected;

        NetworkInfo generalNetworkInfo, infoWifi, infoMobile;

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        wifi_connected = infoWifi.isConnected();

        mobile_connected = infoMobile.isConnected();

        generalNetworkInfo = manager.getActiveNetworkInfo();

        if (generalNetworkInfo != null && generalNetworkInfo.isConnected())
            return true;

        return false;
    }

    public static String GetEllipsis(String text, int length) {
        if (text.length() <= length)
            return text;

        return text.substring(0, length) + "...";
    }

    public static boolean IsStringEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static long GetCurrentTimestamp() {
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());

        long mills = c.getTimeInMillis();

        return mills;
    }

    public static boolean IsJson(String json) {
        JsonParser parser = new JsonParser();

        try {
            parser.parse(json);

            return true;
        } catch (JsonSyntaxException ex) {
            return false;
        } catch (Exception ex) {
            return false;
        }

    }
    public static Gson GetStandardGson(){

        return GetGson(Modifier.PRIVATE, Modifier.TRANSIENT);
    }
    public static Gson GetGson(int... excludeModifiers){

        GsonBuilder builder = new GsonBuilder();

        builder.excludeFieldsWithModifiers(excludeModifiers);

        return builder.create();
    }
    public static String GetFormattedDateString(Date date){

        String format=String.format(SabianUtilities.DEFAULT_DATE_FORMAT, date);

        return format;
    }
    public static Date GetFormattedDate(String dateString){

        DateTime dt= DateTime.parse(dateString);

        return dt.toDate();
    }
    public static boolean setAutomaticHeightList(AbsListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;

            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            int totalDividersHeight = 0;

            if (listView.getClass() == GridView.class) {
                GridView gridView = (GridView) listView;

                totalItemsHeight = totalItemsHeight / 2;
            }

            // Get total height of all item dividers.
            if (listView.getClass() == ListView.class) {
                ListView lsView = (ListView) listView;
                totalDividersHeight = lsView.getDividerHeight() *
                        (numberOfItems - 1);

                //Add list vew offset
                totalItemsHeight+=context.getResources().getDimensionPixelSize(R.dimen.automatic_list_offset);
            }

            float scale = context.getResources().getDisplayMetrics().density;

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();

            int height = totalItemsHeight + totalDividersHeight;


            int scaled_margin = (int) (scale * height + 0.5f);

            params.height = height;

            listView.setLayoutParams(params);

            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }
    public static float dipToPixels(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return dipValue * scale + 0.5f;
    }

    public static float spToPixels(float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }
    public static EmailValidator getEmailValidator() {
        return new EmailValidator();
    }

    public static Context getContext() {
        return context;
    }

    public enum TYPE_MESSAGE {
        ERROR("fa-exclamation-circle",R.drawable.sabian_toast_layout_danger),
        SUCCESS("fa-check-circle",R.drawable.sabian_toast_layout_success),
        INFORMATION("fa-info-circle", R.drawable.sabian_toast_layout_primary);

        private String icon;

        private @DrawableRes int drawableResource;

        TYPE_MESSAGE(String icon, @DrawableRes int drawableResource){
            this.icon=icon;
            this.drawableResource=drawableResource;
        }

        public String getIcon() {
            return icon;
        }

        public int getDrawableResource() {
            return drawableResource;
        }
    }

    public static class EmailValidator {

        private Pattern pattern;

        private Matcher matcher;

        private static final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        public EmailValidator() {
            pattern = Pattern.compile(EMAIL_PATTERN);
        }

        /**
         * Validate hex with regular expression
         *
         * @param hex hex for validation
         * @return true valid hex, false invalid hex
         */
        public boolean validate(final String hex) {

            matcher = pattern.matcher(hex);

            return matcher.matches();

        }
    }




}
