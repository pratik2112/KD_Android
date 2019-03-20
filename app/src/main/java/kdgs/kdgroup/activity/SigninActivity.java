package kdgs.kdgroup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseActivity;
import kdgs.kdgroup.config.CommonFunctions;
import kdgs.kdgroup.config.Constants;
import kdgs.kdgroup.config.KDGConfig;
import kdgs.kdgroup.config.WebService;
import kdgs.kdgroup.model.LoginResponse;

public class SigninActivity extends BaseActivity {

    @BindView(R.id.ti_uname)
    TextInputLayout ti_uname;
    @BindView(R.id.edt_uname)
    EditText edt_uname;
    @BindView(R.id.ti_pass)
    TextInputLayout ti_pass;
    @BindView(R.id.edt_pass)
    EditText edt_pass;
    @BindView(R.id.btn_signin)
    Button btn_signin;

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
            /*{
                "u_name":"jatinvaghasiya248",
                "u_password":"jatin248",
                "device_token":"rfgdfhy",
                "device_type":"jfghfghhm",
                "device_id":"151121dfhgdf"
            }*/
            if (edt_uname.getText().toString().trim().length() == 0) {
                Toast.makeText(this, R.string.str_rgstr3, Toast.LENGTH_SHORT).show();
            } else if (edt_pass.getText().toString().trim().length() == 0) {
                Toast.makeText(this, R.string.str_rgstr4, Toast.LENGTH_SHORT).show();
            } else {
                JSONObject inputdata = new JSONObject();
                inputdata.put(Constants.u_name, edt_uname.getText().toString().trim());
                inputdata.put(Constants.u_password, edt_pass.getText().toString().trim());
                inputdata.put(Constants.device_token, "");
                inputdata.put(Constants.device_type, Constants.Android);
                inputdata.put(Constants.device_id, CommonFunctions.getPreference(this, Constants.device_token, ""));
                WebService webService = new WebService(KDGConfig.WEBURL + KDGConfig.APIURL + KDGConfig.loginURL, inputdata, true, this);
                webService.getData(Request.Method.POST, new WebService.OnResult() {
                    @Override
                    public void OnSuccess(JSONObject result) {
                        try {
                            if (result.getBoolean(Constants.status)) {
                                Gson gson = new Gson();
                                LoginResponse loginResponse = gson.fromJson(result.toString(), LoginResponse.class);
                                CommonFunctions.setPreference(SigninActivity.this, Constants.isLogin, true);
                                CommonFunctions.setPreference(getApplicationContext(), Constants.userdata, gson.toJson(loginResponse));
                                CommonFunctions.changeactivity(SigninActivity.this, DashboardActivity.class);
                            } else {
                                Toast.makeText(SigninActivity.this, getString(R.string.invalid_usr_pwd), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void OnFail(String error) {
                        Toast.makeText(SigninActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
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