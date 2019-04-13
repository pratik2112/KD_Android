package kdgs.kdgroup.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;

import org.json.JSONObject;

import butterknife.BindView;
import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseFragment;
import kdgs.kdgroup.config.Constants;
import kdgs.kdgroup.config.KDGConfig;
import kdgs.kdgroup.config.WebService;
import kdgs.kdgroup.model.ComplaintResponse;

public class StatusComplaintFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.edt_tic_no)
    EditText edt_tic_no;
    @BindView(R.id.btn_clear)
    Button btn_clear;
    @BindView(R.id.btn_status)
    Button btn_status;

    ComplaintResponse complaintResponse;
    ComplaintResponse.Data dataEntity;

    @Override
    public int getContentView() {
        return R.layout.fragment_status_complaint;
    }

    public static StatusComplaintFragment newInstance() {
        return new StatusComplaintFragment();
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        super.onViewReady(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        inticompnets();
    }

    private void inticompnets() {
        try {
            btn_clear.setOnClickListener(this);
            btn_status.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                edt_tic_no.setText("");
                break;
            case R.id.btn_status:
                RecentComplaintFragment fragment = new RecentComplaintFragment();
                Bundle bundle = new Bundle();
                fragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.frm_complain_status, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                getComplaintData();
                break;
        }
    }

    private void getComplaintData() {
        try {
            JSONObject inputdata = new JSONObject();
            inputdata.put(Constants.uc_no, edt_tic_no.getText().toString());

            WebService webService = new WebService(KDGConfig.WEBURL + KDGConfig.APIURL + KDGConfig.checkComplainStatusURL, inputdata, true, getContext());
            webService.getData(Request.Method.POST, new WebService.OnResult() {
                @Override
                public void OnSuccess(JSONObject result) {
                    try {
                        if (result.getBoolean(Constants.status)) {
                            Gson gson = new Gson();
                            complaintResponse = gson.fromJson(result.toString(), ComplaintResponse.class);
                        } else {
                            Toast.makeText(getContext(), result.getString(Constants.message), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void OnFail(String error) {
                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}