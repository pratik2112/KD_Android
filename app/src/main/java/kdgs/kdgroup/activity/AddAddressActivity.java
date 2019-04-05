package kdgs.kdgroup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class AddAddressActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_home)
    ImageView iv_home;
    @BindView(R.id.iv_edit)
    ImageView iv_edit;

    @BindView(R.id.edt_fl_no)
    EditText edt_fl_no;
    @BindView(R.id.edt_so_aprt)
    EditText edt_so_aprt;
    @BindView(R.id.edt_adrs)
    EditText edt_adrs;
    @BindView(R.id.edt_pin)
    EditText edt_pin;
    @BindView(R.id.rg_addres_type)
    RadioGroup rg_addres_type;
    @BindView(R.id.rb_homme)
    RadioButton rb_homme;
    @BindView(R.id.rb_office)
    RadioButton rb_office;
    @BindView(R.id.rb_other)
    RadioButton rb_other;
    int from;
    AddressResponse.Data dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inticompnets();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_address;
    }

    private void inticompnets() {
        try {
            getSupportActionBar().hide();
            tv_title.setText(getString(R.string.str_adrs));
            iv_home.setVisibility(View.VISIBLE);
            iv_edit.setVisibility(View.GONE);

            if (getIntent().getExtras() != null) {
                from = getIntent().getExtras().getInt(Constants.from);
                if (from == 1) {
                    Gson gson = new Gson();
                    dataList = gson.fromJson(getIntent().getExtras().getString(Constants.addressData), AddressResponse.Data.class);
                    setData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setData() {
        try {
            String[] separated = dataList.address.replace("|", "%%%").split("%%%");

            if (separated.length > 0) {
                if (separated.length >= 1)
                    edt_fl_no.setText(separated[0]);
                if (separated.length >= 2)
                    edt_so_aprt.setText(separated[1]);
                if (separated.length >= 3)
                    edt_adrs.setText(separated[2]);
            }
            edt_pin.setText(dataList.pincode);
            switch (dataList.type) {
                case "Home":
                    rb_homme.setChecked(true);
                    break;
                case "Office":
                    rb_office.setChecked(true);
                    break;
                case "Other":
                    rb_other.setChecked(true);
                    break;
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

    @OnClick(R.id.btn_save)
    public void saveClick() {
        try {
            if (edt_fl_no.getText().toString().trim().length() == 0) {
                Toast.makeText(this, R.string.addaddress_title13, Toast.LENGTH_SHORT).show();
                edt_fl_no.requestFocus();
            } else if (edt_so_aprt.getText().toString().trim().length() == 0) {
                Toast.makeText(this, R.string.addaddress_title15, Toast.LENGTH_SHORT).show();
                edt_so_aprt.requestFocus();
            } else if (edt_adrs.getText().toString().trim().length() == 0) {
                Toast.makeText(this, R.string.addaddress_title16, Toast.LENGTH_SHORT).show();
                edt_adrs.requestFocus();
            } else if (edt_pin.getText().toString().trim().length() == 0 || edt_pin.getText().toString().trim().length() != 6) {
                Toast.makeText(this, R.string.addaddress_title14, Toast.LENGTH_SHORT).show();
            } else {
                saveData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveData() {
        try {
            String adrsid = "";
            if (dataList != null) {
                adrsid = dataList.uaId;
            } else
                adrsid = "";

            JSONObject inputdata = new JSONObject();
            inputdata.put(Constants.ua_id, adrsid);
            inputdata.put(Constants.user_id, CommonFunctions.getloginresponse(this).data.uId);
            inputdata.put(Constants.address, getAddress());
            inputdata.put(Constants.pincode, Integer.parseInt(edt_pin.getText().toString().trim()));
            inputdata.put(Constants.type, getAddressType());

            WebService webService = new WebService(KDGConfig.WEBURL + KDGConfig.APIURL + KDGConfig.addAddressURL, inputdata, true, this);
            webService.getData(Request.Method.POST, new WebService.OnResult() {
                @Override
                public void OnSuccess(JSONObject result) {
                    try {
                        if (result.getBoolean(Constants.status)) {
                            Intent intent = new Intent();
                            intent.putExtra(Constants.getAddressdata, true);
                            setResult(900, intent);
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            Toast.makeText(AddAddressActivity.this, result.getString(Constants.message), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void OnFail(String error) {
                    Toast.makeText(AddAddressActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAddress() {
        String address = "";
        try {
            address = edt_fl_no.getText().toString().trim() + "|" + edt_so_aprt.getText().toString().trim() + "|" + edt_adrs.getText().toString().trim();
            return address;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }

    public String getAddressType() {
        String addressType = Constants.Home;
        try {
            switch (rg_addres_type.getCheckedRadioButtonId()) {
                case R.id.rb_homme:
                    addressType = Constants.Home;
                    break;
                case R.id.rb_office:
                    addressType = Constants.Office;
                    break;
                case R.id.rb_other:
                    addressType = Constants.Other;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addressType;
    }
}