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
import android.widget.ImageButton;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlAutorPerfil;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.model.entity.FilaSubmissao;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.model.transitorio.AutorPerfil;
import com.rhcloud.papers.view.adapters.adpPublicacoes;
import com.rhcloud.papers.view.decorator.dividerItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class viewPublicacao extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<FilaSubmissao> lstSubmissoes;
    private adpPublicacoes mAdapter;
    private ImageButton btnVoltar;
    private FilaSubmissao filaSubmissao;
    private Usuario usuario;
    private ProgressDialog progressDialog;
    private procDados procDados;
    private TextView txtNenhumRegistro;
    private FloatingActionButton btnFloat;
    private AutorPerfil autorPerfil;


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
        procDados = new procDados();
        procDados.execute();
    }

    private void prepararComponenetes() {
        recyclerView = (RecyclerView) findViewById(R.id.lstPublicacoes);
        txtNenhumRegistro = (TextView) findViewById(R.id.txtNenhumRegistroPublicacao);
        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarHomePublicacao);
        btnVoltar.setOnClickListener(viewPublicacao.this);
    }

    public void onClick(View view) {
        Intent intent;
        if (view.getId() == btnVoltar.getId()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            intent = new Intent(this, viewHome.class);
            intent.putExtras(bundle);
            startActivity(intent);
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
        recyclerView.addItemDecoration(new dividerItemDecorator(viewPublicacao.this, LinearLayoutManager.VERTICAL));
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

        @Override
        protected List<FilaSubmissao> doInBackground(Void...voids) {
            try {
                ctrlAutorPerfil ctrlAutorPerfil = new ctrlAutorPerfil(usuario);
                autorPerfil = ctrlAutorPerfil.getAutorPublicacoes();
                lstSubmissoes = autorPerfil.getLstResponsavelPublicacao();

            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                excPassaErro.getMessage();
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

