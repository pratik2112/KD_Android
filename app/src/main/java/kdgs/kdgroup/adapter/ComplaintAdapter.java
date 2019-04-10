package kdgs.kdgroup.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kdgs.kdgroup.R;
import kdgs.kdgroup.model.ComplaintResponse;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.MyViewHolder> {

    private Activity mContext;
    ComplaintResponse myaddresslist;
    List<ComplaintResponse.Data> addressList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_sno, tv_comp_no, tv_comp_name, tv_comp, tv_comp_sdt, tv_comp_edt, tv_comp_status;

        public MyViewHolder(View view) {
            super(view);
            tv_sno = (TextView) view.findViewById(R.id.tv_sno);
            tv_comp_no = (TextView) view.findViewById(R.id.tv_comp_no);
            tv_comp_name = (TextView) view.findViewById(R.id.tv_comp_name);
            tv_comp = (TextView) view.findViewById(R.id.tv_comp);
            tv_comp_sdt = (TextView) view.findViewById(R.id.tv_comp_sdt);
            tv_comp_edt = (TextView) view.findViewById(R.id.tv_comp_edt);
            tv_comp_status = (TextView) view.findViewById(R.id.tv_comp_status);
        }
    }

    public ComplaintAdapter(Activity mContext, ComplaintResponse myaddresslist) {
        this.mContext = mContext;
        this.myaddresslist = myaddresslist;
        addressList = new ArrayList<>();
        addressList.addAll(myaddresslist.data);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_single_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            final ComplaintResponse.Data complaintData = addressList.get(position);
            holder.tv_sno.setText("1");
            holder.tv_comp_no.setText(complaintData.ucNo);
            holder.tv_comp_name.setText(complaintData.ucTitle);
            holder.tv_comp.setText(complaintData.ucDesc);
            holder.tv_comp_sdt.setText(complaintData.createdAt);
            holder.tv_comp_edt.setText(complaintData.modifiedAt);
            holder.tv_comp_status.setText(complaintData.status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return myaddresslist.data.size();
    }
}