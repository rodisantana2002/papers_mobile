package com.rhcloud.papers.view.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.model.entity.Documento;

import java.util.List;

/**
 * Created by rodolfosantana on 30/08/17.
 */

public class adpDocumentos extends RecyclerView.Adapter<adpDocumentos.RepoHolder>  {
    private List<Documento> lstDocumentos;
    private Context context;
    private itfOnItemClickListener onItemClickListener;


    public itfOnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(itfOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public adpDocumentos(Context context, List<Documento> lstDocs) {
        this.context = context;
        this.lstDocumentos = lstDocs;
    }

    @Override
    public RepoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_documento, parent, false);
        RepoHolder repoHolder = new RepoHolder(itemView);
        return new RepoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RepoHolder holder, int position) {
        final Documento documento = lstDocumentos.get(position);

        holder.txtTipo.setText(lstDocumentos.get(position).getTipoDocumento().getDescricao());
        holder.txtTitulo.setText(lstDocumentos.get(position).getTitulo());
        holder.txtPalavrasChave.setText(lstDocumentos.get(position).getPalavrasChave()!=null ? lstDocumentos.get(position).getPalavrasChave() : "Palavras-Chave");

        final View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(documento);
            }
        };
        holder.txtTitulo.setOnClickListener(listener);
    }

    public class RepoHolder extends RecyclerView.ViewHolder {
        public TextView txtTitulo, txtResumo, txtPalavrasChave, txtTipo;

        public RepoHolder(View itemView) {
            super(itemView);
            this.txtTipo = (TextView) itemView.findViewById(R.id.txtTipoDocumento);
            this.txtTitulo = (TextView) itemView.findViewById(R.id.txtTitulo);
            this.txtPalavrasChave = (TextView) itemView.findViewById(R.id.txtPalavrasChave);
        }
    }

    @Override
    public int getItemCount() {
        return lstDocumentos.size();
    }
}

