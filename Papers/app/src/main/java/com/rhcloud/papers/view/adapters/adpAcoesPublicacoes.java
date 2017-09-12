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
import com.rhcloud.papers.model.transitorio.Acao;

import java.util.List;

/**
 * Created by rodolfosantana on 12/09/17.
 */

public class adpAcoesPublicacoes extends  RecyclerView.Adapter<adpAcoesPublicacoes.AcoesHolder>  {
        private List<Acao> lstAcoes;
        private Context context;
        private itfOnItemClickListener onItemClickListener;


        public itfOnItemClickListener getOnItemClickListener() {
            return onItemClickListener;
        }

        public void setOnItemClickListener(itfOnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public adpAcoesPublicacoes(Context context, List<Acao> lsAcoes) {
            this.context = context;
            this.lstAcoes = lsAcoes;
        }

        @Override
        public adpAcoesPublicacoes.AcoesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_acoes_publicacoes, parent, false);
            adpAcoesPublicacoes.AcoesHolder acoesHolder = new adpAcoesPublicacoes.AcoesHolder(itemView);
            return new adpAcoesPublicacoes.AcoesHolder(itemView);
        }

        @Override
        public void onBindViewHolder(adpAcoesPublicacoes.AcoesHolder holder, int position) {
            final Acao acao = lstAcoes.get(position);

            holder.txtNomeAcao.setText(lstAcoes.get(position).getNomeAcao());
            holder.txtComentarioAcao.setText(lstAcoes.get(position).getComentarioAcao());
            holder.imgAcao.setImageDrawable(lstAcoes.get(position).getImgAcao());

            final View.OnClickListener listener = new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    onItemClickListener.onItemClick(acao);
                }
            };
            holder.txtNomeAcao.setOnClickListener(listener);
            holder.txtComentarioAcao.setOnClickListener(listener);
            holder.imgAcao.setOnClickListener(listener);
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

        @Override
        public int getItemCount() {
            return lstAcoes.size();
        }
}
