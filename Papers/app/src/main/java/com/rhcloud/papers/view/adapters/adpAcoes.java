package com.rhcloud.papers.view.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.model.transitorio.Acao;

import java.util.List;

/**
 * Created by rodolfosantana on 30/08/17.
 */

public class adpAcoes extends RecyclerView.Adapter<adpAcoes.AcoesHolder>  {
    private List<Acao> lstAcoes;
    private itfOnItemClickListener onItemClickListener;

    public itfOnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(itfOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class AcoesHolder extends RecyclerView.ViewHolder {
        public TextView txtNomeAcao;
        public TextView txtComentarioAcao;
        public ImageView imgAcao;

        public AcoesHolder(View itemView) {
            super(itemView);
            this.txtNomeAcao = (TextView) itemView.findViewById(R.id.txtNomeAcao);
            this.txtComentarioAcao = (TextView) itemView.findViewById(R.id.txtComentarioAcao);
            this.imgAcao = (ImageView) itemView.findViewById(R.id.imgAcao);
        }
    }

    public adpAcoes(List<Acao> lsAcoes) {
        this.lstAcoes = lsAcoes;
    }

    @Override
    public AcoesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_acoes, parent, false);
        return new AcoesHolder(itemView);

    }

    @Override
    public void onBindViewHolder(AcoesHolder holder, int position) {
        holder.txtNomeAcao.setText(lstAcoes.get(position).getNomeAcao());
        holder.txtComentarioAcao.setText(lstAcoes.get(position).getComentarioAcao());
        holder.imgAcao.setImageDrawable(lstAcoes.get(position).getImgAcao());
    }

    @Override
    public int getItemCount() {
        return lstAcoes.size();
    }

}

