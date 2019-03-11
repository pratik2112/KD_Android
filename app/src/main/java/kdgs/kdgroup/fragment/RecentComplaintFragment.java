package kdgs.kdgroup.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseFragment;
import kdgs.kdgroup.model.ComplaintResponse;

public class RecentComplaintFragment extends BaseFragment {

    @BindView(R.id.ll_empty_view)
    LinearLayout ll_empty_view;
    @BindView(R.id.rv_complaint)
    RecyclerView rv_complaint;
    ComplaintResponse complaintResponse = null;

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
            setComplaintAdapter(complaintResponse);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}