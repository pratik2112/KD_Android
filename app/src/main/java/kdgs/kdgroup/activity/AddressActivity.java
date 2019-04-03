package kdgs.kdgroup.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import kdgs.kdgroup.model.AddressResponse;
import kdgs.kdgroup.utills.EqualSpacingItemDecoration;

public class AddressActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_home)
    ImageView iv_home;
    @BindView(R.id.iv_edit)
    ImageView iv_edit;

    @BindView(R.id.tv_addadress)
    TextView tv_addadress;
    @BindView(R.id.ll_empty_view)
    LinearLayout ll_empty_view;
    @BindView(R.id.rv_address)
    RecyclerView rv_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inticompnets();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_address;
    }

    private void inticompnets() {
        try {
            getSupportActionBar().hide();
            tv_title.setText(getString(R.string.str_p_adrs));
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
}