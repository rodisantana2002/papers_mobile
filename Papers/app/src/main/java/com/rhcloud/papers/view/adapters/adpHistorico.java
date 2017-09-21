package com.rhcloud.papers.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.model.entity.HistoricoFilaSubmissao;
import com.rhcloud.papers.model.enumeration.Situacao;

import java.util.List;

/**
 * Created by rodolfosantana on 12/09/17.
 */

public class adpHistorico extends RecyclerView.Adapter<adpHistorico.RepoHolder>{
    private List<HistoricoFilaSubmissao> historicoFilaSubmissaos;
    private Context context;
    private itfOnItemClickListener onItemClickListener;

    public itfOnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(itfOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public adpHistorico(Context context, List<HistoricoFilaSubmissao> historicoFilaSubmissaoList) {
        this.context = context;
        this.historicoFilaSubmissaos = historicoFilaSubmissaoList;
    }

    @Override
    public adpHistorico.RepoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_publicacao_historico, parent, false);
        adpHistorico.RepoHolder repoHolder = new adpHistorico.RepoHolder(itemView);
        return new adpHistorico.RepoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(adpHistorico.RepoHolder holder, int position) {
        final HistoricoFilaSubmissao historicoFilaSubmissao = historicoFilaSubmissaos.get(position);

        holder.txtDataAtualizacao.setText(historicoFilaSubmissao.getDtAtualizacao() + " " + historicoFilaSubmissao.getHoraAtualizacao());
        holder.txtComentario.setText(historicoFilaSubmissao.getComentario());
        holder.txtSituacaoHistorico.setText(historicoFilaSubmissao.getSituacao().getDescricao() + "    versão: " + historicoFilaSubmissao.getVersao());

        if (historicoFilaSubmissao.getSituacao().equals(Situacao.EM_AVALIACAO_ORIENTADOR)){
            holder.imgHistorico.setImageResource(R.drawable.ic_arrow_forward_black_24dp);
        }
        else if ((historicoFilaSubmissao.getSituacao().equals(Situacao.AGUARDANDO_AJUSTES))){
            holder.imgHistorico.setImageResource(R.drawable.ic_thumb_down_black_24dp);
        }
        else if ((historicoFilaSubmissao.getSituacao().equals(Situacao.LIBERADO_ORIENTADOR))){
            holder.imgHistorico.setImageResource(R.drawable.ic_thumb_up_black_24dp);
        }
        else if ((historicoFilaSubmissao.getSituacao().equals(Situacao.SUBMETIDO_PUBLICACAO))){
            holder.imgHistorico.setImageResource(R.drawable.ic_send_black_18dp);
        }
        else if ((historicoFilaSubmissao.getSituacao().equals(Situacao.APROVADA_PUBLICACAO))){
            holder.imgHistorico.setImageResource(R.drawable.ic_spellcheck_black_24dp);
        }
        else if ((historicoFilaSubmissao.getSituacao().equals(Situacao.PUBLICADO))){
            holder.imgHistorico.setImageResource(R.drawable.ic_mood_black_24dp);
        }
        else if ((historicoFilaSubmissao.getSituacao().equals(Situacao.REJEITADO))){
            holder.imgHistorico.setImageResource(R.drawable.ic_mood_bad_black_24dp);
        }
        else if ((historicoFilaSubmissao.getSituacao().equals(Situacao.CANCELADO))){
            holder.imgHistorico.setImageResource(R.drawable.ic_close_black_24dp);
        }

        final View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                onItemClickListener.onItemClick(historicoFilaSubmissao);
            }
        };
    }

    public class RepoHolder extends RecyclerView.ViewHolder {
        public TextView txtDataAtualizacao, txtComentario, txtSituacaoHistorico;
        public ImageView imgHistorico;

        public RepoHolder(View itemView) {
            super(itemView);
            this.txtDataAtualizacao = (TextView) itemView.findViewById(R.id.txtDataAtualizacao);
            this.txtComentario = (TextView) itemView.findViewById(R.id.txtComentario);
            this.txtSituacaoHistorico = (TextView) itemView.findViewById(R.id.txtSituacaoHistorico);
            this.imgHistorico = (ImageView) itemView.findViewById(R.id.imgHistorico);
        }
    }

    @Override
    public int getItemCount() {
        return historicoFilaSubmissaos.size();
    }

}
