package kdgs.kdgroup.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;

import org.json.JSONObject;

import butterknife.BindView;
import kdgs.kdgroup.R;
import kdgs.kdgroup.adapter.ComplaintAdapter;
import kdgs.kdgroup.base.BaseFragment;
import kdgs.kdgroup.config.CommonFunctions;
import kdgs.kdgroup.config.Constants;
import kdgs.kdgroup.config.KDGConfig;
import kdgs.kdgroup.config.WebService;
import kdgs.kdgroup.model.ComplaintResponse;

public class RecentComplaintFragment extends BaseFragment {

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.ll_empty_view)
    LinearLayout ll_empty_view;
    @BindView(R.id.rv_complaint)
    RecyclerView rv_complaint;
    ComplaintResponse complaintResponse;
    ComplaintAdapter complaintAdapter;

    @Override
    public int getContentView() {
        return R.layout.fragment_recent_complaint;
    }

    public static RecentComplaintFragment newInstance() {
        return new RecentComplaintFragment();
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
            getComplaintList();
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getComplaintList();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getComplaintList() {
        try {
            JSONObject inputdata = new JSONObject();
            inputdata.put(Constants.uc_id, "");
            inputdata.put(Constants.user_id, CommonFunctions.getloginresponse(getContext()).data.uId);

            WebService webService = new WebService(KDGConfig.WEBURL + KDGConfig.APIURL + KDGConfig.getComplainDetailURL, inputdata, true, getContext());
            webService.getData(Request.Method.POST, new WebService.OnResult() {
                @Override
                public void OnSuccess(JSONObject result) {
                    try {
                        if (result.getBoolean(Constants.status)) {
                            Gson gson = new Gson();
                            complaintResponse = gson.fromJson(result.toString(), ComplaintResponse.class);
                            setComplaintAdapter(complaintResponse);
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            ll_empty_view.setVisibility(View.VISIBLE);
                            rv_complaint.setVisibility(View.GONE);
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

    private void setComplaintAdapter(ComplaintResponse complaintResponse) {
        try {
            if (complaintResponse == null) {
                ll_empty_view.setVisibility(View.VISIBLE);
                rv_complaint.setVisibility(View.GONE);
            } else {
                ll_empty_view.setVisibility(View.GONE);
                rv_complaint.setVisibility(View.VISIBLE);
                complaintAdapter = new ComplaintAdapter(getActivity(), complaintResponse);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rv_complaint.setLayoutManager(mLayoutManager);
                rv_complaint.setItemAnimator(new DefaultItemAnimator());
                rv_complaint.setAdapter(complaintAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}