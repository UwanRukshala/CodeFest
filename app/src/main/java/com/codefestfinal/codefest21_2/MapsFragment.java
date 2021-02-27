package com.codefestfinal.codefest21_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapsFragment extends Fragment {

    GoogleMap currentGoogleMap;
    final int REQUEST_LOCATION_PERMISSION = 28;
    LatLng customerLoaction;

    public LatLng getPosition() {
        return position;
    }

    LatLng position;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {

            currentGoogleMap=googleMap;
            setLocation();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private void setLocation() {
        Context context = MapsFragment.super.getContext();
        String FineLocation = Manifest.permission.ACCESS_FINE_LOCATION;
        String CoarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION;

        boolean isFineLocationPermissionGranted = (ActivityCompat.checkSelfPermission(context, FineLocation) == PackageManager.PERMISSION_GRANTED);
        boolean isCoarseLocationPermissionGranted = (ActivityCompat.checkSelfPermission(context, CoarseLocation) == PackageManager.PERMISSION_GRANTED);

        if (isFineLocationPermissionGranted && isCoarseLocationPermissionGranted) {

            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
            Task<Location> lastLocation = fusedLocationProviderClient.getLastLocation();
            lastLocation.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null){

                        customerLoaction=new LatLng(location.getLatitude(),location.getLongitude());
                        BitmapDescriptor bitcurrent =  getBitmapDesc(getContext(),R.drawable.ic_baseline_location_on_24);
                        MarkerOptions shopMarker = new MarkerOptions().icon(bitcurrent).draggable(true).position(customerLoaction);

                        currentGoogleMap.addMarker(shopMarker);
                        currentGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(customerLoaction));
                        currentGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(14));

                        currentGoogleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                            @Override
                            public void onMarkerDragStart(Marker marker) {

                                    Log.d("ABC","START");
                            }

                            @Override
                            public void onMarkerDrag(Marker marker) {
                                Log.i("ABC","dragging");
                            }

                            @Override
                            public void onMarkerDragEnd(Marker marker) {
                                position = marker.getPosition();
                                setData();
                            }
                        });

                    }else{

                        Toast.makeText(MapsFragment.super.getContext(),"location Not Found>>",Toast.LENGTH_SHORT).show();

                    }
                }
            });

        } else {
            requestPermissions(new String[]{FineLocation, CoarseLocation}, REQUEST_LOCATION_PERMISSION);
        }
    }

    private BitmapDescriptor getBitmapDesc(Context context  ,int ic_baseline_add_location_24) {
        Drawable LAYER_1 = ContextCompat.getDrawable(context,ic_baseline_add_location_24);
        LAYER_1.setBounds(0, 0, LAYER_1.getIntrinsicWidth(), LAYER_1.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(LAYER_1.getIntrinsicWidth(), LAYER_1.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        LAYER_1.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);


    }

    void setData(){

        Fragment home = getActivity().getSupportFragmentManager().findFragmentByTag("Home");
        ((Home)home).setData(position);
    }
}