package com.fotradis.angelb_rrmz.fotradistest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ANGELB_RRMZ on 26/04/2018.
 */

public class ListViewAdapterParada extends BaseAdapter {
    private ArrayList<ItemParada> item;
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<ItemParada> arraylist;

    public ListViewAdapterParada(ArrayList<ItemParada> item, Context context) {
        this.item = item;
        this.context = context;
        arraylist = new ArrayList<>();
        arraylist.addAll(item);
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int i) {
        return item.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vista = layoutInflater.inflate(R.layout.layout_item_parada, viewGroup, false);
        TextView nombre = (TextView) vista.findViewById(R.id.nombre_parada);
        TextView referencias = (TextView) vista.findViewById(R.id.referencias_parada);
        nombre.setText(item.get(i).getNombre());
        referencias.setText(item.get(i).getReferencias());
        return vista;
    }
}
