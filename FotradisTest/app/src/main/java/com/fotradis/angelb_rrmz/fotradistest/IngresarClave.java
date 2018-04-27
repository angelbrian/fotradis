package com.fotradis.angelb_rrmz.fotradistest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ANGELB_RRMZ on 25/04/2018.
 */

public class IngresarClave extends AppCompatActivity {
    Button ok;
    Button cancelar;
    EditText clave;
    private String servicio = "";
    private String ruta = "";
    private String parada = "";
    private String latitud = "";
    private String longitud = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_clave);

        ok = (Button) findViewById(R.id.button_ok_usuario);
        cancelar = (Button) findViewById(R.id.button_cancelar_usuario);
        clave = (EditText) findViewById(R.id.editText_claveUsuario);

        Bundle bundle = getIntent().getExtras();
        servicio = bundle.getString("servicio");

        if(servicio.equals("urban")) {
            ruta = bundle.getString("ruta");
            parada = bundle.getString("parada");
            latitud = bundle.getString("latitud");
            longitud = bundle.getString("longitud");
        }

        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View views){
             if(!clave.getText().toString().equals("")) {
                validarClave();
             }
            }
        });
    }

    public void validarClave() {
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        DatabaseReference myRef = fdb.getReference("usuarios");
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                if(dataSnapshot.getValue() != null) {
                    for(int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                        if(dataSnapshot.child("usuario" + (i+1)).child("clave").getValue().equals(clave.getText().toString())) {
                            if(dataSnapshot.child("usuario" + (i+1)).child("servicio").getValue().equals(servicio)) {
                                Bundle bundle = new Bundle();

                                int dias = diasFaltantes(dataSnapshot.child("usuario" + (i+1)).child("fecha_activacion").getValue().toString());
                                if(dias > 0)
                                    bundle.putString("caduca", "Faltan: " + dias + " días");
                                else
                                    bundle.putString("caduca", "Tu servicio ha caducada");

                                bundle.putString("usuario", dataSnapshot.child("usuario" + (i+1)).child("nombre").getValue().toString());
                                bundle.putString("servicio", servicio);
                                bundle.putString("parada", parada);
                                bundle.putString("ruta", ruta);
                                bundle.putString("latitud", latitud);
                                bundle.putString("longitud", longitud);
                                Intent abrir_ventana= new Intent(IngresarClave.this, MostrarUsuario.class);
                                abrir_ventana.putExtras(bundle);
                                startActivity(abrir_ventana);
                                return;
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error){
                Log.e("ERROR FIREBASE",error.getMessage());
            }

        });
    }

    private int diasFaltantes(String fecha_usuario) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha_hoy = new Date();
        Date fecha_activacion = null;
        try {
            fecha_activacion = dateFormat.parse(fecha_usuario);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat.format(fecha_hoy);
        return (int) ((fecha_hoy.getTime() - fecha_activacion.getTime())/86400000);
    }
}
