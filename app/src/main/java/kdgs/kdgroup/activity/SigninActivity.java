package kdgs.kdgroup.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;

import butterknife.OnClick;
import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseActivity;
import kdgs.kdgroup.config.CommonFunctions;
import kdgs.kdgroup.config.Constants;

public class SigninActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inticompnets();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_signin;
    }

    private void inticompnets() {
        try {
            getSupportActionBar().hide();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_signin)
    public void signinClick() {
        try {
            /*if (output.getString(verified).equalsIgnoreCase(yes)) {
                Gson gson = new Gson();
                LogingResponce logingResponce = gson.fromJson(output.toString(), LogingResponce.class);
                CommonFunctions.setPreference(SigninActivity.this, Constants.isLogin, true);
                CommonFunctions.setPreference(getApplicationContext(), Constants.userdata, gson.toJson(logingResponce));
                CommonFunctions.changeactivity(SigninActivity.this, DashboardActivity.class);
            } else {
                startActivity(new Intent(SigninActivity.this, SigninActivity.class));
                finish();
            }*/
            startActivity(new Intent(SigninActivity.this, DashboardActivity.class));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tv_signup)
    public void signupClick() {
        startActivity(new Intent(SigninActivity.this, SignupActivity.class));
        finish();
    }

    @OnClick(R.id.tv_forgot)
    public void forgotClick() {
        startActivity(new Intent(SigninActivity.this, ForgotActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        gotoback();
    }

    public void gotoback() {
        try {
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}