package kdgs.kdgroup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.OnClick;
import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseActivity;
import kdgs.kdgroup.config.CommonFunctions;

public class IntroActivity extends BaseActivity {

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
            if (CommonFunctions.checkConnection(this)) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_signup)
    public void signupClick() {
        startActivity(new Intent(IntroActivity.this, SignupActivity.class));
    }

    @OnClick(R.id.btn_login)
    public void loginClick() {
        startActivity(new Intent(IntroActivity.this, LoginActivity.class));
    }
}