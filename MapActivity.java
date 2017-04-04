package com.example.happy.myapplication;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 20-03-2017.
 */

// mapactvity replace here

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
   List<Marker> originMarkers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * @param googleMap onMapReady method Invoke the current lat and lang in onMap Ready
     */
    @Override
    public void onMapReady(GoogleMap googleMap) throws Resources.NotFoundException {
        mMap = googleMap;
        //   mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_aubergine));
        LatLng hcmus = new LatLng(11.0764, 77.0030);
        ArrayList<LatLng> locations = new ArrayList();
        locations.add(new LatLng(11.081218,76.941558));
        locations.add(new LatLng(11.076375,77.002984));
        locations.add(new LatLng(11.151008,76.935498));
        locations.add(new LatLng(11.020983,76.966334));
        locations.add(new LatLng(11.289087,76.940969));

        for(LatLng location : locations){
            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .title("Alumni")
                    .position( location)));
        }


        CameraPosition cameraPosition =
                new CameraPosition.Builder()
                        .target(hcmus)
                        .bearing(45)
                        .tilt(90)
                        .zoom(10)
                        .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(
                cameraUpdate,
                300,
                new GoogleMap.CancelableCallback() {

                    @Override
                    public void onFinish() {
                    }

                    @Override
                    public void onCancel() {
                    }
                }
        );

        //    mMap.moveCamera(cameraUpdate);
        //Vector originMarkers;
//

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

}