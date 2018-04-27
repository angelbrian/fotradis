package com.fotradis.angelb_rrmz.fotradistest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ANGELB_RRMZ on 25/04/2018.
 */

public class ListViewAdapterRuta extends BaseAdapter{
    private ArrayList<ItemRuta> item;
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<ItemRuta> arraylist;

    public ListViewAdapterRuta(ArrayList<ItemRuta> item, Context context) {
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
        View vista = layoutInflater.inflate(R.layout.layout_item_ruta, viewGroup, false);
        TextView nombre = (TextView) vista.findViewById(R.id.nombre_ruta);
        nombre.setText(item.get(i).getNombre());
        return vista;
    }
}