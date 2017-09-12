package com.rhcloud.papers.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.rhcloud.papers.model.entity.Destino;

import java.util.List;

/**
 * Created by rodolfosantana on 11/09/17.
 */

public class adpDestinos extends ArrayAdapter<Destino> {
    private Context context;
    private List<Destino> destinoList;


    public adpDestinos(Context context, int textResource,List<Destino> destinos) {
        super(context, textResource, destinos);
        this.context = context;
        this.destinoList = destinos;
    }

    public int getCount(){
        return destinoList.size();
    }

    public Destino getItem(int position){
        return destinoList.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

}
