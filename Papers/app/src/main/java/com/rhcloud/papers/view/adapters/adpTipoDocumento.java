package com.rhcloud.papers.view.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.model.entity.TipoDocumento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodolfosantana on 07/09/17.
 */

public class adpTipoDocumento extends ArrayAdapter<TipoDocumento> {

    private Context context;
    private List<TipoDocumento> tipoDocumentoList;

    public adpTipoDocumento(Context context, int textResource,List<TipoDocumento> lstTipoDocumento) {
        super(context, textResource, lstTipoDocumento);
        this.context = context;
        this.tipoDocumentoList = lstTipoDocumento;
    }

    public int getCount(){
        return tipoDocumentoList.size();
    }

    public TipoDocumento getItem(int position){
        return tipoDocumentoList.get(position);
    }

    public long getItemId(int id){
        return id;
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
