package com.zalego;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.gc.materialdesign.widgets.SnackBar;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sabiantools.controls.SabianFitImage;
import com.sabiantools.utilities.SabianUtilities;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.zalego.operations.SabianDrawerInitializer;
import java.util.ArrayList;
import java.util.Collections;

import cz.msebera.android.httpclient.Header;


public class HomeActivity extends SabianActivity {

    private ArrayList<ZalegoImage> images=new ArrayList<>();
    private RecyclerView rcl;
    private ImageAdapter adapter;

    private AsyncHttpClient client;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rcl=(RecyclerView)findViewById(R.id.rcl_Images);
        GridLayoutManager manager=new GridLayoutManager(this,2);
        rcl.setLayoutManager(manager);


        adapter=new ImageAdapter(images);

        if(!ZalegoHelper.isUserIsLoggedIn()){
            startActivity(new Intent(this,LogInActivity.class));
            finish();
            return;
        }
        init_menu();
        load();
    }

    private void load() {

        show_progress();

        client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(ZalegoConfig.IMAGES_URL, params, new AsyncHttpResponseHandler() {
            private void show_error(String title, String message) {
                SabianUtilities.DisplayMessage("Error " + title);
                SnackBar snackBar = new SnackBar(getThisActivity(), message);
                snackBar.show();

            }

            private void hide_error() {
            }

            @Override
            public void onSuccess(int i, Header[] headers, byte[] res) {

                hide_progress();

                Gson gson = new Gson();

                String response = new String(res).trim();

                if (SabianUtilities.IsJson(response) != true) {

                    show_error("Error Occurred", "Error occurred while trying to load content.Please try again");

                    SabianUtilities.WriteLog("Error signing up " + response);

                    return;
                }

                try {
                   ZalegoImage[] oimages=gson.fromJson(response, ZalegoImage[].class);

                    for(ZalegoImage image : oimages)
                        images.add(image);

                    adapter=new ImageAdapter(images);
                    rcl.setAdapter(adapter);

                } catch (JsonParseException ex) {

                    show_error("Error Occurred", "Error occurred while trying to load content.Please try again " + ex.getMessage());

                    SabianUtilities.WriteLog("Error serializing response " + response);

                    return;

                }
            }

            @Override
            public void onFailure(int code, Header[] headers, byte[] res, Throwable throwable) {

                hide_progress();

                if (code == 404) {

                    show_error("Not Found", "The request URL could not be found");

                    return;
                }

                if (res == null) {

                    show_error("No Response", "The request could not be completed.Please try again");

                    return;
                }

                String response = new String(res).trim();

                show_error("Error", "An unexpected error has occurred");

                SabianUtilities.WriteLog("Code " + code + " Content : " + response);


            }
        });
    }

    private void show_progress() {

        progressDialog = new ProgressDialog(this, "Loading...", R.color.sabian_material_color);

        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.setCancelable(false);

        progressDialog.show();
    }

    private void hide_progress() {
        if (progressDialog != null)
            progressDialog.hide();
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


    private class ImageAdapter extends RecyclerView.Adapter<Holder>{

        private ArrayList<ZalegoImage> images=new ArrayList<>();

        public ImageAdapter(ArrayList<ZalegoImage> images) {
            this.images = images;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_images,null);
            return new Holder(getApplicationContext(),view);
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
            Picasso.with(context).load(zalegoImage.image).centerCrop().fit().into(image, new Callback() {
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
