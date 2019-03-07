package kdgs.kdgroup.activity;

import android.content.Intent;
import android.os.Bundle;

import butterknife.OnClick;
import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseActivity;
import kdgs.kdgroup.config.CommonFunctions;

public class SignupActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inticompnets();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_signup;
    }

    private void inticompnets() {
        try {
            getSupportActionBar().hide();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (CommonFunctions.checkConnection(this)) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tv_signin)
    public void signinClick() {
        startActivity(new Intent(SignupActivity.this, SigninActivity.class));
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