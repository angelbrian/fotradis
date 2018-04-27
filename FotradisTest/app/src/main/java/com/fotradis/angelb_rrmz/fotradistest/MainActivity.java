package com.fotradis.angelb_rrmz.fotradistest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String latitud = "";
    String longitud = "";
    String direccion = "";
    Chronometer cronometro;
    Button on_of;
    private ArrayList<String> latitudes;
    private ArrayList<String> longitudes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorrido_taxi);

        on_of = (Button) findViewById(R.id.button_onOf);
        cronometro = (Chronometer) findViewById(R.id.chronometer);
        latitudes = new ArrayList<>();
        longitudes = new ArrayList<>();

        on_of.setOnClickListener(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }
    }

    public void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    public void setLocation(Location loc) {
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address direcc = list.get(0);
                            /*direcc.getAddressLine(0)
                            direcc.getLocality()
                            direcc.getLocale()
                            direcc.getCountryName()
                            direcc.getFeatureName()*/
                    direccion = direcc.getAddressLine(0) + ", "
                            + direcc.getLocality() + ", "
                            + direcc.getSubLocality() + ", "
                            + direcc.getLocale() + ", "
                            + direcc.getCountryName() + ", "
                            + direcc.getFeatureName();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(on_of.getText().toString().toLowerCase().equals("iniciar")) {
            on_of.setText("terminar");
            cronometro.start();
        } else if(on_of.getText().toString().toLowerCase().equals("terminar")) {
            Bundle bundle_mapa = new Bundle();
            bundle_mapa.putStringArrayList("latitudes", latitudes);
            bundle_mapa.putStringArrayList("longitudes", longitudes);
            Intent abrir_mapa = new Intent(MainActivity.this, MapParada.class);
            abrir_mapa.putExtras(bundle_mapa);
            startActivity(abrir_mapa);
            cronometro.stop();
        }
    }

    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        com.fotradis.angelb_rrmz.fotradistest.MainActivity mainActivity;

        public com.fotradis.angelb_rrmz.fotradistest.MainActivity getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(com.fotradis.angelb_rrmz.fotradistest.MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();

            String texto = "Mi ubicacion actual es: " + "\n Latitud = "
                    + loc.getLatitude() + "\n Longitud = " + loc.getLongitude();
            latitud = "" + loc.getLatitude();
            longitud = "" + loc.getLongitude();
            if(on_of.getText().toString().toLowerCase().equals("terminar")) {
                //recorrido.setText(latitud + " - " + longitud);
                latitudes.add(latitud);
                longitudes.add(longitud);
            }
            this.mainActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }
}

