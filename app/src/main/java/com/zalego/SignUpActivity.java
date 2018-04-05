package com.zalego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.gc.materialdesign.widgets.ProgressDialog;
import com.gc.materialdesign.widgets.SnackBar;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sabiantools.controls.SabianFloatLabel;
import com.sabiantools.modals.SabianListModal;
import com.sabiantools.utilities.SabianUtilities;
import com.zalego.users.ZalegoUser;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SignUpActivity extends SabianActivity {

    private SabianFloatLabel txt_Firstname, txt_Email, txt_DOB, txt_Password;
    private BootstrapButton btn_Gender, btn_Languages;
    private BootstrapButton btn_Finish;
    private ZalegoUser user = new ZalegoUser();
    private AsyncHttpClient client;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init_menu();

        txt_Firstname = (SabianFloatLabel) findViewById(R.id.sfl_Firstname);
        txt_Email = (SabianFloatLabel) findViewById(R.id.sfl_Email);
        txt_DOB = (SabianFloatLabel) findViewById(R.id.sfl_DateOfBirth);
        txt_Password = (SabianFloatLabel) findViewById(R.id.sfl_Password);

        btn_Gender = (BootstrapButton) findViewById(R.id.btn_Gender);
        btn_Languages = (BootstrapButton) findViewById(R.id.btn_Languages);
        btn_Finish = (BootstrapButton) findViewById(R.id.btn_Submit);

        final ArrayList<String> gchoices = new ArrayList<>();
        gchoices.add("Male");
        gchoices.add("Female");

        final ArrayList<String> languages = new ArrayList<>();
        languages.add("Java");
        languages.add("C");
        languages.add("Python");

        btn_Gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SabianListModal modal = new SabianListModal();
                modal.setTitle("Select Gender");
                modal.setChoices(gchoices);

                modal.setOnActionSelectedListener(new SabianListModal.OnActionSelectedListener() {
                    @Override
                    public void onSelected(int i) {
                        user.setGender(gchoices.get(i));
                        btn_Gender.setText(gchoices.get(i));
                    }
                });

                modal.show(getSupportFragmentManager());
            }
        });

        btn_Languages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SabianListModal modal = new SabianListModal();
                modal.setTitle("Select Language");
                modal.setChoices(languages);

                modal.setOnActionSelectedListener(new SabianListModal.OnActionSelectedListener() {
                    @Override
                    public void onSelected(int i) {
                        user.setLanguage(languages.get(i));
                        btn_Languages.setText(languages.get(i));
                    }
                });

                modal.show(getSupportFragmentManager());
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
        user.setFirstname(txt_Firstname.getText());
        user.setEmail(txt_Email.getText());
        user.setPassword(txt_Password.getText());
        user.setDob(txt_DOB.getText());

        if(!SabianUtilities.getEmailValidator().validate(user.getEmail())){
            SabianUtilities.DisplayMessage("Invalid email address");
            return;
        }

        show_progress();
        ZalegoHelper.setUser(user);
        client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("firstname", user.firstname);
        params.put("email", user.email);
        params.put("password", user.password);
        params.put("gender", user.gender);
        params.put("language", user.language);
        params.put("dob", user.dob);

        client.post(ZalegoConfig.SIGN_UP_URL, params, new AsyncHttpResponseHandler() {

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
                        SabianUtilities.DisplayMessage("Error "+zalegoResponse.message);
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

                    show_error("Not Found", "The request URL could not be found.");

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
