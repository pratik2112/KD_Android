package kdgs.kdgroup.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kdgs.kdgroup.R;
import kdgs.kdgroup.model.ComplaintResponse;

import static kdgs.kdgroup.config.CommonFunctions.parseDateToddMMyyyy;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.MyViewHolder> {

    private Activity mContext;
    ComplaintResponse complaintlist;
    List<ComplaintResponse.Data> complaintDataList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_sno, tv_comp_no, tv_comp_name, tv_comp, tv_comp_sdt, tv_comp_edt, tv_comp_status;
        public CardView cv_comp_detail;

        public MyViewHolder(View view) {
            super(view);
            tv_sno = (TextView) view.findViewById(R.id.tv_sno);
            tv_comp_no = (TextView) view.findViewById(R.id.tv_comp_no);
            tv_comp_name = (TextView) view.findViewById(R.id.tv_comp_name);
            tv_comp = (TextView) view.findViewById(R.id.tv_comp);
            tv_comp_sdt = (TextView) view.findViewById(R.id.tv_comp_sdt);
            tv_comp_edt = (TextView) view.findViewById(R.id.tv_comp_edt);
            tv_comp_status = (TextView) view.findViewById(R.id.tv_comp_status);
            cv_comp_detail = view.findViewById(R.id.cv_comp_detail);
        }
    }

    public ComplaintAdapter(Activity mContext, ComplaintResponse complaintlist) {
        this.mContext = mContext;
        this.complaintlist = complaintlist;
        complaintDataList = new ArrayList<>();
        complaintDataList.addAll(complaintlist.data);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_single_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            final ComplaintResponse.Data complaintData = complaintDataList.get(position);
            holder.tv_sno.setText(position + 1 + ". ");
            holder.tv_comp_no.setText(complaintData.ucNo);
            holder.tv_comp_name.setText(complaintData.ucTitle);
            holder.tv_comp.setText(complaintData.ucDesc);
            holder.tv_comp_sdt.setText(parseDateToddMMyyyy("yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm", complaintData.createdAt));
            holder.tv_comp_edt.setText(parseDateToddMMyyyy("yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm", complaintData.modifiedAt));
            holder.tv_comp_status.setText(complaintData.status);
            holder.cv_comp_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return complaintlist.data.size();
    }
}