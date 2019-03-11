package kdgs.kdgroup.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseFragment;

public class StatusComplaintFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.edt_tic_no)
    EditText edt_tic_no;
    @BindView(R.id.btn_clear)
    Button btn_clear;

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
        }
    }
}