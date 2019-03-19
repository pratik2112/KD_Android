package kdgs.kdgroup.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseActivity;
import kdgs.kdgroup.config.CommonFunctions;
import kdgs.kdgroup.config.Constants;
import kdgs.kdgroup.config.KDGConfig;
import kdgs.kdgroup.config.WebService;
import kdgs.kdgroup.model.LoginResponse;
import kdgs.kdgroup.utills.CircleImageView;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_home)
    ImageView iv_home;
    @BindView(R.id.iv_edit)
    ImageView iv_edit;
    @BindView(R.id.iv_save)
    ImageView iv_save;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.iv_male)
    ImageView iv_male;
    @BindView(R.id.iv_female)
    ImageView iv_female;
    @BindView(R.id.tv_dob)
    TextView tv_dob;
    private boolean isselected = false;
    private int mYear, mMonth, mDay;
    private static final int GALLERY = 1;
    @BindView(R.id.iv_profile_pic)
    CircleImageView iv_profile_pic;

    @BindView(R.id.edt_fname)
    EditText edt_fname;
    @BindView(R.id.edt_lname)
    EditText edt_lname;
    @BindView(R.id.edt_mobile)
    EditText edt_mobile;
    @BindView(R.id.edt_email)
    EditText edt_email;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inticompnets();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_profile;
    }

    private void inticompnets() {
        try {
            getSupportActionBar().hide();
            tv_title.setText(getString(R.string.str_m_edt_prof));
            iv_home.setVisibility(View.VISIBLE);
            iv_edit.setVisibility(View.GONE);
            iv_save.setVisibility(View.VISIBLE);
            iv_close.setVisibility(View.VISIBLE);

            iv_save.setOnClickListener(this);
            iv_close.setOnClickListener(this);
            iv_male.setOnClickListener(this);
            iv_female.setOnClickListener(this);
            tv_dob.setOnClickListener(this);
            iv_profile_pic.setOnClickListener(this);

            LoginResponse loginResponse = CommonFunctions.getloginresponse(this);
            if (loginResponse != null) {
                edt_fname.setText(loginResponse.data.uFirstname);
                edt_lname.setText(loginResponse.data.uLastname);
                edt_mobile.setText(loginResponse.data.uMobile);
                edt_email.setText(loginResponse.data.uEmail);
            }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_save:
                getdataUpdate();
                break;
            case R.id.iv_close:
                finish();
                break;
            case R.id.iv_male:
                if (!isselected) {
                    gender = "1";
                    iv_male.setImageResource(R.drawable.ic_male);
                    iv_female.setImageResource(R.drawable.ic_g_female);
                }
                break;
            case R.id.iv_female:
                if (!isselected) {
                    gender = "0";
                    iv_female.setImageResource(R.drawable.ic_female);
                    iv_male.setImageResource(R.drawable.ic_g_male);
                }
                break;
            case R.id.tv_dob:
                dobpicker();
                break;
            case R.id.iv_profile_pic:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY);
                break;
        }
    }

    private void getdataUpdate() {
        try {
            /*{
                "u_id":"1",
                "u_gender":"1",
                "u_dob":"1994-05-01",
                "u_mobile":"9874561230",
                "image":"image.png"
            }*/
            JSONObject inputdata = new JSONObject();
            inputdata.put(Constants.device_type, Constants.Android);
            inputdata.put(Constants.user_id, CommonFunctions.getloginresponse(this).data.uId);
            inputdata.put(Constants.u_mobile, edt_mobile.getText().toString());
            inputdata.put(Constants.u_dob, tv_dob.getText().toString());
            inputdata.put(Constants.u_gender, gender);
            inputdata.put(Constants.image, "");

            WebService webService = new WebService(KDGConfig.WEBURL + KDGConfig.APIURL + KDGConfig.updateUserURL, inputdata, true, this);
            webService.getData(Request.Method.POST, new WebService.OnResult() {
                @Override
                public void OnSuccess(JSONObject result) {
                    try {
                        if (result.getBoolean(Constants.status)) {
                            setdata();
                            Intent intent = new Intent();
                            intent.putExtra(Constants.userdata, true);
                            setResult(600, intent);
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            //Toast.makeText(EditProfileActivity.this, R.string.address_error1, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void OnFail(String error) {
                    Toast.makeText(EditProfileActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setdata() {
        Gson gson = new Gson();
        LoginResponse loginResponse = CommonFunctions.getloginresponse(EditProfileActivity.this);
        if (loginResponse != null) {
            loginResponse.data.uFirstname = edt_fname.getText().toString();
            loginResponse.data.uLastname = edt_lname.getText().toString();
            loginResponse.data.uMobile = edt_mobile.getText().toString();
            loginResponse.data.uEmail = edt_email.getText().toString();
            CommonFunctions.setPreference(getApplicationContext(), Constants.userdata, gson.toJson(loginResponse));
        } else {
            getdataUpdate();
        }
    }

    private void dobpicker() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_dob.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}