package com.example.king.fragement;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity1
        extends FragmentActivity {
    private String bestProvider;
    private Button btn_loc;
    private Button btn_nav;
    private CameraPosition cameraPosition;
    private EditText edt_lat;
    private EditText edt_lng;
    private LocationManager locManager;
    private Location location;
    GoogleMap mMap;
    private MarkerOptions markerOpt;
    private RadioGroup rg_mapType;
    SupportMapFragment mapFragment ;

    private void findViews() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mMap = mapFragment.getMap();
        this.btn_loc = ((Button) findViewById(R.id.btn_loc));
        this.edt_lng = ((EditText) findViewById(R.id.edt_lng));
        this.edt_lat = ((EditText) findViewById(R.id.edt_lat));
        this.rg_mapType = ((RadioGroup) findViewById(R.id.rg_mapType));
    }

    private void initProvider() {
        this.locManager = ((LocationManager) getSystemService(LOCATION_SERVICE));
        this.locManager.getAllProviders();
        Criteria localCriteria = new Criteria();
        this.bestProvider = this.locManager.getBestProvider(localCriteria, false);
//        this.location = this.locManager.getLastKnownLocation(this.bestProvider);
        this.location = locManager.getLastKnownLocation(this.bestProvider);
    }

    private void updateToNewLocation(Location paramLocation) {
        this.markerOpt = new MarkerOptions();
        double d2 = 114.515D;
        double d1 = 38.042D;
        if (paramLocation != null) {
            d2 = paramLocation.getLongitude();
            d1 = paramLocation.getLatitude();
        }
        this.markerOpt.position(new LatLng(d1, d2));
        this.markerOpt.draggable(false);
        this.markerOpt.visible(true);
        this.markerOpt.anchor(0.5F, 0.5F);
        this.markerOpt.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        this.mMap.addMarker(this.markerOpt);
        this.cameraPosition = new CameraPosition.Builder().target(new LatLng(d1, d2)).zoom(17.0F).bearing(0.0F).tilt(30.0F).build();
        this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(this.cameraPosition));
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.maps);
        findViews();
        initProvider();
        /*
        * 会报错，因为找不到fragement
        * : Attempt to invoke virtual method 'com.google.android.gms.maps.GoogleMap
        * com.google.android.gms.maps.MapFragment.getMap()' on a null object reference
        * */
//        this.mMap = ((MapFragment) getFragmentManager().findFragmentById(2131689617)).getMap();
        /*
        * 不是layout是id，所以也会包上面的错误
        * */
//        this.mMap = ((MapFragment) getFragmentManager().findFragmentById(R.layout.map_view)).getMap();
//        this.mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapView)).getMap();
        this.btn_loc.setOnClickListener(new MapClickedListener());
        this.rg_mapType.setOnCheckedChangeListener(new ChangeMapTypeListener());

        this.locManager.requestLocationUpdates(this.bestProvider, 3000L, 8.0F, new LocationListener() {
            public void onLocationChanged(Location paramAnonymousLocation) {
            }

            public void onProviderDisabled(String paramAnonymousString) {
                MapsActivity1.this.updateToNewLocation(null);
            }

            public void onProviderEnabled(String paramAnonymousString) {
//                MapsActivity1.access$202(MapsActivity.this, MapsActivity.this.locManager.getLastKnownLocation(paramAnonymousString));
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                location = locManager.getLastKnownLocation(bestProvider);
            }

            public void onStatusChanged(String paramAnonymousString, int paramAnonymousInt, Bundle paramAnonymousBundle) {
            }
        });
    }

    private class ChangeMapTypeListener
            implements OnCheckedChangeListener
    {
        private ChangeMapTypeListener() {}

        public void onCheckedChanged(RadioGroup paramRadioGroup, int paramInt)
        {
            switch (paramInt)
            {
                default:
//                    return;
                    break;
                case R.id.rb_nomal:
//                    MapsActivity1.this.mMap.setMapType(1);
                    MapsActivity1.this.mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                    return;
                    break;
            }
            MapsActivity1.this.mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//            MapsActivity1.this.mMap.setMapType(4);
        }
    }

    private class MapClickedListener
            implements OnClickListener
    {
        private MapClickedListener() {}

        public void onClick(View v)
        {
            String lng = MapsActivity1.this.edt_lng.getText().toString().trim();
            String lat = MapsActivity1.this.edt_lat.getEditableText().toString().trim();
            if ((lng.equals("")) || (lat.equals("")))
            {
                Toast.makeText(MapsActivity1.this.getApplicationContext(), "请输入有效的经纬度信息！", Toast.LENGTH_SHORT).show();
//                MapsActivity1.access$202(MapsActivity1.this, MapsActivity1.this.locManager.getLastKnownLocation(MapsActivity.this.bestProvider));
               location = locManager.getLastKnownLocation(bestProvider);
                MapsActivity1.this.updateToNewLocation(MapsActivity1.this.location);
                return;
            }
            MapsActivity1.this.location.setLongitude(Double.parseDouble(lng));
            MapsActivity1.this.location.setLatitude(Double.parseDouble(lat));
            MapsActivity1.this.updateToNewLocation(MapsActivity1.this.location);
        }
    }
}


/* Location:              D:\迅雷下载\dex2jar-2.0\classes-dex2jar.jar!\com\example\king\fragement\MapsActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */