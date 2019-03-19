package kdgs.kdgroup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import static kdgs.kdgroup.config.CommonFunctions.validateEmailAddress;

public class SignupActivity extends BaseActivity {

    @BindView(R.id.rl_step1)
    RelativeLayout rl_step1;
    @BindView(R.id.rl_step2)
    RelativeLayout rl_step2;
    @BindView(R.id.rl_step3)
    RelativeLayout rl_step3;
    @BindView(R.id.tv_note)
    TextView tv_note;

    @BindView(R.id.ti_email)
    TextInputLayout ti_email;
    @BindView(R.id.edt_email)
    EditText edt_email;
    @BindView(R.id.ti_mobile)
    TextInputLayout ti_mobile;
    @BindView(R.id.edt_mobile)
    EditText edt_mobile;
    @BindView(R.id.btn_otp_submit)
    Button btn_otp_submit;

    @BindView(R.id.ti_verify)
    TextInputLayout ti_verify;
    @BindView(R.id.edt_verify)
    EditText edt_verify;

    @BindView(R.id.ti_fname)
    TextInputLayout ti_fname;
    @BindView(R.id.edt_fname)
    EditText edt_fname;
    @BindView(R.id.ti_lname)
    TextInputLayout ti_lname;
    @BindView(R.id.edt_lname)
    EditText edt_lname;
    @BindView(R.id.ti_uname)
    TextInputLayout ti_uname;
    @BindView(R.id.edt_uname)
    EditText edt_uname;
    @BindView(R.id.ti_pass)
    TextInputLayout ti_pass;
    @BindView(R.id.edt_pass)
    EditText edt_pass;
    @BindView(R.id.ti_conf_pass)
    TextInputLayout ti_conf_pass;
    @BindView(R.id.edt_con_pass)
    EditText edt_con_pass;

    @BindView(R.id.ll_email_confirm)
    LinearLayout ll_email_confirm;
    @BindView(R.id.nv_signup)
    NestedScrollView nv_signup;
    String otpValue;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_otp_submit)
    public void otpsubmitClick() {
        try {
            String username = edt_email.getText().toString().trim();
            if (edt_email.getText().toString().length() == 0) {
                Toast.makeText(this, R.string.str_rgstr1, Toast.LENGTH_SHORT).show();
                edt_email.requestFocus();
            } else if (!validateEmailAddress(username)) {
                ti_email.setError(getString(R.string.str_rgstr2));
                edt_email.requestFocus();
            } else {
                rl_step1.setVisibility(View.GONE);
                rl_step2.setVisibility(View.VISIBLE);
                rl_step3.setVisibility(View.GONE);
                otpSend();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void otpSend() {
        try {
            /*{ "email":"jatinvaghasiya@gmail.com" }*/

            JSONObject inputdata = new JSONObject();
            inputdata.put(Constants.u_email, edt_email.getText().toString().trim());

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
                    Toast.makeText(SignupActivity.this, error, Toast.LENGTH_SHORT).show();
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
            rl_step1.setVisibility(View.GONE);
            rl_step2.setVisibility(View.VISIBLE);
            rl_step3.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_verify)
    public void verifyClick() {
        try {
            if (edt_verify.getText().toString().trim().equalsIgnoreCase(otpValue)) {
                rl_step1.setVisibility(View.GONE);
                rl_step2.setVisibility(View.GONE);
                rl_step3.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, getString(R.string.invalid_otp), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_submit)
    public void submitClick() {
        try {
            if (edt_fname.getText().toString().length() == 0) {
                Toast.makeText(this, R.string.str_rgstr7, Toast.LENGTH_SHORT).show();
                edt_fname.requestFocus();
            } else if (edt_lname.getText().toString().length() == 0) {
                Toast.makeText(this, R.string.str_rgstr8, Toast.LENGTH_SHORT).show();
                edt_lname.requestFocus();
            } else if (edt_uname.getText().toString().length() == 0) {
                Toast.makeText(this, R.string.str_rgstr9, Toast.LENGTH_SHORT).show();
                edt_uname.requestFocus();
            } else if (edt_pass.getText().toString().length() == 0) {
                Toast.makeText(this, R.string.str_rgstr4, Toast.LENGTH_SHORT).show();
                edt_pass.requestFocus();
            } else if (edt_con_pass.getText().toString().length() == 0) {
                Toast.makeText(this, R.string.str_rgstr5, Toast.LENGTH_SHORT).show();
                edt_con_pass.requestFocus();
            } else if (!(edt_pass.getText().toString()).equals(edt_con_pass.getText().toString())) {
                Toast.makeText(this, R.string.str_rgstr6, Toast.LENGTH_SHORT).show();
            } else {
                registerUser();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerUser() {
        try {
            /*{
                "u_name":"jatinvaghasiya248",
                "u_email":"jatinvaghasiya248@gmail.com",
                "u_password":"jatin248",
                "u_firstname":"jatin",
                "u_lastname":"vaghasiya",
                "u_mobile":"9979542873",
                "device_type":"jfghfghhm",
                "device_id":"151121dfhgdf"
            }*/

            String userid = "";
            if (getIntent().getExtras() != null) {
                userid = getIntent().getExtras().getString(Constants.user_id);
            }

            JSONObject inputdata = new JSONObject();
            //inputdata.put(Constants.user_id, userid);
            inputdata.put(Constants.u_firstname, edt_fname.getText().toString().trim());
            inputdata.put(Constants.u_lastname, edt_lname.getText().toString().trim());
            inputdata.put(Constants.u_name, edt_uname.getText().toString().trim());
            inputdata.put(Constants.u_email, edt_email.getText().toString().trim());
            inputdata.put(Constants.u_password, edt_pass.getText().toString().trim());
            inputdata.put(Constants.u_mobile, edt_mobile.getText().toString().trim());
            inputdata.put(Constants.device_token, "");
            inputdata.put(Constants.device_type, Constants.Android);
            inputdata.put(Constants.device_id, CommonFunctions.getPreference(this, Constants.device_token, ""));

            WebService webService = new WebService(KDGConfig.WEBURL + KDGConfig.APIURL + KDGConfig.registerURL, inputdata, true, this);
            webService.getData(Request.Method.POST, new WebService.OnResult() {
                @Override
                public void OnSuccess(JSONObject result) {
                    try {
                        if (result.getBoolean(Constants.status)) {
                            Gson gson = new Gson();
                            LoginResponse loginResponse = gson.fromJson(result.toString(), LoginResponse.class);
                            CommonFunctions.setPreference(SignupActivity.this, Constants.isLogin, true);
                            CommonFunctions.setPreference(getApplicationContext(), Constants.userdata, gson.toJson(loginResponse));
                            CommonFunctions.changeactivity(SignupActivity.this, SigninActivity.class);

                            nv_signup.setVisibility(View.GONE);
                            ll_email_confirm.setVisibility(View.VISIBLE);
                            tv_note.setText(Html.fromHtml(getString(R.string.str_note, edt_email.getText().toString())));
                        } else {
                            Toast.makeText(SignupActivity.this, result.getString("message"), Toast.LENGTH_SHORT).show();
                            CommonFunctions.changeactivity(SignupActivity.this, SignupActivity.class);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void OnFail(String error) {
                    Toast.makeText(SignupActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.tv_signin, R.id.btn_ok})
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