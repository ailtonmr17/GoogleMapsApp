package com.example.googlemapsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import
        com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import
        com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import java.util.Arrays;
public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback {
    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private int currentMapType = GoogleMap.MAP_TYPE_NORMAL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// Obtener el SupportMapFragment y notificar cuando el mapa esté listo para ser usado
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button btnChangeMapType = findViewById(R.id.btnChangeMapType);
        btnChangeMapType.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                changeMapType();
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
// Añade un marcador en Sydney y mueve la cámara
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new
                MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
// Habilitar el botón de "Mi Ubicación" y comprobar permisos
        enableMyLocation();
    }
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    new
                            String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull
    String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void changeMapType() {
        if (mMap != null) {
            currentMapType = (currentMapType + 1) % 4;
            switch (currentMapType) {
                case 0:
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    Toast.makeText(this, "Mapa Normal",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    Toast.makeText(this, "Mapa Satélite",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    Toast.makeText(this, "Mapa Terreno",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    Toast.makeText(this, "Mapa Híbrido",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}