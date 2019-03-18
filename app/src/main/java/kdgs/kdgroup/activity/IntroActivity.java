package kdgs.kdgroup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import butterknife.OnClick;
import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseActivity;
import kdgs.kdgroup.config.CommonFunctions;

public class IntroActivity extends BaseActivity {

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inticompnets();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_intro;
    }

    private void inticompnets() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_signup)
    public void signupClick() {
        startActivity(new Intent(IntroActivity.this, SignupActivity.class));
    }

    @OnClick(R.id.btn_signin)
    public void signinClick() {
        startActivity(new Intent(IntroActivity.this, SigninActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.msg_press_back_twice), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}