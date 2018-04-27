package com.fotradis.angelb_rrmz.fotradistest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import android.util.Log;

/**
 * Created by ANGELB_RRMZ on 25/04/2018.
 */

public class SeleccionarRuta extends AppCompatActivity implements View.OnClickListener {
    // private static final String TAG = SeleccionarRuta.class.getName();
    ListView lista = null;
    ArrayList<ItemRuta> ruta = null;
    ListViewAdapterRuta adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_ruta);

        lista = (ListView) findViewById(R.id.listView_rutas);
        ruta = new ArrayList<>();

        leerRutas();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("ruta", ruta.get(i).getNombre());
                Intent abrir_lista_paradas = new Intent(SeleccionarRuta.this, SeleccionarParada.class);
                abrir_lista_paradas.putExtras(bundle);
                startActivity(abrir_lista_paradas);
            }
        });
    }

    private void leerRutas() {
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        DatabaseReference myRef = fdb.getReference("rutas");
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                if(dataSnapshot.getValue() != null) {
                    for(int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                        ruta.add(new ItemRuta(dataSnapshot.child("ruta" + (i+1)).child("nombre").getValue().toString()));
                    }
                    adapter = new ListViewAdapterRuta(ruta, SeleccionarRuta.this);
                    lista.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError error){
                Log.e("ERROR FIREBASE",error.getMessage());
            }

        });

    }

    @Override
    public void onClick(View view) {

    }
}