package kdgs.kdgroup.fragment;

import android.os.Bundle;

import org.json.JSONObject;

import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseFragment;
import kdgs.kdgroup.config.Constants;

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
            registerUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerUser() {
        try {
                /*{
                    "u_name":"jatin11",
                    "u_email":"jatin112vaghasiya248@gmail.com",
                    "u_password":"jatin248",
                    "u_firstname":"jatin",
                    "u_lastname":"vaghasiya",
                    "u_mobile":"9979542873",
                    "device_token":"rfgdfhy",
                    "device_type":"jfghfghhm",
                    "device_id":"151121dfhgdf"
                }*/
            String userid = "";
            if (getActivity().getIntent().getExtras() != null) {
                userid = getActivity().getIntent().getExtras().getString(Constants.user_id);
            }

            JSONObject inputdata = new JSONObject();
            inputdata.put(Constants.device_type, Constants.Android);
            inputdata.put(Constants.user_id, userid);
            /*inputdata.put(Constants.u_name, et_email.getText().toString().trim());
            inputdata.put(Constants.u_firstname, et_firstname.getText().toString().trim());
            inputdata.put(Constants.u_lastname, et_lastname.getText().toString().trim());*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}