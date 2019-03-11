package kdgs.kdgroup.fragment;

import android.os.Bundle;

import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseFragment;

public class ComplaintFragment extends BaseFragment {

    @Override
    public int getContentView() {
        return R.layout.fragment_complaint;
    }

    public static ComplaintFragment newInstance() {
        return new ComplaintFragment();
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}