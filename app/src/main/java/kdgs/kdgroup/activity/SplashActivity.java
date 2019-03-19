package kdgs.kdgroup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import kdgs.kdgroup.config.CommonFunctions;
import kdgs.kdgroup.config.Constants;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initcompnets();
    }

    private void initcompnets() {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!CommonFunctions.getPreference(SplashActivity.this, Constants.isLogin, false)) {
                        startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                        finish();
                    } else {
                        if (CommonFunctions.getloginresponse(SplashActivity.this) != null) {
                            startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                        }
                    }
                }
            }, SPLASH_TIME_OUT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}