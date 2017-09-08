package com.rhcloud.papers.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.rhcloud.papers.model.entity.Pessoa;

import java.util.List;

/**
 * Created by rodolfosantana on 08/09/17.
 */

public class adpPessoas extends ArrayAdapter<Pessoa>{
    private Context context;
    private List<Pessoa> pessoaList;

    public adpPessoas(Context context, int textResource,List<Pessoa> pessoas) {
        super(context, textResource, pessoas);
        this.context = context;
        this.pessoaList = pessoas;
    }

    public int getCount(){
        return pessoaList.size();
    }

    public Pessoa getItem(int position){
        return pessoaList.get(position);
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

