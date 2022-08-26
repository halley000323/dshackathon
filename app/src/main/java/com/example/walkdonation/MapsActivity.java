package com.example.walkdonation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    //위치 정보 얻는 객체
    private FusedLocationProviderClient mFusedLocationClient;

    //권한 체크 요청 코드 정의
    public static  final int REQUEST_CODE_PERMISSIONS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ImageButton back_btn = (ImageButton) findViewById(R.id. back);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
                startActivity(intent);
            }
        });

        //GoogleAPIClient 인스턴스 생성
        if(mGoogleApiClient == null){
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        ToggleButton b_mapMode = findViewById(R.id.b_mapmode);
        b_mapMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                else
                    mMap.setMapType((GoogleMap.MAP_TYPE_NORMAL));
            }
        });

        ToggleButton b_showCurPos = findViewById(R.id.b_showCurPos);
        b_showCurPos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //권한 체크
                if(ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                                !=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MapsActivity.this,
                            new String[]{ Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION },
                            REQUEST_CODE_PERMISSIONS);
                    return;
                }
                mMap.setMyLocationEnabled(isChecked);
            }
        });
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //마커 추가
        LatLng ds = new LatLng(37.65132619696229, 127.01612821175677);
        //        mMap.addMarker(new MarkerOptions().position(ds).title("Duksung University"));
        //카메라 이동
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ds));

//        Resources r = context.getResources();
//        BitmapDrawable bd = (BitmapDrawable) ContextCompat.getDrawable(r, R.drawable.baseline_delete_outline_black_24dp);
//        Bitmap b = bd.getBitmap();
        Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.bx_trash);
        Bitmap bitMapImage = Bitmap.createScaledBitmap(b,75,75,false);

        LatLng trash_1 = new LatLng(37.652420, 127.016478);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bitMapImage)).position(trash_1));

        LatLng trash_2 = new LatLng(37.653337, 127.015566);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bitMapImage)).position(trash_2));

        LatLng trash_3 = new LatLng(37.653248, 127.015410);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bitMapImage)).position(trash_3));

        LatLng trash_4 = new LatLng(37.652386, 127.016945);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bitMapImage)).position(trash_4));

        LatLng trash_5 = new LatLng(37.652156, 127.016950);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bitMapImage)).position(trash_5));

        LatLng trash_6 = new LatLng(37.651587, 127.015975);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bitMapImage)).position(trash_6));

        LatLng trash_7 = new LatLng(37.651621, 127.016355);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bitMapImage)).position(trash_7));

        LatLng trash_8 = new LatLng(37.651611, 127.016929);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bitMapImage)).position(trash_8));

        LatLng trash_9 = new LatLng(37.653938, 127.015822);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bitMapImage)).position(trash_9));

//        LatLng trash_1 = new LatLng(37.651428, 127.015705);
//        mMap.addMarker(new MarkerOptions().position(trash_1).title("trash"));

        //카메라 줌인(2.0f ~ 21.0f)
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));

//        //마커 텍스트 클릭 이벤트트
//        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener(){
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:02-123-5678"));
//
//                startActivity(intent);
//            }
//        });

        UiSettings ui = mMap.getUiSettings();

        ui.setZoomControlsEnabled(true); //확대 축소

        //false일 때 막아짐 (기본적으로 true, 설정되어있음)
        //ui.setCompassEnabled(true); //나침판 (방향 움직여야 나옴) default
        //ui.setMyLocationButtonEnabled(true); //default (현재위치보이기 활성화했을 때 맨위 오른쪽 표시)
        //ui.setScrollGesturesEnabled(true);

//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                MarkerOptions maker = new MarkerOptions()
//                        .position(latLng);
//                mMap.addMarker(maker);
//
//                CircleOptions circle = new CircleOptions()
//                        .center(latLng)
//                        .radius(100)
//                        .strokeColor(Color.BLUE)
//                        .strokeWidth(1.0f)
//                        .fillColor(Color.parseColor("#220000FF"));
//                mMap.addCircle(circle);
//            }
//        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void mCurrentLocation(View v){
        //권한 체크
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    REQUEST_CODE_PERMISSIONS);
            return;
        }

        mFusedLocationClient.getLastLocation().addOnSuccessListener(this,
                new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null){
                            //현재 위치
                            LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions()
                                    .position(myLocation)
                                    .title("현재 위치"));

                            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));

                            //카메라 줌
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_PERMISSIONS:
                if(ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this,
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한 체크 거부 됨", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
}