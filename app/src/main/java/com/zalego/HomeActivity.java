package com.zalego;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gc.materialdesign.widgets.ProgressDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.sabiantools.controls.SabianFitImage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.zalego.operations.SabianDrawerInitializer;

import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.util.ArrayList;


public class HomeActivity extends SabianActivity {

    private ArrayList<ZalegoImage> images = new ArrayList<>();
    private RecyclerView rcl;
    private ImageAdapter adapter;

    private AsyncHttpClient client;
    ProgressDialog progressDialog;
    private static final int PERMISSION_READ_CODE = 10191;
    private static final int PERMISSION_WRITE_CODE = 10192;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rcl = (RecyclerView) findViewById(R.id.rcl_Images);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rcl.setLayoutManager(manager);

        adapter = new ImageAdapter(images);

        if (!ZalegoHelper.isUserIsLoggedIn()) {
            startActivity(new Intent(this,LogInActivity.class));
            finish();
            return;
        }
        init_menu();
        load();
    }

    private boolean perGranted(String permission) {
        int res = ContextCompat.checkSelfPermission(this, permission);
        return res == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int rcode, String[] prtmissions, int[] grantRes) {
        int resG = -1;
        boolean hasRes = grantRes.length > 0;

        if (hasRes)
            resG = grantRes[0];

        if (!hasRes) {
            return;
        }

        switch (rcode) {
            case PERMISSION_READ_CODE:
                if (resG == PackageManager.PERMISSION_GRANTED) {
                    load();
                }
                break;
        }
    }

    private void load() {

        if (!perGranted(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE /*,android.Manifest.permission.CAMERA*/}, PERMISSION_READ_CODE);
            return;
        }
        String[] imgColumns = {MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.DATE_TAKEN};
        Cursor cur = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imgColumns, null, null, null);
        int colIDIndex = cur.getColumnIndex(imgColumns[0]);
        int colDateTakenIndex = cur.getColumnIndex(imgColumns[1]);

        while (cur.moveToNext()) {
            int imgID = cur.getInt(colIDIndex);
            Long date = cur.getLong(colDateTakenIndex);
            Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + imgID);
            ZalegoImage img = new ZalegoImage();

            Timestamp ts = new Timestamp(date);
            DateTime dt = new DateTime(ts.getTime());
            img.imageURI = uri;
            img.date_created = dt.toString("EEEE dd YYYY h:mm a");
            images.add(img);

        }
        rcl.setAdapter(adapter);
    }

    @Override
    protected boolean initDrawer() {

        this.drawerLayout = (DrawerLayout) findViewById(R.id.drl_SabianMainDrawer);

        this.drawerContainer = (LinearLayout) findViewById(R.id.ll_SabianDrawerContainer);

        return true;
    }

    @Override
    protected void getDrawer() {

        if (drawerInitializer != null)
            return;

        this.drawerInitializer = new SabianDrawerInitializer(this, toolbar, drawerLayout, mainContainer, drawerContainer);
    }

    @Override
    protected boolean allowBackNavigation() {
        return false;
    }

    @Override
    protected void init_menu() {
        super.init_menu();
    }


    private class ImageAdapter extends RecyclerView.Adapter<Holder> {

        private ArrayList<ZalegoImage> images = new ArrayList<>();

        public ImageAdapter(ArrayList<ZalegoImage> images) {
            this.images = images;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_images, null);
            return new Holder(getApplicationContext(), view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.setContent(images.get(position));
        }

        @Override
        public int getItemCount() {
            return images.size();
        }
    }

    private class Holder extends RecyclerView.ViewHolder {
        private SabianFitImage image;
        private TextView txt;
        private ZalegoImage zalegoImage;
        private ProgressBar progressBar;
        Context context;

        public Holder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            image = (SabianFitImage) itemView.findViewById(R.id.sfi_Image);
            txt = (TextView) itemView.findViewById(R.id.sct_ImageText);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pb_Progress);
        }

        public void setContent(ZalegoImage zalegoImage) {
            Picasso.with(context).load(zalegoImage.imageURI).centerCrop().fit().into(image, new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {

                }
            });
            txt.setText(zalegoImage.date_created);
        }
    }
}
