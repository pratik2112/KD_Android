package kdgs.kdgroup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
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

public class ForgotActivity extends BaseActivity {

    @BindView(R.id.rl_send_otp)
    RelativeLayout rl_send_otp;
    @BindView(R.id.rl_verify_otp)
    RelativeLayout rl_verify_otp;
    @BindView(R.id.rl_reset_pwd)
    RelativeLayout rl_reset_pwd;

    @BindView(R.id.ti_uname)
    TextInputLayout ti_uname;
    @BindView(R.id.edt_uname)
    EditText edt_uname;
    @BindView(R.id.edt_verify)
    EditText edt_verify;
    @BindView(R.id.edt_pwd)
    EditText edt_pwd;
    @BindView(R.id.edt_conf_pwd)
    EditText edt_conf_pwd;
    String otpValue;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_send_otp)
    public void sendOTPClick() {
        try {
            String username = edt_uname.getText().toString().trim();
            if (edt_uname.getText().toString().length() == 0) {
                Toast.makeText(this, R.string.str_rgstr1, Toast.LENGTH_SHORT).show();
                edt_uname.requestFocus();
            } else {
                checkEmailAddress(username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkEmailAddress(String email) {
        try {
            JSONObject inputdata = new JSONObject();
            inputdata.put(Constants.u_email, email);

            WebService webService = new WebService(KDGConfig.WEBURL + KDGConfig.APIURL + KDGConfig.checkEmailURL, inputdata, true, this);
            webService.getData(Request.Method.POST, new WebService.OnResult() {
                @Override
                public void OnSuccess(JSONObject result) {
                    try {
                        if (result.getBoolean(Constants.status)) {
                            otpSend();
                            //otpValue = result.getString(Constants.otp);
                            rl_send_otp.setVisibility(View.GONE);
                            rl_verify_otp.setVisibility(View.VISIBLE);
                            rl_reset_pwd.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(ForgotActivity.this, result.getString(Constants.message), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void OnFail(String error) {
                    Toast.makeText(ForgotActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_resend)
    public void resendClick() {
        try {
            edt_verify.setText("");
            edt_verify.requestFocus();
            otpSend();
            rl_send_otp.setVisibility(View.GONE);
            rl_verify_otp.setVisibility(View.VISIBLE);
            rl_reset_pwd.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_verify)
    public void verifyClick() {
        try {
            if (edt_verify.getText().toString().trim().equalsIgnoreCase(otpValue)) {
                rl_send_otp.setVisibility(View.GONE);
                rl_verify_otp.setVisibility(View.GONE);
                rl_reset_pwd.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, getString(R.string.invalid_otp), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void otpSend() {
        try {
            /*{ "email":"jatinvaghasiya@gmail.com" }*/
            JSONObject inputdata = new JSONObject();
            inputdata.put(Constants.u_email, edt_uname.getText().toString().trim());

            WebService webService = new WebService(KDGConfig.WEBURL + KDGConfig.APIURL + KDGConfig.otpURL, inputdata, true, this);
            webService.getData(Request.Method.POST, new WebService.OnResult() {
                @Override
                public void OnSuccess(JSONObject result) {
                    try {
                        if (result.getBoolean(Constants.status)) {
                            otpValue = result.getString(Constants.otp);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void OnFail(String error) {
                    Toast.makeText(ForgotActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_submit)
    public void submitClick() {
        try {
            if (edt_pwd.getText().toString().length() == 0) {
                Toast.makeText(this, R.string.str_rgstr4, Toast.LENGTH_SHORT).show();
                edt_pwd.requestFocus();
            } else if (edt_conf_pwd.getText().toString().length() == 0) {
                Toast.makeText(this, R.string.str_rgstr5, Toast.LENGTH_SHORT).show();
                edt_conf_pwd.requestFocus();
            } else if (!(edt_pwd.getText().toString()).equals(edt_conf_pwd.getText().toString())) {
                Toast.makeText(this, R.string.str_rgstr6, Toast.LENGTH_SHORT).show();
            } else {
                resetPassword();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetPassword() {
        try {
            JSONObject inputdata = new JSONObject();
            inputdata.put(Constants.u_email, edt_uname.getText().toString().trim());
            inputdata.put(Constants.new_password, edt_pwd.getText().toString().trim());

            WebService webService = new WebService(KDGConfig.WEBURL + KDGConfig.APIURL + KDGConfig.resetpwdURL, inputdata, true, this);
            webService.getData(Request.Method.POST, new WebService.OnResult() {
                @Override
                public void OnSuccess(JSONObject result) {
                    try {
                        if (result.getBoolean(Constants.status)) {
                            Toast.makeText(ForgotActivity.this, result.getString(Constants.message), Toast.LENGTH_SHORT).show();
                            CommonFunctions.changeactivity(ForgotActivity.this, SigninActivity.class);
                        } else {
                            Toast.makeText(ForgotActivity.this, result.getString(Constants.message), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void OnFail(String error) {
                    Toast.makeText(ForgotActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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