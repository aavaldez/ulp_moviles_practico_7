package com.a2valdez.ulp_moviles_practico_7.ui.ubicacion;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class UbicacionViewModel extends AndroidViewModel {
    private Context context;
    private FusedLocationProviderClient fused;
    private MutableLiveData<Location> mLocation;
    private MutableLiveData<MapaActual> mMapa;

    private static final LatLng SANLUIS = new LatLng(-33.280576, -66.332482);

    public UbicacionViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        fused = LocationServices.getFusedLocationProviderClient(context);
    }

    public LiveData<Location> getMLocation() {
        if (mLocation == null) {
            mLocation = new MutableLiveData<>();
        }
        return mLocation;
    }

    public void obtenerUltimaUbicacion() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> tarea = fused.getLastLocation();
        tarea.addOnSuccessListener(getApplication().getMainExecutor(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                mLocation.setValue(location);
            }
        });
    }

    public void lecturaPermanente() {
        LocationRequest request = LocationRequest.create();
        request.setInterval(5000);
        request.setFastestInterval(5000);
        request.setPriority(Priority.PRIORITY_HIGH_ACCURACY);

        LocationCallback callback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    mLocation.setValue(location);
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fused.requestLocationUpdates(request, callback, null);
    }

    public LiveData<MapaActual> getMMapa(){
        if( mMapa == null ){
            mMapa = new MutableLiveData<>();
        }
        return mMapa;
    }

    public void obtenerMapa(){
        MapaActual ma = new MapaActual();
        mMapa.setValue(ma);
    }

    public class MapaActual implements OnMapReadyCallback{

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Task<Location> tarea = fused.getLastLocation();
            tarea.addOnSuccessListener(getApplication().getMainExecutor(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    LatLng ltlg = new LatLng(location.getLatitude(), location.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(ltlg).title("Ahora estoy aca"));
                    CameraPosition camPos = new CameraPosition.Builder()
                            .target(ltlg)
                            .zoom(19)
                            .bearing(45)
                            .tilt(70)
                            .build();
                    CameraUpdate update = CameraUpdateFactory.newCameraPosition(camPos);
                    googleMap.animateCamera(update);
                }
            });
        }
    }
}