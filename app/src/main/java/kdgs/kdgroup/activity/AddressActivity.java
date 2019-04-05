package kdgs.kdgroup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import kdgs.kdgroup.adapter.AddressListAdapter;
import kdgs.kdgroup.base.BaseActivity;
import kdgs.kdgroup.config.CommonFunctions;
import kdgs.kdgroup.config.Constants;
import kdgs.kdgroup.config.KDGConfig;
import kdgs.kdgroup.config.WebService;
import kdgs.kdgroup.model.AddressResponse;

public class AddressActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_home)
    ImageView iv_home;
    @BindView(R.id.iv_edit)
    ImageView iv_edit;
    @BindView(R.id.iv_add)
    ImageView iv_add;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.ll_empty_view)
    LinearLayout ll_empty_view;
    @BindView(R.id.rv_address)
    RecyclerView rv_address;
    AddressListAdapter addressListAdapter;
    AddressResponse addressResponse;

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
            tv_title.setText(getString(R.string.str_adrs));
            iv_home.setVisibility(View.VISIBLE);
            iv_add.setVisibility(View.VISIBLE);
            iv_edit.setVisibility(View.GONE);
            getAddressList();
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getAddressList();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAddressList() {
        try {
            JSONObject inputdata = new JSONObject();
            inputdata.put(Constants.ua_id, "");
            inputdata.put(Constants.user_id, CommonFunctions.getloginresponse(this).data.uId);

            WebService webService = new WebService(KDGConfig.WEBURL + KDGConfig.APIURL + KDGConfig.getAddressURL, inputdata, true, this);
            webService.getData(Request.Method.POST, new WebService.OnResult() {
                @Override
                public void OnSuccess(JSONObject result) {
                    try {
                        if (result.getBoolean(Constants.status)) {
                            Gson gson = new Gson();
                            addressResponse = gson.fromJson(result.toString(), AddressResponse.class);
                            setadapterAddress(addressResponse);
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            ll_empty_view.setVisibility(View.VISIBLE);
                            rv_address.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void OnFail(String error) {
                    Toast.makeText(AddressActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setadapterAddress(AddressResponse addressResponse) {
        try {
            ll_empty_view.setVisibility(View.GONE);
            rv_address.setVisibility(View.VISIBLE);
            addressListAdapter = new AddressListAdapter(this, addressResponse);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rv_address.setLayoutManager(mLayoutManager);
            rv_address.setItemAnimator(new DefaultItemAnimator());
            rv_address.setAdapter(addressListAdapter);
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

    @OnClick(R.id.iv_add)
    public void addAddress() {
        try {
            Intent intent = new Intent(this, AddAddressActivity.class);
            intent.putExtra(Constants.from, 0);
            startActivityForResult(intent, 900);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == 900) {
                Bundle b = data.getExtras();
                if (b.getBoolean(Constants.getAddressdata))
                    getAddressList();

            }
        }
    }
}