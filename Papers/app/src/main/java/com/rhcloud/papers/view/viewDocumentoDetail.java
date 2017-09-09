package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlDocumentoPessoas;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.model.entity.Documento;
import com.rhcloud.papers.model.entity.DocumentosPessoas;
import com.rhcloud.papers.model.entity.Pessoa;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.model.transitorio.Acao;
import com.rhcloud.papers.view.adapters.adpAcoesDocumento;
import com.rhcloud.papers.view.adapters.adpDocumentoParticipantes;
import com.rhcloud.papers.view.decorator.dividerItemDecorator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodolfosantana on 06/09/17.
 */

public class viewDocumentoDetail extends AppCompatActivity implements View.OnClickListener {
    private TextView lblTitulo, lblTipo, lblPalavrasChave, lblResumo;
    private Button btnEditar;
    private ImageButton btnVoltar;

    private adpAcoesDocumento mAdapter;
    private adpDocumentoParticipantes mAdapterParticipantes;

    private ProgressDialog progressDialog;
    private Documento documento;
    private Pessoa pessoa;
    private Usuario usuario;

    private List<Pessoa> lstParticipantes;
    private List<Acao> lstAcoes;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private RecyclerView recyclerViewParticipantes;
    private RecyclerView.LayoutManager layoutManagerParticipantes;

    private procDados procDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_documento_detail);
        popularListaAcoes();
        prepararComponentes(getIntent().getExtras());

        lstParticipantes = new ArrayList<Pessoa>();
        procDados = new procDados();
        procDados.execute();
    }

    private void prepararComponentes(Bundle bundle) {
        documento = (Documento) bundle.getSerializable("documento");
        usuario = (Usuario) bundle.getSerializable("usuario");

        lblTitulo = (TextView) findViewById(R.id.lblTitulo);
        lblTipo = (TextView) findViewById(R.id.lblTipo);
        lblPalavrasChave = (TextView) findViewById(R.id.lblPalavrasChave);
        lblResumo = (TextView) findViewById(R.id.lblResumo);

        lblTitulo.setText(documento.getTitulo());
        lblTipo.setText(documento.getTipoDocumento().getDescricao());
        lblPalavrasChave.setText(documento.getPalavrasChave());
        if (documento.getResumo()!=null){
            if (documento.getResumo().length()<=200){
                lblResumo.setText(documento.getResumo());
            }
            else{
                lblResumo.setText(documento.getResumo().substring(0,200) + "...");
            }
        }
        else{lblResumo.setText("Não foi registrado um resumo para o Artigo.");}

        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarDocumentoDetail);
        btnVoltar.setOnClickListener(viewDocumentoDetail.this);

        recyclerView = (RecyclerView) findViewById(R.id.ListaAcoes);
        recyclerViewParticipantes = (RecyclerView) findViewById(R.id.ListaAutores);

        //Açoes
        mAdapter = new adpAcoesDocumento(viewDocumentoDetail.this, lstAcoes);
        mAdapter.setOnItemClickListener(new itfOnItemClickListener<Acao>() {
            @Override
            public void onItemClick(Acao item) {
                if(item.getId()==1){
                    alterarArtigo();
                }
                else  if(item.getId()==2){
                    alterarResumo();
                }
                else if(item.getId()==3){
                    gerenciarParticipantes();
                }
                else if(item.getId()==4){
                    criarPublicacao();
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new dividerItemDecorator(this, LinearLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(mAdapter);
    }

    private void alterarArtigo(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);
        bundle.putSerializable("documento", documento);

        Intent intent = new Intent(this, viewDocumentoEdit.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void alterarResumo() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);
        bundle.putSerializable("documento", documento);

        Intent intent = new Intent(this, viewDocumentoResumo.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void gerenciarParticipantes() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);
        bundle.putSerializable("documento", documento);

        Intent intent = new Intent(this, viewDocumentoPessoas.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void criarPublicacao() {
    }


    private void popularListaAcoes() {
        lstAcoes = new ArrayList<Acao>();

        Acao acao = new Acao();
        acao = new Acao();
        acao.setId(1);
        acao.setNomeAcao("Editar");
        acao.setComentarioAcao("mantenha os dados básicos do seu artigo atualizados.");
        acao.setImgAcao(getDrawable(R.drawable.ic_chrome_reader_mode_black_18dp));
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(2);
        acao.setNomeAcao("Resumo");
        acao.setComentarioAcao("registre um pequeno resumo para o seus artigo.");
        acao.setImgAcao(getDrawable(R.drawable.ic_reorder_black_18dp));
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(3);
        acao.setNomeAcao("Participantes");
        acao.setComentarioAcao("adicione ou remova os participantes do artigo.");
        acao.setImgAcao(getDrawable(R.drawable.ic_group_black_24dp));
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(4);
        acao.setNomeAcao("Publicações");
        acao.setComentarioAcao("registre e controle os envios de publicação do seu artigo.");
        acao.setImgAcao(getDrawable(R.drawable.ic_send_black_18dp));
        lstAcoes.add(acao);
    }

    private void prepararLista() {
        mAdapterParticipantes = new adpDocumentoParticipantes(viewDocumentoDetail.this, lstParticipantes);
        mAdapterParticipantes.setOnItemClickListener(new itfOnItemClickListener<Pessoa>(){

            @Override
            public void onItemClick(Pessoa item) {
            }
        }) ;

        recyclerViewParticipantes.setLayoutManager(new LinearLayoutManager(viewDocumentoDetail.this));
        recyclerViewParticipantes.setItemAnimator(new DefaultItemAnimator());
        recyclerViewParticipantes.setAdapter(mAdapterParticipantes);
        recyclerViewParticipantes.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == btnVoltar.getId()){
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            intent = new Intent(viewDocumentoDetail.this, viewDocumento.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private class procDados extends AsyncTask<Void, Void, List<Pessoa>> {
        @Override
        protected List<Pessoa> doInBackground(Void... voids) {
            ctrlDocumentoPessoas ctrlDocumentoPessoas  = new ctrlDocumentoPessoas(new DocumentosPessoas());
            try {
                List<DocumentosPessoas> documentosPessoases = ctrlDocumentoPessoas.obterAllByDocumento(documento.getId());
                for (DocumentosPessoas pessoas : documentosPessoases){
                    lstParticipantes.add(pessoas.getPessoa());
                }
                lstParticipantes.add(usuario.getPessoa());

            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                excPassaErro.getMessage();
                lstParticipantes = new ArrayList<Pessoa>();
            }
            return lstParticipantes;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewDocumentoDetail.this, "Aguarde", "Carregando dados...");
        }

        @Override
        protected void onPostExecute(List<Pessoa> destinos) {
            progressDialog.dismiss();
            prepararLista();
        }
    }
}
