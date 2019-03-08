package kdgs.kdgroup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;
import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseActivity;
import kdgs.kdgroup.config.CommonFunctions;

public class ForgotActivity extends BaseActivity {

    @BindView(R.id.rl_send_otp)
    RelativeLayout rl_send_otp;
    @BindView(R.id.rl_verify_otp)
    RelativeLayout rl_verify_otp;
    @BindView(R.id.rl_reset_pwd)
    RelativeLayout rl_reset_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inticompnets();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_forgot;
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

    @OnClick(R.id.btn_send_otp)
    public void sendOTPClick() {
        rl_send_otp.setVisibility(View.GONE);
        rl_verify_otp.setVisibility(View.VISIBLE);
        rl_reset_pwd.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_verify)
    public void verifyClick() {
        rl_send_otp.setVisibility(View.GONE);
        rl_verify_otp.setVisibility(View.GONE);
        rl_reset_pwd.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_submit)
    public void submitClick() {
        startActivity(new Intent(ForgotActivity.this, SigninActivity.class));
        finish();
    }

    @OnClick(R.id.tv_signin)
    public void signinClick() {
        startActivity(new Intent(ForgotActivity.this, SigninActivity.class));
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