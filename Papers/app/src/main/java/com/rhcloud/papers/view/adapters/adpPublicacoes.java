package com.rhcloud.papers.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.helpers.generic.hlpMapasValoresEnuns;
import com.rhcloud.papers.model.entity.FilaSubmissao;

import java.util.List;

/**
 * Created by rodolfosantana on 11/09/17.
 */

public class adpPublicacoes extends RecyclerView.Adapter<adpPublicacoes.RepoHolder>{
    private List<FilaSubmissao> filaSubmissaos;
    private Context context;
    private itfOnItemClickListener onItemClickListener;
    private hlpMapasValoresEnuns mapasValoresEnuns;


    public itfOnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(itfOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public adpPublicacoes(Context context, List<FilaSubmissao> submissaos) {
        this.context = context;
        this.filaSubmissaos = submissaos;
        mapasValoresEnuns = new hlpMapasValoresEnuns();
    }

    @Override
    public RepoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_publicacao, parent, false);
        RepoHolder repoHolder = new RepoHolder(itemView);
        return new RepoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RepoHolder holder, int position) {
        final FilaSubmissao filaSubmissao = filaSubmissaos.get(position);

        holder.txtSituacao.setText(mapasValoresEnuns.getDescricaoSituacao(filaSubmissaos.get(position).getSituacao()));
        holder.txtVersao.setText(filaSubmissaos.get(position).getVersao());
        holder.txtTitulo.setText(filaSubmissaos.get(position).getDocumento().getTitulo());
        holder.txtDestino.setText(filaSubmissaos.get(position).getDestino().getDescricao() + " - " + filaSubmissaos.get(position).getDestino().getClassificacao());
        if (filaSubmissaos.get(position).getDtLimiteSubmissao()!=null){
            holder.txtDataLimiteSubmissao.setText(filaSubmissaos.get(position).getDtLimiteSubmissao());
        }

        final View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(filaSubmissao);
            }
        };

        holder.txtTitulo.setOnClickListener(listener);
        holder.txtDataLimiteSubmissao.setOnClickListener(listener);
        holder.txtSituacao.setOnClickListener(listener);
        holder.txtVersao.setOnClickListener(listener);
        holder.txtDestino.setOnClickListener(listener);

        holder.gridSituacao.setOnClickListener(listener);
        holder.gridSubmissao.setOnClickListener(listener);
        holder.gridTitulo.setOnClickListener(listener);
    }

    public class RepoHolder extends RecyclerView.ViewHolder {
        public TextView txtTitulo, txtSituacao, txtVersao, txtQualis, txtDestino, txtCriadoPor, txtDataLimiteSubmissao;
        public GridLayout gridSituacao, gridTitulo, gridSubmissao, gridSubmissaoDetalhe, gridInfo;

        public RepoHolder(View itemView) {
            super(itemView);
            this.txtTitulo = (TextView) itemView.findViewById(R.id.txtTitulo);
            this.txtSituacao = (TextView) itemView.findViewById(R.id.txtSituacao);
            this.txtVersao = (TextView) itemView.findViewById(R.id.txtVersao);
            this.txtDestino = (TextView) itemView.findViewById(R.id.txtDestino);
            this.txtDataLimiteSubmissao = (TextView) itemView.findViewById(R.id.txtDataLimite);

            this.gridSituacao = (GridLayout) itemView.findViewById(R.id.gridSituacao);
            this.gridTitulo = (GridLayout) itemView.findViewById(R.id.gridTitulo);
            this.gridSubmissao = (GridLayout) itemView.findViewById(R.id.gridSubmissao);
        }
    }

    @Override
    public int getItemCount() {
        return filaSubmissaos.size();
    }
}

