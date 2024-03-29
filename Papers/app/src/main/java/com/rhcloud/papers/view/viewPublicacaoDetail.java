package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.helpers.generic.hlpMapasValoresEnuns;
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
    private TextView txtTitulo, txtSituacao, txtVersao, txtDestino, txtDataLimiteSubmissao, txtIdiona, txtNenhumRegistro, txtTituloLista, txtDtPublicacao, lblDtPublicacao;
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

        recyclerViewAcoes = (RecyclerView) findViewById(R.id.lstGeral);
        recyclerViewAcoes.addItemDecoration(new dividerItemDecorator(this, LinearLayoutManager.VERTICAL,100));
        recyclerViewHistorico = (RecyclerView) findViewById(R.id.lstGeral);

        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarHomePublicacaoDetail);
        btnVoltar.setOnClickListener(viewPublicacaoDetail.this);

        btnEditar = (ImageButton) findViewById(R.id.btnEditarPublica);
        btnEditar.setOnClickListener(viewPublicacaoDetail.this);

        if (filaSubmissao.getSituacao().equals(Situacao.CANCELADO) ||
            filaSubmissao.getSituacao().equals(Situacao.PUBLICADO) ||
            filaSubmissao.getSituacao().equals(Situacao.REJEITADO)){
            btnEditar.setVisibility(View.GONE);
        }
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
        txtDtPublicacao =(TextView) findViewById(R.id.txtDataPublicacaoDetail);
        lblDtPublicacao =(TextView) findViewById(R.id.lblDataPublicacaoPublicacaoDetail);

        txtSituacao.setText(mapasValoresEnuns.getDescricaoSituacao(filaSubmissao.getSituacao()));
        txtSituacao.setTextColor(mapasValoresEnuns.getSituacaoColor(filaSubmissao.getSituacao()));
        txtVersao.setText(filaSubmissao.getVersao());
        txtTitulo.setText(filaSubmissao.getDocumento().getTitulo());
        txtDestino.setText(filaSubmissao.getDestino().getDescricao() + " - " + filaSubmissao.getDestino().getClassificacao());
        if (filaSubmissao.getDtLimiteSubmissao()!=null){
            txtDataLimiteSubmissao.setText(filaSubmissao.getDtLimiteSubmissao());
        }
        if (filaSubmissao.getDtPublicacao()!=null){
            lblDtPublicacao.setVisibility(View.VISIBLE);
            txtDtPublicacao.setVisibility(View.VISIBLE);
            txtDtPublicacao.setText(filaSubmissao.getDtPublicacao());
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
        recyclerViewHistorico.setAdapter(adpHistorico);

        if (lstHistorico.isEmpty()){
            txtNenhumRegistro.setVisibility(View.VISIBLE);
            txtNenhumRegistro.setText("Nenhum histórico registrado para a Publicação");
            txtTituloLista.setVisibility(View.GONE);
            recyclerViewHistorico.setVisibility(View.GONE);
        }
        else{
            txtNenhumRegistro.setVisibility(View.GONE);
            txtTituloLista.setText("Histórico de Alterações");
            txtTituloLista.setVisibility(View.VISIBLE);
            recyclerViewHistorico.setVisibility(View.VISIBLE);
        }
    }


    private void popularListaAcoes() {
        lstAcoes = (ArrayList<Acao>) mapasValoresEnuns.getListaAcoesSituacao(filaSubmissao.getSituacao());
        prepararLista();
    }

    private void prepararLista(){
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
                    acao.setNomeAcao(item.getNomeAcao());
                }
                else if(item.getId()==2){
                    acao.setSituacao(Situacao.AGUARDANDO_AJUSTES);
                    acao.setNomeAcao(item.getNomeAcao());
                }
                else if(item.getId()==3){
                    acao.setSituacao(Situacao.LIBERADO_ORIENTADOR);
                    acao.setNomeAcao(item.getNomeAcao());
                }
                else if(item.getId()==4){
                    acao.setSituacao(Situacao.SUBMETIDO_PUBLICACAO);
                    acao.setNomeAcao(item.getNomeAcao());
                }
                else if(item.getId()==5){
                    acao.setSituacao(Situacao.APROVADA_PUBLICACAO);
                    acao.setNomeAcao(item.getNomeAcao());
                }
                else if(item.getId()==6){
                    acao.setSituacao(Situacao.PUBLICADO);
                    acao.setNomeAcao(item.getNomeAcao());
                }
                else if(item.getId()==7){
                    acao.setSituacao(Situacao.REJEITADO);
                    acao.setNomeAcao(item.getNomeAcao());
                }
                else if(item.getId()==8){
                    acao.setSituacao(Situacao.CANCELADO);
                    acao.setNomeAcao(item.getNomeAcao());
                }

                bundle.putSerializable("acao", acao);
                Intent intent = new Intent(viewPublicacaoDetail.this, viewPublicacaoSituacao.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        recyclerViewAcoes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAcoes.setItemAnimator(new DefaultItemAnimator());
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
                String msg = excPassaErro.getMessage();
                hlpDialog.getAlertDialog(viewPublicacaoDetail.this, "Atenção", msg, "Ok", new itfDialogGeneric() {
                    @Override
                    public void onButtonAction(boolean value) throws com.rhcloud.papers.excecoes.excPassaErro {
                    }
                });
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
            popularListaAcoes();
        }
    }

}
