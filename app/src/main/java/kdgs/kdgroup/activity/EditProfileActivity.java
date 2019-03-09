package kdgs.kdgroup.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseActivity;

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
    private boolean isselected = false;
    @BindView(R.id.tv_dob)
    TextView tv_dob;
    private int mYear, mMonth, mDay;

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
            iv_close.setOnClickListener(this);
            iv_male.setOnClickListener(this);
            iv_female.setOnClickListener(this);
            tv_dob.setOnClickListener(this);
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
            case R.id.iv_close:
                finish();
                break;
            case R.id.iv_male:
                if (!isselected) {
                    iv_male.setImageResource(R.drawable.ic_male);
                    iv_female.setImageResource(R.drawable.ic_g_female);
                }
                break;
            case R.id.iv_female:
                if (!isselected) {
                    iv_female.setImageResource(R.drawable.ic_female);
                    iv_male.setImageResource(R.drawable.ic_g_male);
                }
                break;
            case R.id.tv_dob:
                dobpicker();
                break;
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
                        tv_dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}