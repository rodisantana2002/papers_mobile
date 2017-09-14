package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlHistorico;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.helpers.rest.hlpMapasValoresEnuns;
import com.rhcloud.papers.model.entity.FilaSubmissao;
import com.rhcloud.papers.model.entity.HistoricoFilaSubmissao;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.model.enumeration.AcoesPublicacao;
import com.rhcloud.papers.model.enumeration.Situacao;
import com.rhcloud.papers.model.transitorio.Acao;
import com.rhcloud.papers.model.transitorio.AutorPerfil;
import com.rhcloud.papers.view.adapters.adpAcoesPublicacoes;
import com.rhcloud.papers.view.adapters.adpHistorico;
import com.rhcloud.papers.view.decorator.dividerItemDecorator;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class viewPublicacaoDetail extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerViewHistorico, recyclerViewAcoes;
    private RecyclerView.LayoutManager layoutManager;
    private adpHistorico adpHistorico;
    private adpAcoesPublicacoes mAdapter;

    private ImageButton btnVoltar, btnEditar;
    private FilaSubmissao filaSubmissao;
    private Usuario usuario;
    private AutorPerfil autorPerfil;
    private TextView txtTitulo, txtSituacao, txtVersao, txtDestino, txtDataLimiteSubmissao, txtIdiona, txtNenhumRegistro, txtTituloLista;
    private ArrayList<Acao> lstAcoes;
    private ArrayList<HistoricoFilaSubmissao> lstHistorico;
    private hlpMapasValoresEnuns mapasValoresEnuns;
    private ProgressDialog progressDialog;
    private procDados procDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_publicacao_detail);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_publicacoes_detail);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        prepararComponenetes(getIntent().getExtras());
    }

    private void prepararComponenetes(Bundle bundle) {
        filaSubmissao = (FilaSubmissao) bundle.getSerializable("publicacao");
        usuario = (Usuario) bundle.getSerializable("usuario");
        autorPerfil = (AutorPerfil) bundle.getSerializable("autorPerfil");

        txtNenhumRegistro = (TextView) findViewById(R.id.txtNenhumRegistro);
        txtTituloLista = (TextView) findViewById(R.id.txtTituloLista);
        mapasValoresEnuns = new hlpMapasValoresEnuns();

        recyclerViewHistorico = (RecyclerView) findViewById(R.id.lstGeral);

        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarHomePublicacaoDetail);
        btnVoltar.setOnClickListener(viewPublicacaoDetail.this);

        btnEditar = (ImageButton) findViewById(R.id.btnEditarPublica);
        btnEditar.setOnClickListener(viewPublicacaoDetail.this);

        popularDadosHeader();
        procDados = new procDados();
        procDados.execute();
    }

    public void onClick(View view) {
        Intent intent;
        if (view.getId() == btnVoltar.getId()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("publicacao", filaSubmissao);
            bundle.putSerializable("usuario", usuario);
            bundle.putSerializable("autorPerfil", autorPerfil);
            intent = new Intent(this, viewPublicacao.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        if (view.getId() == btnEditar.getId()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            bundle.putSerializable("autorPerfil", autorPerfil);
            bundle.putSerializable("publicacao", filaSubmissao);
            intent = new Intent(this, viewPublicacaoEdit.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_publicacao_historico:
                    popularListaHistorico();
                    return true;
                case R.id.navigation_publicacao_aocoes:
                    popularListaAcoes();
                    return true;
            }
            return false;
        }
    };

    private void popularDadosHeader(){
        txtTitulo = (TextView) findViewById(R.id.txtTituloPublicacaoDetail);
        txtSituacao = (TextView) findViewById(R.id.txtSituacaoPublicacaoDetail);
        txtVersao = (TextView) findViewById(R.id.txtVersaoPublicacaoDetail);
        txtDestino = (TextView) findViewById(R.id.txtDestinoPublicacaoDetail);
        txtDataLimiteSubmissao = (TextView) findViewById(R.id.txtDataLimitePublicacaoDetail);
        txtIdiona = (TextView) findViewById(R.id.txtIdiomaPublicacaoDetail);

        txtSituacao.setText(mapasValoresEnuns.getDescricaoSituacao(filaSubmissao.getSituacao()));
        txtVersao.setText(filaSubmissao.getVersao());
        txtTitulo.setText(filaSubmissao.getDocumento().getTitulo());
        txtDestino.setText(filaSubmissao.getDestino().getDescricao() + " - " + filaSubmissao.getDestino().getClassificacao());
        if (filaSubmissao.getDtLimiteSubmissao()!=null){
            txtDataLimiteSubmissao.setText(filaSubmissao.getDtLimiteSubmissao());
        }
        if(filaSubmissao.getIdioma()!=null){
            txtIdiona.setText(filaSubmissao.getIdioma());
        }
    }


    private void popularListaHistorico(){
        adpHistorico = new adpHistorico(viewPublicacaoDetail.this, lstHistorico);
        adpHistorico.setOnItemClickListener(new itfOnItemClickListener<HistoricoFilaSubmissao>(){

            @Override
            public void onItemClick(HistoricoFilaSubmissao item) {
            }
        }) ;

        recyclerViewHistorico.setLayoutManager(new LinearLayoutManager(viewPublicacaoDetail.this));
        recyclerViewHistorico.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHistorico.addItemDecoration(new dividerItemDecorator(viewPublicacaoDetail.this, LinearLayoutManager.VERTICAL));
        recyclerViewHistorico.setAdapter(adpHistorico);

        if (lstHistorico.isEmpty()){
            txtNenhumRegistro.setVisibility(View.VISIBLE);
            txtNenhumRegistro.setText("Nenhum histórico registrado para a Publicação");
            txtTituloLista.setVisibility(View.GONE);
            recyclerViewHistorico.setVisibility(View.GONE);
        }
        else{
            txtNenhumRegistro.setVisibility(View.GONE);
            txtTituloLista.setText("Histórico de Movimentações");
            txtTituloLista.setVisibility(View.VISIBLE);
            recyclerViewHistorico.setVisibility(View.VISIBLE);
        }
    }


    private void popularListaAcoes() {
        lstAcoes = new ArrayList<Acao>();

        Acao acao = new Acao();
        acao.setId(1);
        acao.setNomeAcao("Encaminhar para Orientador");
        acao.setComentarioAcao("movimenta o artigo para o orientador.");
        acao.setImgAcao(getDrawable(R.drawable.ic_arrow_forward_black_24dp));
        acao.setSituacao(Situacao.EM_AVALIACAO_ORIENTADOR);
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(2);
        acao.setNomeAcao("Devolver para Ajustes");
        acao.setComentarioAcao("retorna o artigo para a realização de ajustes.");
        acao.setImgAcao(getDrawable(R.drawable.ic_thumb_down_black_24dp));
        acao.setSituacao(Situacao.AGUARDANDO_AJUSTES);
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(3);
        acao.setNomeAcao("Liberar para Submissão");
        acao.setComentarioAcao("libera o artigo para ser encaminhado para publicação.");
        acao.setImgAcao(getDrawable(R.drawable.ic_thumb_up_black_24dp));
        acao.setSituacao(Situacao.LIBERADO_ORIENTADOR);
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(4);
        acao.setNomeAcao("Submeter para Publicação");
        acao.setComentarioAcao("registra que o artigo já foi encaminhado para a publicação.");
        acao.setImgAcao(getDrawable(R.drawable.ic_send_black_18dp));
        acao.setSituacao(Situacao.SUBMETIDO_PUBLICACAO);
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(5);
        acao.setNomeAcao("Registrar Aprovação Publicação");
        acao.setComentarioAcao("registra que o artigo foi liberado para a publicação.");
        acao.setImgAcao(getDrawable(R.drawable.ic_spellcheck_black_24dp));
        acao.setSituacao(Situacao.APROVADA_PUBLICACAO);
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(6);
        acao.setNomeAcao("Registrar Publicação");
        acao.setComentarioAcao("registra que o artigo foi publicado.");
        acao.setImgAcao(getDrawable(R.drawable.ic_mood_black_24dp));
        acao.setSituacao(Situacao.PUBLICADO);
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(7);
        acao.setNomeAcao("Rejeitar Publicação");
        acao.setComentarioAcao("registra que o artigo foi rejeitdo para publicação.");
        acao.setImgAcao(getDrawable(R.drawable.ic_mood_bad_black_24dp));
        acao.setSituacao(Situacao.REJEITADO);
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(8);
        acao.setNomeAcao("Cancelar");
        acao.setComentarioAcao("Encerra a publicação.");
        acao.setImgAcao(getDrawable(R.drawable.ic_close_black_24dp));
        acao.setSituacao(Situacao.CANCELADO);
        lstAcoes.add(acao);

        prepararLista();
    }


    private void prepararLista(){
        recyclerViewAcoes = (RecyclerView) findViewById(R.id.lstGeral);

        mAdapter = new adpAcoesPublicacoes(viewPublicacaoDetail.this, lstAcoes);
        mAdapter.setOnItemClickListener(new itfOnItemClickListener<Acao>() {
            @Override
            public void onItemClick(Acao item) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("publicacao", filaSubmissao);
                bundle.putSerializable("usuario", usuario);
                bundle.putSerializable("autorPerfil", autorPerfil);
                Acao acao = new Acao();
                if(item.getId()==1){
                    acao.setSituacao(Situacao.EM_AVALIACAO_ORIENTADOR);
                }
                else if(item.getId()==2){
                    acao.setSituacao(Situacao.AGUARDANDO_AJUSTES);
                }
                else if(item.getId()==3){
                    acao.setSituacao(Situacao.LIBERADO_ORIENTADOR);
                }
                else if(item.getId()==4){
                    acao.setSituacao(Situacao.SUBMETIDO_PUBLICACAO);
                }
                else if(item.getId()==5){
                    acao.setSituacao(Situacao.APROVADA_PUBLICACAO);
                }
                else if(item.getId()==6){
                    acao.setSituacao(Situacao.PUBLICADO);
                }
                else if(item.getId()==7){
                    acao.setSituacao(Situacao.REJEITADO);
                }
                else if(item.getId()==8){
                    acao.setSituacao(Situacao.CANCELADO);
                }

                bundle.putSerializable("acao", acao);
                Intent intent = new Intent(viewPublicacaoDetail.this, viewPublicacaoSituacao.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        recyclerViewAcoes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAcoes.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAcoes.addItemDecoration(new dividerItemDecorator(this, LinearLayoutManager.VERTICAL));
        recyclerViewAcoes.setAdapter(mAdapter);

        if (lstAcoes.isEmpty()){
            txtNenhumRegistro.setVisibility(View.VISIBLE);
            txtNenhumRegistro.setText("A Publicação não permite mais que a sua Situação seja alterada");
            txtTituloLista.setVisibility(View.GONE);
            recyclerViewHistorico.setVisibility(View.GONE);
        }
        else{
            txtNenhumRegistro.setVisibility(View.GONE);
            txtTituloLista.setText("Selecione a opção desejada");
            txtTituloLista.setVisibility(View.VISIBLE);
            recyclerViewHistorico.setVisibility(View.VISIBLE);
        }
    }

    private class procDados extends AsyncTask<Void, Void, List<HistoricoFilaSubmissao>> {

        @Override
        protected List<HistoricoFilaSubmissao> doInBackground(Void...voids) {
            try {
                HistoricoFilaSubmissao historicoFilaSubmissao = new HistoricoFilaSubmissao();
                historicoFilaSubmissao.setFilaSubmissao(filaSubmissao);
                ctrlHistorico ctrlHistorico = new ctrlHistorico(historicoFilaSubmissao);
                lstHistorico = (ArrayList<HistoricoFilaSubmissao>) ctrlHistorico.obterAllByPublicacao();

            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                excPassaErro.getMessage();
                lstHistorico = new ArrayList<HistoricoFilaSubmissao>();
            }
            return lstHistorico;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewPublicacaoDetail.this, "Aguarde", "Carregando dados...");
        }

        @Override
        protected void onPostExecute(List<HistoricoFilaSubmissao> historicoFilaSubmissaos) {
            progressDialog.dismiss();
            popularListaHistorico();
        }
    }

}
