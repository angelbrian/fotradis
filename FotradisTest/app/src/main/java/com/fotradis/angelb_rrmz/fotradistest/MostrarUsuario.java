package com.fotradis.angelb_rrmz.fotradistest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;

/**
 * Created by ANGELB_RRMZ on 26/04/2018.
 */

public class MostrarUsuario extends AppCompatActivity implements View.OnClickListener{
    TextView usuario;
    TextView servicio;
    TextView caduca;
    TextView subioEn;
    TextView titulo_subioEn;
    Button iniciar_servicio;
    private String ser;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_usuario);
        usuario = (TextView) findViewById(R.id.textView_nombreUsuario);
        servicio = (TextView) findViewById(R.id.textView_servicioOfrecido);
        caduca = (TextView) findViewById(R.id.textView_caduca);
        subioEn = (TextView) findViewById(R.id.textView_subioEn);
        titulo_subioEn = (TextView) findViewById(R.id.textViewmu4);
        iniciar_servicio = (Button) findViewById(R.id.button_iniciarServicio);

        bundle = getIntent().getExtras();

        ser = bundle.getString("servicio");
        usuario.setText(bundle.getString("usuario"));
        servicio.setText(ser);
        caduca.setText(bundle.getString("caduca"));

        if(ser.equals("urban")) {
            subioEn.setText("Ruta " + bundle.getString("ruta") + "\nParada " + bundle.getString("parada"));
        } else {
            subioEn.setText("");
            titulo_subioEn.setText("");
        }

        iniciar_servicio.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(ser.equals("urban")) {
            /*Bundle bundle_map = new Bundle();
            bundle_map.putString("latitud", bundle.getString("latitud"));
            bundle_map.putString("longitud", bundle.getString("longitud"));*/
            Intent abrir_mapa = new Intent(MostrarUsuario.this, Inicio.class);
            //abrir_mapa.putExtras(bundle_map);
            startActivity(abrir_mapa);
        } else if(ser.equals("taxi")) {
            Intent abrir_ventana = new Intent(MostrarUsuario.this, MainActivity.class);
            startActivity(abrir_ventana);
        }
    }
}
