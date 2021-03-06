package kdgs.kdgroup.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kdgs.kdgroup.R;
import kdgs.kdgroup.activity.AddAddressActivity;
import kdgs.kdgroup.activity.AddressActivity;
import kdgs.kdgroup.config.CommonFunctions;
import kdgs.kdgroup.config.Constants;
import kdgs.kdgroup.config.KDGConfig;
import kdgs.kdgroup.config.WebService;
import kdgs.kdgroup.model.AddressResponse;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.MyViewHolder> {

    private Activity mContext;
    AddressResponse myaddresslist;
    List<AddressResponse.Data> addressList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_addres_type, tv_address, tv_pincode;
        public Button btn_edit, btn_delete;

        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_addres_type = (TextView) view.findViewById(R.id.tv_addres_type);
            tv_address = (TextView) view.findViewById(R.id.tv_address);
            tv_pincode = (TextView) view.findViewById(R.id.tv_pincode);
            btn_edit = (Button) view.findViewById(R.id.btn_edit);
            btn_delete = (Button) view.findViewById(R.id.btn_delete);
        }
    }

    public AddressListAdapter(Activity mContext, AddressResponse myaddresslist) {
        this.mContext = mContext;
        this.myaddresslist = myaddresslist;
        addressList = new ArrayList<>();
        addressList.addAll(myaddresslist.data);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_single_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            final AddressResponse.Data adrsData = addressList.get(position);
            holder.tv_name.setText(CommonFunctions.getloginresponse(mContext).data.uFirstname + " " + CommonFunctions.getloginresponse(mContext).data.uLastname);
            holder.tv_address.setText(adrsData.address.replace("|", "\n"));
            holder.tv_pincode.setText(mContext.getString(R.string.str_pinc, adrsData.pincode));
            holder.tv_addres_type.setText(adrsData.type);
            holder.btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(mContext, AddAddressActivity.class);
                        intent.putExtra(Constants.from, 1);
                        intent.putExtra(Constants.addressData, new Gson().toJson(adrsData));
                        mContext.startActivityForResult(intent, 900);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        deleteAddress(adrsData, position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteAddress(AddressResponse.Data adrsData, int position) {
        try {
            String adrsid = "";
            if (adrsData != null) {
                adrsid = adrsData.uaId;
            } else
                adrsid = "";

            JSONObject inputdata = new JSONObject();
            inputdata.put(Constants.ua_id, adrsid);

            WebService webService = new WebService(KDGConfig.WEBURL + KDGConfig.APIURL + KDGConfig.deleteAddressURL, inputdata, true, mContext);
            webService.getData(Request.Method.POST, new WebService.OnResult() {
                @Override
                public void OnSuccess(JSONObject result) {
                    try {
                        if (result.getBoolean(Constants.status)) {
                            if (mContext instanceof AddressActivity) {
                                ((AddressActivity) mContext).getAddressList();
                                Toast.makeText(mContext, result.getString(Constants.message), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, R.string.address_error1, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void OnFail(String error) {
                    Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return myaddresslist.data.size();
    }
}