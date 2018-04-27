package com.fotradis.angelb_rrmz.fotradistest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ANGELB_RRMZ on 25/04/2018.
 */

public class SeleccionarParada extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SeleccionarRuta.class.getName();
    ListView lista = null;
    ArrayList<ItemParada> parada = null;
    ListViewAdapterParada adapter = null;
    String ruta = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_parada);

        lista = (ListView) findViewById(R.id.listView_paradas);
        parada = new ArrayList<>();

        Bundle bundle1 = getIntent().getExtras();
        ruta = bundle1.getString("ruta");

        leerParadasDeRuta();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("latitud", parada.get(i).getLatitud());
                bundle.putString("longitud", parada.get(i).getLongitud());
                bundle.putString("parada", parada.get(i).getNombre());
                bundle.putString("ruta", ruta);
                bundle.putString("servicio", "urban");
                Intent abrir_lista_paradas = new Intent(SeleccionarParada.this, IngresarClave.class);
                abrir_lista_paradas.putExtras(bundle);
                startActivity(abrir_lista_paradas);
            }
        });
    }

    private void leerParadasDeRuta() {
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        DatabaseReference myRef = fdb.getReference("paradas");
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                if(dataSnapshot.getValue() != null) {
                    for(int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                        for (DataSnapshot postSnapshot: dataSnapshot.child("parada" + (i+1)).child("rutas").getChildren()) {
                            if(postSnapshot.getKey().toString().equals(ruta)) {
                                parada.add(new ItemParada(
                                            dataSnapshot.child("parada" + (i + 1)).child("nombre").getValue().toString(),
                                            dataSnapshot.child("parada" + (i + 1)).child("latitud").getValue().toString(),
                                            dataSnapshot.child("parada" + (i + 1)).child("longitud").getValue().toString(),
                                            dataSnapshot.child("parada" + (i + 1)).child("referencias").getValue().toString()
                                        )
                                );
                            }
                        }
                    }
                    adapter = new ListViewAdapterParada(parada, SeleccionarParada.this);
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
