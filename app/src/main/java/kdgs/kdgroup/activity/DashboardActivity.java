package kdgs.kdgroup.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;

import butterknife.BindView;
import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseActivity;
import kdgs.kdgroup.config.CommonFunctions;
import kdgs.kdgroup.config.Constants;

public class DashboardActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    private PermissionRequestErrorListener errorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inticompnets();
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_dashboard;
    }

    private void inticompnets() {
        try {
            if (CommonFunctions.checkConnection(this)) {
            }
        } catch (Exception e) {
            e.printStackTrace();
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

        if (id == R.id.nav_dashboard) {
        } else if (id == R.id.nav_comp) {

        } else if (id == R.id.nav_comp_his) {

        } else if (id == R.id.nav_chng_pwd) {

        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_contact) {
            callPermissionListeners();
        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(this, SigninActivity.class));
            this.finish();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
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