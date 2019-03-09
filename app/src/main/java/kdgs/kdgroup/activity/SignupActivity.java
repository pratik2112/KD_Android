package kdgs.kdgroup.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseActivity;
import kdgs.kdgroup.config.CommonFunctions;

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
    @BindView(R.id.ll_email_confirm)
    LinearLayout ll_email_confirm;
    @BindView(R.id.ll_signup)
    LinearLayout ll_signup;

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

    @OnClick(R.id.btn_submit)
    public void submitClick() {
        String username = edt_email.getText().toString();
        if (!validateEmailAddress(username)) {
            ti_email.setError("Not a valid email address!");
        } else {
            rl_step1.setVisibility(View.GONE);
            rl_step2.setVisibility(View.VISIBLE);
            rl_step3.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_verify)
    public void verifyClick() {
        rl_step1.setVisibility(View.GONE);
        rl_step2.setVisibility(View.GONE);
        rl_step3.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_submit1)
    public void acuntSubmitClick() {
        try {
            ll_signup.setVisibility(View.GONE);
            ll_email_confirm.setVisibility(View.VISIBLE);
            tv_note.setText(Html.fromHtml(getString(R.string.str_note, edt_email.getText().toString())));
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