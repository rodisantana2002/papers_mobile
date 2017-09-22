package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlAutorPerfil;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.helpers.generic.hlpMapasValoresEnuns;
import com.rhcloud.papers.model.entity.FilaSubmissao;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.model.transitorio.AutorPerfil;
import com.rhcloud.papers.view.adapters.adpPublicacoes;
import com.rhcloud.papers.view.decorator.dividerItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class viewPublicacao extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<FilaSubmissao> lstSubmissoes;
    private adpPublicacoes mAdapter;
    private ImageButton btnVoltar, btnEncerrarPesquisa, btnPesquisar;
    private FilaSubmissao filaSubmissao;
    private Usuario usuario;
    private ProgressDialog progressDialog;
    private procDados procDados;
    private TextView txtNenhumRegistro;
    private FloatingActionButton btnFloat;
    private AutorPerfil autorPerfil;
    private GridLayout gridPesquisar;
    private hlpMapasValoresEnuns hlpMapasValoresEnuns;
    private RadioButton btnEmAndamento, btnEncerradas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_publicacao);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_publicacoes);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        popularLista(getIntent().getExtras());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_responsavel_publicacao:
                    popularLista(autorPerfil.getLstResponsavelPublicacao());
                    return true;
                case R.id.navigation_participante_publicacao:
                    popularLista(autorPerfil.getLstParticipantePublicacao());
                    return true;
                case R.id.navigation_favorito_publicacao:
                    popularLista(autorPerfil.getLstFavoritosPublicacao());
                    return true;
            }
            return false;
        }
    };

    private void popularLista(Bundle bundle) {
        lstSubmissoes = new ArrayList<FilaSubmissao>();

        if (bundle != null) {
            usuario = (Usuario) bundle.getSerializable("usuario");
        }
        prepararComponenetes();
        procDados = new procDados(true);
        procDados.execute();
    }

    private void prepararComponenetes() {
        hlpMapasValoresEnuns = new hlpMapasValoresEnuns();
        recyclerView = (RecyclerView) findViewById(R.id.lstPublicacoes);
        txtNenhumRegistro = (TextView) findViewById(R.id.txtNenhumRegistroPublicacao);
        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarHomePublicacao);
        btnVoltar.setOnClickListener(viewPublicacao.this);

        btnEncerrarPesquisa = (ImageButton) findViewById(R.id.btnEncerrarPesquisaPublicacao);
        btnEncerrarPesquisa.setOnClickListener(viewPublicacao.this);

        btnPesquisar = (ImageButton) findViewById(R.id.btnPesquisarPublicacacao);
        btnPesquisar.setOnClickListener(viewPublicacao.this);

        btnEmAndamento = (RadioButton) findViewById(R.id.radEmAndamento);
        btnEmAndamento.setOnClickListener(viewPublicacao.this);
        btnEmAndamento.setButtonDrawable(R.drawable.ic_check_box_black_24dp);

        btnEncerradas = (RadioButton) findViewById(R.id.radFinalizadas);
        btnEncerradas.setOnClickListener(viewPublicacao.this);
        btnEncerradas.setButtonDrawable(R.drawable.ic_check_box_outline_blank_black_24dp);

        gridPesquisar = (GridLayout) findViewById(R.id.gridPesquisarPublicidade);
    }

    public void onClick(View view) {
        Intent intent;
        if (view.getId() == btnVoltar.getId()) {
            Bundle bundle = new Bundle();
            intent = new Intent(this, viewHome.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        if (view.getId() == btnPesquisar.getId()){
            gridPesquisar.setVisibility(View.VISIBLE);
        }

        if (view.getId() == btnEncerrarPesquisa.getId()){
            gridPesquisar.setVisibility(View.GONE);
        }

        if(view.getId() ==btnEmAndamento.getId()){
            gridPesquisar.setVisibility(View.GONE);
            btnEmAndamento.setButtonDrawable(R.drawable.ic_check_box_black_24dp);
            btnEncerradas.setButtonDrawable(R.drawable.ic_check_box_outline_blank_black_24dp);
            procDados = new procDados(true);
            procDados.execute();

        }

        if(view.getId() ==btnEncerradas.getId()){
            gridPesquisar.setVisibility(View.GONE);
            btnEmAndamento.setButtonDrawable(R.drawable.ic_check_box_outline_blank_black_24dp);
            btnEncerradas.setButtonDrawable(R.drawable.ic_check_box_black_24dp);
            procDados = new procDados(false);
            procDados.execute();
        }
    }

    private void popularLista(List<FilaSubmissao> filaSubmissaos) {
        mAdapter = new adpPublicacoes(viewPublicacao.this, filaSubmissaos);
        mAdapter.setOnItemClickListener(new itfOnItemClickListener<FilaSubmissao>() {

            @Override
            public void onItemClick(FilaSubmissao item) {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("publicacao", item);
                    bundle.putSerializable("usuario", usuario);
                    bundle.putSerializable("autorPerfil", autorPerfil);
                    Intent intent = new Intent(viewPublicacao.this, viewPublicacaoDetail.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(viewPublicacao.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new dividerItemDecorator(viewPublicacao.this, LinearLayoutManager.VERTICAL,0));
        recyclerView.setAdapter(mAdapter);

        if (filaSubmissaos.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            txtNenhumRegistro.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            txtNenhumRegistro.setVisibility(View.GONE);
        }

    }
    private class procDados extends AsyncTask<Void, Void, List<FilaSubmissao>> {
        private boolean situacao;

        public procDados(boolean situacao){
            this.situacao = situacao;
        }

        @Override
        protected List<FilaSubmissao> doInBackground(Void...voids) {
            ctrlAutorPerfil ctrlAutorPerfil;
            try {
                ctrlAutorPerfil = new ctrlAutorPerfil(usuario);
                autorPerfil = ctrlAutorPerfil.getAutorPublicacoes(situacao);
                lstSubmissoes = autorPerfil.getLstResponsavelPublicacao();

            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                String msg = excPassaErro.getMessage();
                hlpDialog.getAlertDialog(viewPublicacao.this, "Atenção", msg, "Ok", new itfDialogGeneric() {
                    @Override
                    public void onButtonAction(boolean value) throws com.rhcloud.papers.excecoes.excPassaErro {
                    }
                });
                lstSubmissoes = new ArrayList<FilaSubmissao>();
            }
            return lstSubmissoes;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewPublicacao.this, "Aguarde", "Carregando dados...");
        }

        @Override
        protected void onPostExecute(List<FilaSubmissao> documentos) {
            progressDialog.dismiss();
            popularLista(lstSubmissoes);
        }
    }
}

