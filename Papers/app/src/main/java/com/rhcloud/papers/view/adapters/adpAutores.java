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
import com.rhcloud.papers.model.entity.Pessoa;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by rodolfosantana on 30/08/17.
 */

public class adpAutores extends RecyclerView.Adapter<adpAutores.RepoHolder>  {
    private List<Pessoa> lstAutores;
    private Context context;
    private itfOnItemClickListener onItemClickListener;


    public itfOnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(itfOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public adpAutores(Context context, List<Pessoa> lsAcoes) {
        this.context = context;
        this.lstAutores = lsAcoes;
    }

    @Override
    public RepoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_autor, parent, false);
        RepoHolder repoHolder = new RepoHolder(itemView);
        return new RepoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RepoHolder holder, int position) {
        final Pessoa pessoa = lstAutores.get(position);

        holder.txtNomeCompleto.setText(lstAutores.get(position).getPrimeiroNome() + " " + lstAutores.get(position).getSegundoNome());

        if(lstAutores.get(position).getInstituicao()!=null){
            holder.txtInstituicao.setText(lstAutores.get(position).getInstituicao());
        }

        if (lstAutores.get(position).getEstado()!=null && lstAutores.get(position).getCidade()!=null){
            holder.txtLocal.setText(lstAutores.get(position).getCidade() + " " + lstAutores.get(position).getEstado() );
        }

        if(lstAutores.get(position).getBiografia()==null){
            holder.txtSobreAutor.setText("Não foram registradas informaçôes profissionais até o momento");
        }
        else{
            if (lstAutores.get(position).getBiografia().length()<160){
                holder.txtSobreAutor.setText(lstAutores.get(position).getBiografia().toString());
            }
            else{
                holder.txtSobreAutor.setText(lstAutores.get(position).getBiografia().toString().substring(0, 160) + "...");
            }
        }

        if (lstAutores.get(position).getFoto()==null){
            holder.imgFoto.setImageDrawable(context.getDrawable(R.drawable.ic_account_circle_black_48dp));
        }
        else {
            Bitmap bmUser = BitmapFactory.decodeByteArray(lstAutores.get(position).getFoto(), 0, lstAutores.get(position).getFoto().length);
            holder.imgFoto.setImageBitmap(bmUser);
        }

        final View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                onItemClickListener.onItemClick(pessoa);
            }
        };
        holder.txtNomeCompleto.setOnClickListener(listener);
        holder.txtInstituicao.setOnClickListener(listener);
        holder.txtLocal.setOnClickListener(listener);
        holder.imgSituacao.setOnClickListener(listener);
        holder.gridAutor.setOnClickListener(listener);
        holder.gridAutor.setOnClickListener(listener);
        holder.gridLocal.setOnClickListener(listener);
        holder.txtSobreAutor.setOnClickListener(listener);
    }

    public class RepoHolder extends RecyclerView.ViewHolder {
        public TextView txtNomeCompleto, txtSobreAutor, txtInstituicao, txtLocal;
        public GridLayout gridAutor, gridLocal;
        public ImageView imgFoto, imgSituacao;

        public RepoHolder(View itemView) {
            super(itemView);
            this.imgFoto = (ImageView) itemView.findViewById(R.id.imgFotoAutor);
            this.imgSituacao = (ImageView) itemView.findViewById(R.id.imgFotoAutor);
            this.txtNomeCompleto = (TextView) itemView.findViewById(R.id.txtNomeAutor);
            this.txtInstituicao = (TextView) itemView.findViewById(R.id.txtInstituicaoAutorLista);
            this.txtLocal = (TextView) itemView.findViewById(R.id.txtLocal);
            this.txtSobreAutor = (TextView) itemView.findViewById(R.id.txtSobreAutor);
            this.gridAutor = (GridLayout) itemView.findViewById(R.id.gridAutor);
            this.gridLocal = (GridLayout) itemView.findViewById(R.id.gridLocal);
        }
    }

    @Override
    public int getItemCount() {
        return lstAutores.size();
    }

}

