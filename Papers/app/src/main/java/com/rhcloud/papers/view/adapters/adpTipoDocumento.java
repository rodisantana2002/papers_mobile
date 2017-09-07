package com.rhcloud.papers.view.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.rhcloud.papers.R;
import com.rhcloud.papers.model.entity.TipoDocumento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodolfosantana on 07/09/17.
 */

public class adpTipoDocumento extends ArrayAdapter<TipoDocumento> {

    public adpTipoDocumento(@NonNull Context context, List<TipoDocumento> lstDocumento) {
        super(context, R.layout.support_simple_spinner_dropdown_item, lstDocumento);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final TipoDocumento tipoDocumento = getItem(position);
        convertView.setTag(tipoDocumento);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }
}
