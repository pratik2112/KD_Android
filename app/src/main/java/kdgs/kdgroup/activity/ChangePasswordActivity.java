package kdgs.kdgroup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseActivity;
import kdgs.kdgroup.config.CommonFunctions;
import kdgs.kdgroup.config.Constants;
import kdgs.kdgroup.config.KDGConfig;
import kdgs.kdgroup.config.WebService;

public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_home)
    ImageView iv_home;
    @BindView(R.id.iv_edit)
    ImageView iv_edit;

    @BindView(R.id.edt_old_pass)
    EditText edt_old_pass;
    @BindView(R.id.edt_new_pass)
    EditText edt_new_pass;
    @BindView(R.id.edt_con_pass)
    EditText edt_con_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inticompnets();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_change_password;
    }

    private void inticompnets() {
        try {
            getSupportActionBar().hide();
            tv_title.setText(getString(R.string.str_m_chng_pwd));
            iv_home.setVisibility(View.VISIBLE);
            iv_edit.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changePassword() {
        try {
            /*{
                "u_id":"1",
                "old_password":"jatin248",
                "new_password":"jatin248"
            }*/
            JSONObject inputdata = new JSONObject();
            inputdata.put(Constants.user_id, CommonFunctions.getloginresponse(this).data.uId);
            inputdata.put(Constants.old_password, edt_old_pass.getText().toString().trim());
            inputdata.put(Constants.new_password, edt_new_pass.getText().toString().trim());

            WebService webService = new WebService(KDGConfig.WEBURL + KDGConfig.APIURL + KDGConfig.chngpwdUserURL, inputdata, true, this);
            webService.getData(Request.Method.POST, new WebService.OnResult() {
                @Override
                public void OnSuccess(JSONObject result) {
                    try {
                        if (result.getBoolean(Constants.status)) {
                            Toast.makeText(ChangePasswordActivity.this, result.getString(Constants.message), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, result.getString(Constants.message), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void OnFail(String error) {
                    Toast.makeText(ChangePasswordActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        gotoback();
    }

    @OnClick({R.id.iv_home})
    public void gotoback() {
        try {
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_update)
    public void updateClick() {
        if (edt_old_pass.getText().toString().length() == 0) {
            Toast.makeText(this, R.string.str_rgstr10, Toast.LENGTH_SHORT).show();
            edt_old_pass.requestFocus();
        } else if (edt_new_pass.getText().toString().length() == 0) {
            Toast.makeText(this, R.string.str_rgstr4, Toast.LENGTH_SHORT).show();
            edt_new_pass.requestFocus();
        } else if (edt_con_pass.getText().toString().length() == 0) {
            Toast.makeText(this, R.string.str_rgstr5, Toast.LENGTH_SHORT).show();
            edt_con_pass.requestFocus();
        } else if (!(edt_new_pass.getText().toString()).equals(edt_con_pass.getText().toString())) {
            Toast.makeText(this, R.string.str_rgstr6, Toast.LENGTH_SHORT).show();
        } else {
            changePassword();
        }
    }
}