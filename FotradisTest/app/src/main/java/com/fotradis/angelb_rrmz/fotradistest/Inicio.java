package com.fotradis.angelb_rrmz.fotradistest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by ANGELB_RRMZ on 27/04/2018.
 */

public class Inicio extends AppCompatActivity implements View.OnClickListener{
    Button taxi, urban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        urban = (Button) findViewById(R.id.button_urban);
        taxi = (Button) findViewById(R.id.button_taxi);

        urban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View views) {
                Intent abrir_ventana = new Intent(Inicio.this, SeleccionarRuta.class);
                startActivity(abrir_ventana);
            }
        });

        taxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("servicio", "taxi");
                Intent abrir_ventana = new Intent(Inicio.this, IngresarClave.class);
                abrir_ventana.putExtras(bundle);
                startActivity(abrir_ventana);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
