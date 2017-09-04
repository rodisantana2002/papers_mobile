package com.rhcloud.papers.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.model.entity.Destino;

import java.util.List;

/**
 * Created by rodolfosantana on 30/08/17.
 */

public class adpRepositorios extends RecyclerView.Adapter<adpRepositorios.RepoHolder>  {
    private List<Destino> lstAcoes;
    private Context context;
    private itfOnItemClickListener onItemClickListener;


    public itfOnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(itfOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public adpRepositorios(Context context, List<Destino> lsAcoes) {
        this.context = context;
        this.lstAcoes = lsAcoes;
    }

    @Override
    public RepoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_repositorio, parent, false);
        RepoHolder repoHolder = new RepoHolder(itemView);
        return new RepoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RepoHolder holder, int position) {
        final Destino destino = lstAcoes.get(position);

        holder.txtDescricao.setText(lstAcoes.get(position).getDescricao());
        holder.txtClassificacao.setText(lstAcoes.get(position).getClassificacao());

        final View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                onItemClickListener.onItemClick(destino);
            }
        };
        holder.txtClassificacao.setOnClickListener(listener);
        holder.txtDescricao.setOnClickListener(listener);
    }

    public class RepoHolder extends RecyclerView.ViewHolder {
        public TextView txtClassificacao;
        public TextView txtDescricao;

        public RepoHolder(View itemView) {
            super(itemView);
            this.txtClassificacao = (TextView) itemView.findViewById(R.id.txtClassificacao);
            this.txtDescricao = (TextView) itemView.findViewById(R.id.txtDescricaoRepositorio);
        }
    }

    @Override
    public int getItemCount() {
        return lstAcoes.size();
    }

}

