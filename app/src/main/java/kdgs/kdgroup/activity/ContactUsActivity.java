package kdgs.kdgroup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import kdgs.kdgroup.R;
import kdgs.kdgroup.base.BaseActivity;

public class ContactUsActivity extends BaseActivity implements OnMapReadyCallback {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_home)
    ImageView iv_home;
    @BindView(R.id.iv_edit)
    ImageView iv_edit;
    @BindView(R.id.mapView)
    MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inticompnets();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_contact_us;
    }

    private void inticompnets() {
        try {
            getSupportActionBar().hide();
            tv_title.setText(getString(R.string.str_m_contact));
            iv_home.setVisibility(View.VISIBLE);
            iv_edit.setVisibility(View.GONE);

            mMapView.onResume();
            mMapView.getMapAsync(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;
        double latitude = 21.196102;
        double longitude = 72.815766;

        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 17.0f));
        //mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(getString(R.string.str_title)).snippet(getString(R.string.contactaddress)));
    }
}