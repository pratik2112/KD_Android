package kdgs.kdgroup.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;

import butterknife.BindView;
import butterknife.Optional;
import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseActivity;
import kdgs.kdgroup.config.CommonFunctions;
import kdgs.kdgroup.config.Constants;
import kdgs.kdgroup.fragment.DashboardFragment;

public class DashboardActivity extends BaseActivity implements OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    private PermissionRequestErrorListener errorListener;

    public TextView tv_name, tv_uname, tv_phone, tv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inticompnets();
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        addFragment(new DashboardFragment());
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_dashboard;
    }

    private void inticompnets() {
        try {
            tv_name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_name);
            tv_uname = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_uname);
            tv_phone = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_phone);
            tv_email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_email);

            if (CommonFunctions.getPreference(DashboardActivity.this, Constants.isLogin, false)) {
                tv_name.setText(Html.fromHtml(CommonFunctions.getloginresponse(this).data.uFirstname + " " + CommonFunctions.getloginresponse(this).data.uLastname));
                tv_uname.setText(Html.fromHtml(CommonFunctions.getloginresponse(this).data.uName));
                tv_phone.setText(Html.fromHtml(CommonFunctions.getloginresponse(this).data.uMobile));
                tv_email.setText(Html.fromHtml(CommonFunctions.getloginresponse(this).data.uEmail));
            } else {
                tv_name.setText("");
                tv_uname.setText("");
                tv_phone.setText("");
                tv_email.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        Bundle b = intent.getExtras();
        if (b.getBoolean(Constants.userdata)) {
            if (CommonFunctions.getPreference(DashboardActivity.this, Constants.isLogin, false)) {
                tv_name.setText(Html.fromHtml(CommonFunctions.getloginresponse(this).data.uFirstname + " " + CommonFunctions.getloginresponse(this).data.uLastname));
                tv_uname.setText(Html.fromHtml(CommonFunctions.getloginresponse(this).data.uName));
                tv_phone.setText(Html.fromHtml(CommonFunctions.getloginresponse(this).data.uMobile));
                tv_email.setText(Html.fromHtml(CommonFunctions.getloginresponse(this).data.uEmail));
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            shareApp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        switch (id) {
            case R.id.nav_dashboard:
                if (toolbar != null)
                    toolbar.setTitle(getString(R.string.str_m_dashboard));

                DashboardFragment dashboardFragment = new DashboardFragment();
                transaction.setCustomAnimations(
                        R.anim.push_left_in,
                        R.anim.push_left_out,
                        R.anim.push_left_in,
                        R.anim.push_left_out);
                transaction.replace(R.id.frame_container, dashboardFragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.nav_comp:
                startActivity(new Intent(this, ComplaintActivity.class));
                break;
            case R.id.nav_feedback:
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
            case R.id.nav_contact:
                if (CommonFunctions.getPreference(DashboardActivity.this, Constants.callPermission, false)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:9999999999"));
                    startActivity(intent);
                } else {
                    callPermissionListeners();
                }
                break;
            case R.id.nav_logout:
                CommonFunctions.setPreference(DashboardActivity.this, Constants.isLogin, false);
                CommonFunctions.setPreference(DashboardActivity.this, Constants.userdata, "");
                startActivity(new Intent(this, SigninActivity.class));
                this.finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addFragment(Fragment fragment) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, fragment, null);
        ft.commitAllowingStateLoss();
    }

    private void shareApp() {
        try {
            String shareMsg = getString(R.string.subject) + " \n ";
            String play_store = getString(R.string.google_url);
            String appName = getPackageName();

            Intent linkIntent = new Intent(Intent.ACTION_SEND);
            linkIntent.setType("text/plain");
            linkIntent.putExtra(Intent.EXTRA_TEXT, shareMsg + play_store + appName);
            startActivity(Intent.createChooser(linkIntent, "Share Via"));
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void callPermissionListeners() {
        try {
            errorListener = new PermissionRequestErrorListener() {
                @Override
                public void onError(DexterError error) {
                }
            };

            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.READ_PHONE_STATE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            CommonFunctions.setPreference(DashboardActivity.this, Constants.callPermission, true);
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:9999999999"));
                            startActivity(intent);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            CommonFunctions.setPreference(DashboardActivity.this, Constants.callPermission, false);
                            storagePermissionDialog();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void storagePermissionDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage(getString(R.string.storage_permission1));
        dialogBuilder.setPositiveButton(getString(R.string.dialog_ok_btn), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                callPermissionListeners();
                dialog.dismiss();
            }
        });
        dialogBuilder.setNegativeButton(getString(R.string.dialog_cancel_btn), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogBuilder.setCancelable(false);
        dialogBuilder.show();
    }
}