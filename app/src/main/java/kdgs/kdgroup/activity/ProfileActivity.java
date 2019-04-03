package kdgs.kdgroup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseActivity;
import kdgs.kdgroup.config.CommonFunctions;
import kdgs.kdgroup.config.Constants;
import kdgs.kdgroup.model.LoginResponse;
import kdgs.kdgroup.utills.CircleImageView;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_home)
    ImageView iv_home;

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_uname)
    TextView tv_uname;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_email)
    TextView tv_email;

    @BindView(R.id.nav_img_profile_pic)
    CircleImageView nav_img_profile_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inticompnets();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_profile;
    }

    private void inticompnets() {
        try {
            getSupportActionBar().hide();
            tv_title.setText(getString(R.string.str_m_prof));
            iv_home.setVisibility(View.VISIBLE);

            LoginResponse loginResponse = CommonFunctions.getloginresponse(this);
            if (loginResponse != null) {
                tv_name.setText(loginResponse.data.uFirstname + " " + loginResponse.data.uLastname);
                tv_uname.setText(loginResponse.data.uName);
                tv_phone.setText(loginResponse.data.uMobile);
                tv_email.setText(loginResponse.data.uEmail);
                CommonFunctions.setImageURL(this, nav_img_profile_pic, loginResponse.data.uImage);
            } else {
                tv_name.setText(R.string.txt_guest);
                tv_uname.setText(R.string.txt_guest);
                tv_phone.setText(R.string.txt_guest);
                tv_email.setText(R.string.txt_guest);
                nav_img_profile_pic.setImageResource(R.drawable.unknown);
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

    @OnClick(R.id.cv_chng_pwd)
    public void chngPwdClick() {
        startActivity(new Intent(ProfileActivity.this, ChangePasswordActivity.class));
    }

    @OnClick(R.id.iv_edit)
    public void editProfileClick() {
        Intent intent = new Intent(this, EditProfileActivity.class);
        this.startActivityForResult(intent, 600);
    }

    @OnClick(R.id.cv_adrs)
    public void adrsClick() {
        //startActivity(new Intent(this, MyaddressActivity.class));
    }
}