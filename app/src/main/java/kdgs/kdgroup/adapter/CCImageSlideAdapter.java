package kdgs.kdgroup.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kdgs.kdgroup.R;
import kdgs.kdgroup.model.BannerResponse;

/**
 * Created by Navneet Boghani on 6/7/16.
 */
public class CCImageSlideAdapter extends PagerAdapter {

    List<BannerResponse.Data> banner_info2 = new ArrayList<>();
    Activity activity;
    int width, height;

    public CCImageSlideAdapter(List<BannerResponse.Data> banner_info2, Activity activity) {
        this.activity = activity;
        this.banner_info2 = banner_info2;
        /*DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;*/
    }

    @Override
    public int getCount() {
        return banner_info2.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.row_caseimage_slider_item, container,false);
        final ImageView case_iv = (ImageView) view.findViewById(R.id.case_iv);
        String mUrl = banner_info2.get(position).bName;
        Glide.with(activity).load(mUrl).asBitmap().placeholder(R.drawable.ic_trans).into(case_iv);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View)object);
    }
}
