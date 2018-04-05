package com.zalego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.widgets.ProgressDialog;
import com.gc.materialdesign.widgets.SnackBar;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sabiantools.controls.SabianFloatLabel;
import com.sabiantools.utilities.SabianUtilities;
import com.zalego.users.ZalegoUser;

import cz.msebera.android.httpclient.Header;

public class LogInActivity extends SabianActivity {

    private SabianFloatLabel txt_Email,txt_Password;
    private BootstrapButton btn_Finish;
    private ButtonFlat btn_SignUp;
    private ZalegoUser user=new ZalegoUser();
    private AsyncHttpClient client;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        init_menu();
        txt_Email=(SabianFloatLabel)findViewById(R.id.sfl_Email);
        txt_Password=(SabianFloatLabel)findViewById(R.id.sfl_Password);
        btn_Finish=(BootstrapButton)findViewById(R.id.btn_Submit);
        btn_SignUp=(ButtonFlat) findViewById(R.id.btn_SignUp);

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });
        btn_Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    private void submit() {
        user.setEmail(txt_Email.getText());
        user.setPassword(txt_Password.getText());

        if(!SabianUtilities.getEmailValidator().validate(user.getEmail())){
            SabianUtilities.DisplayMessage("Invalid email address");
            return;
        }

        show_progress();
        ZalegoHelper.setUser(user);

        client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("email", user.email);
        params.put("password", user.password);

        client.post(ZalegoConfig.LOGIN_URL, params, new AsyncHttpResponseHandler() {
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
                    ZalegoResponse zalegoResponse = gson.fromJson(response, ZalegoResponse.class);

                    if (zalegoResponse.success) {
                        SabianUtilities.DisplayMessage(zalegoResponse.message, SabianUtilities.TYPE_MESSAGE.SUCCESS);
                        ZalegoHelper.setUserIsLoggedIn(true);
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    }else{
                        SabianUtilities.DisplayMessage("Login Error "+zalegoResponse.message);
                    }

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
}
