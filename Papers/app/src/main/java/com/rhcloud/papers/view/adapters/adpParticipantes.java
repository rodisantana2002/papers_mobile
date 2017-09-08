package com.rhcloud.papers.view.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.model.entity.DocumentosPessoas;

import java.util.List;

/**
 * Created by rodolfosantana on 08/09/17.
 */

public class adpParticipantes extends RecyclerView.Adapter<adpParticipantes.RepoHolder>  {
    private List<DocumentosPessoas> lstAutores;
    private Context context;
    private itfOnItemClickListener onItemClickListener;
    private GridLayout gridParticipante;
    private ImageView imgParticipante;


    public itfOnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(itfOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public adpParticipantes(Context context, List<DocumentosPessoas> lsAutores) {
        this.context = context;
        this.lstAutores = lsAutores;
    }

    @Override
    public RepoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_participantes, parent, false);
        RepoHolder repoHolder = new RepoHolder(itemView);
        return new RepoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RepoHolder holder, int position) {
        final DocumentosPessoas documentosPessoas = lstAutores.get(position);

        holder.txtNomeCompleto.setText(lstAutores.get(position).getPessoa().getPrimeiroNome() + " " + lstAutores.get(position).getPessoa().getSegundoNome());

        if(lstAutores.get(position).getPessoa().getInstituicao()!=null){
            holder.txtInstituicao.setText(lstAutores.get(position).getPessoa().getInstituicao());
        }

        if (lstAutores.get(position).getPessoa().getFoto()==null){
            holder.imgFoto.setImageDrawable(context.getDrawable(R.drawable.ic_account_circle_black_48dp));
        }
        else {
            Bitmap bmUser = BitmapFactory.decodeByteArray(lstAutores.get(position).getPessoa().getFoto(), 0, lstAutores.get(position).getPessoa().getFoto().length);
            holder.imgFoto.setImageBitmap(bmUser);
        }

        final View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                onItemClickListener.onItemClick(documentosPessoas);
            }
        };
        holder.txtNomeCompleto.setOnClickListener(listener);
        holder.txtInstituicao.setOnClickListener(listener);
        holder.imgFoto.setOnClickListener(listener);
    }

    public class RepoHolder extends RecyclerView.ViewHolder {
        public TextView txtNomeCompleto, txtInstituicao;
        public ImageView imgFoto;

        public RepoHolder(View itemView) {
            super(itemView);
            this.imgFoto = (ImageView) itemView.findViewById(R.id.imgFotoParticipante);
            this.txtNomeCompleto = (TextView) itemView.findViewById(R.id.txtNomeParticipante);
            this.txtInstituicao = (TextView) itemView.findViewById(R.id.txtInstituicaoParticipante);
        }
    }

    @Override
    public int getItemCount() {
        return lstAutores.size();
    }

}
