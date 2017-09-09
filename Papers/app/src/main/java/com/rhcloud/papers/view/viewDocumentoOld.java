package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlAutorPerfil;
import com.rhcloud.papers.control.ctrlDocumento;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.model.entity.Documento;
import com.rhcloud.papers.model.entity.TipoDocumento;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.model.transitorio.AutorPerfil;
import com.rhcloud.papers.view.adapters.adpDocumentos;
import com.rhcloud.papers.view.adapters.adpTipoDocumento;
import com.rhcloud.papers.view.decorator.dividerItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class viewDocumentoOld  extends AppCompatActivity {
//    private RecyclerView recyclerView;
//    private RecyclerView.LayoutManager layoutManager;
//    private List<Documento> lstDocumentos;
//    private adpDocumentos mAdapter;
//    private ImageButton btnVoltar;
//    private Documento documento;
//    private Usuario usuario;
//    private ProgressDialog progressDialog;
//    private procDados procDados;
//    private TextView txtNenhumRegistro;
//    private FloatingActionButton btnFloat;
//    private AutorPerfil autorPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_documentos);
       // popularLista(getIntent().getExtras());
    }
//
//    private void popularLista(Bundle bundle) {
//        lstDocumentos = new ArrayList<Documento>();
//
//        if (bundle!=null){
//            usuario = (Usuario) bundle.getSerializable("usuario");
//        }
//
//        prepararComponenetes();
//        procDados = new procDados();
//        procDados.execute();
//    }
//
//    private void prepararComponenetes(){
//        recyclerView = (RecyclerView) findViewById(R.id.lstDocumentos);
//        txtNenhumRegistro = (TextView) findViewById(R.id.txtNenhumRegistroDocumento);
//        btnFloat = (FloatingActionButton) findViewById(R.id.btnFloatDocumento);
//        btnFloat.setOnClickListener(viewDocumento.this);
//        btnVoltar = (ImageButton)  findViewById(R.id.btnVoltarHomeDocumento);
//        btnVoltar.setOnClickListener(viewDocumento.this);
//    }
//
//    public void onClick(View view) {
//        Intent intent;
//        if(view.getId() == btnVoltar.getId()){
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("usuario", usuario);
//            intent = new Intent(this, viewHome.class);
//            intent.putExtras(bundle);
//            startActivity(intent);
//        }
//
//        if(view.getId() == btnFloat.getId()){
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("documento", new Documento());
//            bundle.putSerializable("usuario", usuario);
//            intent = new Intent(viewDocumento.this, viewDocumentoEdit.class);
//            intent.putExtras(bundle);
//            startActivity(intent);
//        }
//    }
//
//    private void popularLista() {
//        mAdapter = new adpDocumentos(viewDocumento.this, lstDocumentos);
//        mAdapter.setOnItemClickListener(new itfOnItemClickListener<Documento>(){
//
//            @Override
//            public void onItemClick(Documento item) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("documento", item);
//                bundle.putSerializable("usuario", usuario);
//                Intent intent = new Intent(viewDocumento.this, viewDocumentoDetail.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        }) ;
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(viewDocumento.this));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new dividerItemDecorator(viewDocumento.this, LinearLayoutManager.VERTICAL));
//        recyclerView.setAdapter(mAdapter);
//        recyclerView.setVisibility(View.VISIBLE);
//        txtNenhumRegistro.setVisibility(View.GONE);
//    }
//
//
//    private class procDados extends AsyncTask<Void, Void, List<Documento>> {
//
//        @Override
//        protected List<Documento> doInBackground(Void... voids) {
//            ctrlDocumento ctrlDocumento = new ctrlDocumento(new Documento());
//
//            try {
//                ctrlAutorPerfil ctrlAutorPerfil = new ctrlAutorPerfil(usuario);
//                lstDocumentos = ctrlDocumento.obterAllByAutor(usuario.getId());
//                autorPerfil = ctrlAutorPerfil.getAutorPerfil();
//            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
//                excPassaErro.getMessage();
//                lstDocumentos = new ArrayList<Documento>();
//            }
//            return lstDocumentos;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            progressDialog = ProgressDialog.show(viewDocumento.this, "Aguarde", "Carregando dados...");
//        }
//
//        @Override
//        protected void onPostExecute(List<Documento> documentos) {
//            progressDialog.dismiss();
//
//            if (documentos.isEmpty()){
//                recyclerView.setVisibility(View.GONE);
//                txtNenhumRegistro.setVisibility(View.VISIBLE);
//            }
//            else {
//                popularLista();
//            }
//        }
//    }
}


