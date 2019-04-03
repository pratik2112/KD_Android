package kdgs.kdgroup.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kdgs.kdgroup.R;
import kdgs.kdgroup.model.AddressResponse;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {

    private Activity mContext;
    AddressResponse myaddresslist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public AddressAdapter(Activity mContext, AddressResponse myaddresslist) {
        this.mContext = mContext;
        this.myaddresslist = myaddresslist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_address, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}