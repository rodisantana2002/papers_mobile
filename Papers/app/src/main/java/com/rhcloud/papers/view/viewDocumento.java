package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlAutorPerfil;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.model.entity.Documento;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.model.transitorio.AutorPerfil;
import com.rhcloud.papers.view.adapters.adpDocumentos;
import com.rhcloud.papers.view.decorator.dividerItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class viewDocumento extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Documento> lstDocumentos;
    private adpDocumentos mAdapter;
    private ImageButton btnVoltar;
    private Documento documento;
    private Usuario usuario;
    private ProgressDialog progressDialog;
    private procDados procDados;
    private TextView txtNenhumRegistro;
    private FloatingActionButton btnFloat;
    private AutorPerfil autorPerfil;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_responsavel:
                    if (autorPerfil==null){
                        popularLista(new ArrayList<Documento>());
                    }
                    else {
                        popularLista(autorPerfil.getLstDocumentosResponsavel());
                    }
                    return true;
                case R.id.navigation_participante:
                    if (autorPerfil==null){
                        popularLista(new ArrayList<Documento>());
                    }
                    else {
                        popularLista(autorPerfil.getLstDocumentosParticipante());
                    }
                    return true;
                case R.id.navigation_favorito:
                    if (autorPerfil==null){
                        popularLista(new ArrayList<Documento>());
                    }
                    else {
                        popularLista(autorPerfil.getLstDocumentosFavoritos());
                    }
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_documentos);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        popularLista(getIntent().getExtras());
    }

    private void popularLista(Bundle bundle) {
        lstDocumentos = new ArrayList<Documento>();

        if (bundle != null) {
            usuario = (Usuario) bundle.getSerializable("usuario");
        }

        prepararComponenetes();
        procDados = new procDados();
        procDados.execute();
    }

    private void prepararComponenetes() {
        recyclerView = (RecyclerView) findViewById(R.id.lstDocumentos);
        recyclerView.addItemDecoration(new dividerItemDecorator(viewDocumento.this, LinearLayoutManager.VERTICAL,0));

        txtNenhumRegistro = (TextView) findViewById(R.id.txtNenhumRegistroDocumento);
        btnFloat = (FloatingActionButton) findViewById(R.id.btnFloatDocumento);
        btnFloat.setOnClickListener(viewDocumento.this);
        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarHomeDocumento);
        btnVoltar.setOnClickListener(viewDocumento.this);
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

        if (view.getId() == btnFloat.getId()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("documento", new Documento());
            bundle.putSerializable("usuario", usuario);
            bundle.putSerializable("autoPerfil", autorPerfil);
            intent = new Intent(viewDocumento.this, viewDocumentoEdit.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private void popularLista(List<Documento> lstDocs) {

        mAdapter = new adpDocumentos(viewDocumento.this, lstDocs);
        mAdapter.setOnItemClickListener(new itfOnItemClickListener<Documento>() {

            @Override
            public void onItemClick(Documento item) {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("documento", item);
                    bundle.putSerializable("usuario", usuario);
                    bundle.putSerializable("autorPerfil", autorPerfil);
                    Intent intent = new Intent(viewDocumento.this, viewDocumentoDetail.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(viewDocumento.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        if (lstDocs.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            txtNenhumRegistro.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            txtNenhumRegistro.setVisibility(View.GONE);
        }

    }

    private class procDados extends AsyncTask<Void, Void, List<Documento>> {

        @Override
        protected List<Documento> doInBackground(Void... voids) {
            try {
                ctrlAutorPerfil ctrlAutorPerfil = new ctrlAutorPerfil(usuario);
                autorPerfil = ctrlAutorPerfil.getAutorPerfil();
                lstDocumentos = autorPerfil.getLstDocumentosResponsavel();
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                String msg = excPassaErro.getMessage();
                hlpDialog.getAlertDialog(viewDocumento.this, "Atenção", msg, "Ok", new itfDialogGeneric() {
                    @Override
                    public void onButtonAction(boolean value) throws com.rhcloud.papers.excecoes.excPassaErro {
                    }
                });
                lstDocumentos = new ArrayList<Documento>();
            }
            return lstDocumentos;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewDocumento.this, "Aguarde", "Carregando dados...");
        }

        @Override
        protected void onPostExecute(List<Documento> documentos) {
            progressDialog.dismiss();
            popularLista(lstDocumentos);
        }
    }
}
