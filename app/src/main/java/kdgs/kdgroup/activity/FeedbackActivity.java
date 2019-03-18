package kdgs.kdgroup.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseActivity;

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_home)
    ImageView iv_home;
    @BindView(R.id.iv_edit)
    ImageView iv_edit;
    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.edt_adrs)
    EditText edt_adrs;
    @BindView(R.id.edt_area)
    EditText edt_area;
    @BindView(R.id.edt_mobile)
    EditText edt_mobile;
    @BindView(R.id.edt_email)
    EditText edt_email;
    @BindView(R.id.edt_category)
    EditText edt_category;
    @BindView(R.id.edt_feedback)
    EditText edt_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inticompnets();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_feedback;
    }

    private void inticompnets() {
        try {
            getSupportActionBar().hide();
            tv_title.setText(getString(R.string.str_m_feedback));
            iv_home.setVisibility(View.VISIBLE);
            iv_edit.setVisibility(View.GONE);
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

    @OnClick(R.id.btn_clear)
    public void clearClick() {
        try {
            edt_name.setText("");
            edt_adrs.setText("");
            edt_area.setText("");
            edt_mobile.setText("");
            edt_email.setText("");
            edt_category.setText("");
            edt_feedback.setText("");
            edt_name.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}