package com.fotradis.angelb_rrmz.fotradistest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.view.View;
import android.widget.Button;
import android.os.SystemClock;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by ANGELB_RRMZ on 26/04/2018.
 */

public class MapParada extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    Button salir;
    float lat;
    float lng;
    private ArrayList<String> latitudes;
    private ArrayList<String> longitudes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_parada);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        salir = (Button) findViewById(R.id.button_salirMapa);

        Bundle bundle1 = getIntent().getExtras();

        //lat = Float.parseFloat(bundle1.getString("latitud"));
        //lng = Float.parseFloat(bundle1.getString("longitud"));
        latitudes = bundle1.getStringArrayList("latitudes");
        longitudes = bundle1.getStringArrayList("longitudes");

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inicio = new Intent(MapParada.this, Inicio.class);
                startActivity(inicio);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        for(int i = 0; i < latitudes.size(); i++) {
            LatLng marker_fotradis = new LatLng(Float.parseFloat(latitudes.get(i).toString()), Float.parseFloat(longitudes.get(i).toString()));
            mMap.addMarker(new MarkerOptions().position(marker_fotradis).title("DIF Fotradis"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker_fotradis, 15));
        }
    }
}
