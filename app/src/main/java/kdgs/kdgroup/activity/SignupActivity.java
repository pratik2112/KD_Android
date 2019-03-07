package kdgs.kdgroup.activity;

import android.os.Bundle;

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
            if (CommonFunctions.checkConnection(this)) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}