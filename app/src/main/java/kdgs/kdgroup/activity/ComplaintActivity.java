package kdgs.kdgroup.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseActivity;
import kdgs.kdgroup.fragment.ComplaintFragment;
import kdgs.kdgroup.fragment.RecentComplaintFragment;
import kdgs.kdgroup.fragment.StatusComplaintFragment;
import kdgs.kdgroup.utills.CustomViewPager;

public class ComplaintActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_home)
    ImageView iv_home;
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.view_pager)
    CustomViewPager view_pager;
    @BindView(R.id.iv_edit)
    ImageView iv_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inticompnets();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_complaint;
    }

    private void inticompnets() {
        try {
            getSupportActionBar().hide();
            tv_title.setText(getString(R.string.str_m_comp));
            iv_home.setVisibility(View.VISIBLE);
            iv_edit.setVisibility(View.GONE);

            setupViewPager(view_pager);
            tab_layout.setupWithViewPager(view_pager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        gotoback();
    }

    @OnClick({R.id.iv_home})
    public void gotoback() {
        try {
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupViewPager(CustomViewPager view_pager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ComplaintFragment(), getString(R.string.str_m_comp));
        adapter.addFragment(new RecentComplaintFragment(), getString(R.string.str_comp_recnt));
        adapter.addFragment(new StatusComplaintFragment(), getString(R.string.str_comp_stat));
        view_pager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}