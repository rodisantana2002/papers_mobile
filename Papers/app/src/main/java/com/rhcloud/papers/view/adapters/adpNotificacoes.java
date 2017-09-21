package com.rhcloud.papers.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.model.entity.Notificacao;
import com.rhcloud.papers.model.enumeration.Situacao;
import com.rhcloud.papers.model.enumeration.Status;

import java.util.List;

/**
 * Created by rodolfosantana on 19/09/17.
 */

public class adpNotificacoes extends RecyclerView.Adapter<adpNotificacoes.RepoHolder>{

    private List<Notificacao> notificacoes;
    private Context context;
    private itfOnItemClickListener onItemClickListener;

    public itfOnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(itfOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public adpNotificacoes(Context context, List<Notificacao> notificacoes) {
        this.context = context;
        this.notificacoes = notificacoes;
    }

    @Override
    public RepoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notificacao, parent, false);
        RepoHolder repoHolder = new RepoHolder(itemView);
        return new RepoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RepoHolder holder, int position) {
        final Notificacao notificacao = notificacoes.get(position);

        holder.txtStatus.setText(notificacao.getStatus().getDescricao());
        if(notificacao.getStatus().equals(Status.PENDENTE)){
            holder.imgMsg.setImageResource(R.drawable.ic_mail_black_24dp);
        }
        else if(notificacao.getStatus().equals(Status.LIDA)){
            holder.imgMsg.setImageResource(R.drawable.ic_drafts_black_24dp);

        }
        else if(notificacao.getStatus().equals(Status.ARQUIVADA)){
            holder.imgMsg.setImageResource(R.drawable.ic_archive_black_24dp);
        }

        holder.txtDataEnvio.setText(notificacao.getDtCriacao() + " " + notificacao.getHoraCriacao());
        if(notificacao.getConteudo().length()>=120){
            holder.txtNotificacao.setText("Novidades sobre o Artigo: " + notificacao.getDocumento().getTitulo() + ":\n" + notificacao.getConteudo().substring(0,119) + "...");
        }
        else{
            holder.txtNotificacao.setText("Novidades sobre o Artigo: " + notificacao.getDocumento().getTitulo() + ":\n" + notificacao.getConteudo());
        }

        final View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(notificacao);
            }
        };

        holder.txtStatus.setOnClickListener(listener);
        holder.txtDataEnvio.setOnClickListener(listener);
        holder.txtNotificacao.setOnClickListener(listener);

        holder.gridStatus.setOnClickListener(listener);
        holder.gridNotificacao.setOnClickListener(listener);
    }

    public class RepoHolder extends RecyclerView.ViewHolder {
        public TextView txtStatus, txtDataEnvio, txtNotificacao;
        public GridLayout gridStatus, gridNotificacao;
        public ImageView imgMsg;

        public RepoHolder(View itemView) {
            super(itemView);
            this.txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            this.txtDataEnvio = (TextView) itemView.findViewById(R.id.txtDataEnvio);
            this.txtNotificacao = (TextView) itemView.findViewById(R.id.txtNotificacao);

            this.gridStatus = (GridLayout) itemView.findViewById(R.id.gridStatus);
            this.gridNotificacao = (GridLayout) itemView.findViewById(R.id.gridNotificacao);
            this.imgMsg = (ImageView) itemView.findViewById(R.id.imgMsg);
        }
    }

    @Override
    public int getItemCount() {
        return notificacoes.size();
    }
}

