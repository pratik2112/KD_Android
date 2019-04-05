package kdgs.kdgroup.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONObject;

import butterknife.BindView;
import kdgs.kdgroup.R;
import kdgs.kdgroup.adapter.CCImageSlideAdapter;
import kdgs.kdgroup.base.BaseActivity;
import kdgs.kdgroup.config.CommonFunctions;
import kdgs.kdgroup.config.Constants;
import kdgs.kdgroup.config.KDGConfig;
import kdgs.kdgroup.config.WebService;
import kdgs.kdgroup.model.BannerResponse;
import kdgs.kdgroup.utills.AutoScrollViewPager;
import kdgs.kdgroup.utills.PageIndicator;

public class DashboardActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.cv_complaint)
    CardView cv_complaint;
    @BindView(R.id.cv_product)
    CardView cv_product;
    @BindView(R.id.cv_feedback)
    CardView cv_feedback;
    @BindView(R.id.cv_profile)
    CardView cv_profile;
    @BindView(R.id.cv_chng_pwd)
    CardView cv_chng_pwd;
    @BindView(R.id.cv_contact)
    CardView cv_contact;

    @BindView(R.id.info_view_pager)
    AutoScrollViewPager info_view_pager;
    @BindView(R.id.info_indicator)
    PageIndicator info_indicator;
    CCImageSlideAdapter infoBanner1;
    boolean doubleBackToExitPressedOnce = false;
    private PermissionRequestErrorListener errorListener;
    public static BannerResponse bannerResponse;
    final long DELAY_MS = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inticompnets();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_dashboard;
    }

    private void inticompnets() {
        try {
            if (CommonFunctions.getPreference(DashboardActivity.this, Constants.isLogin, false)) {
                tv_name.setText(getString(R.string.str_welcome) + " " + Html.fromHtml(CommonFunctions.getloginresponse(this).data.uFirstname + " " + CommonFunctions.getloginresponse(this).data.uLastname));
                getData();
                setbanneradapter(bannerResponse);
                cv_complaint.setOnClickListener(this);
                cv_product.setOnClickListener(this);
                cv_feedback.setOnClickListener(this);
                cv_profile.setOnClickListener(this);
                cv_chng_pwd.setOnClickListener(this);
                cv_contact.setOnClickListener(this);
            } else {
                tv_name.setText(getString(R.string.str_welcome));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getData() {
        try {
            JSONObject inputdata = new JSONObject();
            inputdata.put("b_id", "");
            WebService webService = new WebService(KDGConfig.WEBURL + KDGConfig.APIURL + KDGConfig.getBananerURL, inputdata, true, this);
            webService.getData(Request.Method.POST, new WebService.OnResult() {
                @Override
                public void OnSuccess(JSONObject result) {
                    try {
                        if (result.getBoolean(Constants.status)) {
                            Gson gson = new Gson();
                            bannerResponse = gson.fromJson(result.toString(), BannerResponse.class);
                            setbanneradapter(bannerResponse);
                        } else {
                            Toast.makeText(getApplication(), R.string.msg_server_error, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void OnFail(String error) {
                    Toast.makeText(getApplication(), error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setbanneradapter(BannerResponse bannerResponse) {
        try {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            //params.height = CommonFunctions.getMessaerHeight(CommonFunctions.dpToPx(560, this), CommonFunctions.dpToPx(280, this), this);

            if (bannerResponse.data.size() > 0) {
                infoBanner1 = new CCImageSlideAdapter(bannerResponse.data, this);
                info_view_pager.startAutoScroll();
                info_view_pager.setInterval(DELAY_MS);
                info_view_pager.setCycle(true);
                info_view_pager.setStopScrollWhenTouch(true);

                info_view_pager.setAdapter(infoBanner1);
                info_indicator.setViewPager(info_view_pager);
                info_view_pager.setLayoutParams(params);
                /*info_view_pager.setClipToPadding(false);
                info_view_pager.setPadding(0, 0, 0, 0);
                info_view_pager.setPageMargin(24);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.msg_press_back_twice), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
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
        if (id == R.id.action_logout) {
            CommonFunctions.setPreference(DashboardActivity.this, Constants.isLogin, false);
            CommonFunctions.setPreference(DashboardActivity.this, Constants.userdata, "");
            CommonFunctions.changeactivity(DashboardActivity.this, SigninActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_complaint:
                startActivity(new Intent(this, ComplaintActivity.class));
                break;
            case R.id.cv_product:
                break;
            case R.id.cv_feedback:
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
            case R.id.cv_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.cv_chng_pwd:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case R.id.cv_contact:
                if (CommonFunctions.getPreference(DashboardActivity.this, Constants.callPermission, false)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:9999999999"));
                    startActivity(intent);
                } else {
                    callPermissionListeners();
                }
                break;
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