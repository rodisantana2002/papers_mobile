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
import android.widget.ImageButton;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlDestino;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.model.entity.Destino;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.view.adapters.adpRepositorios;
import com.rhcloud.papers.view.decorator.dividerItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class viewRepositorio extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Destino> lstDestinos;
    private adpRepositorios mAdapter;
    private ImageButton btnVoltar;
    private Destino destino;
    private Usuario usuario;
    private ProgressDialog progressDialog;
    private procDados procDados;
    private TextView txtNenhumRegistro;
    private FloatingActionButton btnFloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_repositorio);
        popularLista(getIntent().getExtras());
    }

    private void popularLista(Bundle bundle) {
        lstDestinos = new ArrayList<Destino>();

        if (bundle!=null){
            usuario = (Usuario) bundle.getSerializable("usuario");
        }

        prepararComponenetes();
        procDados = new procDados();
        procDados.execute();
    }

    private void prepararComponenetes(){
        recyclerView = (RecyclerView) findViewById(R.id.lstRepositorios);

        txtNenhumRegistro = (TextView) findViewById(R.id.txtNenhumRegistroRepositorio);
        btnFloat = (FloatingActionButton) findViewById(R.id.btnFloatDestino);
        btnFloat.setOnClickListener(viewRepositorio.this);
        btnVoltar = (ImageButton)  findViewById(R.id.btnVoltarHomeRepositorio);
        btnVoltar.setOnClickListener(viewRepositorio.this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btnVoltar.getId()){
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);

            Intent intent = new Intent(this, viewHome.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        if(view.getId() == btnFloat.getId()){
            Bundle bundle = new Bundle();
            bundle.putSerializable("destino", new Destino());
            Intent intent = new Intent(viewRepositorio.this, viewRepositorioDetail.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private class procDados extends AsyncTask<Void, Void, List<Destino>> {

        @Override
        protected List<Destino> doInBackground(Void... voids) {
            ctrlDestino ctrlDestino = new ctrlDestino(new Destino());
            try {
                lstDestinos = ctrlDestino.obterAll();
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                excPassaErro.getMessage();
                lstDestinos = new ArrayList<Destino>();
            }
            return lstDestinos;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewRepositorio.this, "Aguarde", "Carregando configurações...");
        }

        @Override
        protected void onPostExecute(List<Destino> destinos) {
            progressDialog.dismiss();

            if (destinos.isEmpty()){
                recyclerView.setVisibility(View.GONE);
                txtNenhumRegistro.setVisibility(View.VISIBLE);
            }
            else {
                popularLista();
            }
        }
    }

    private void popularLista() {
        mAdapter = new adpRepositorios(viewRepositorio.this, lstDestinos);
        mAdapter.setOnItemClickListener(new itfOnItemClickListener<Destino>(){

            @Override
            public void onItemClick(Destino item) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("destino", item);
                Intent intent = new Intent(viewRepositorio.this, viewRepositorioDetail.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }) ;

        recyclerView.setLayoutManager(new LinearLayoutManager(viewRepositorio.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new dividerItemDecorator(viewRepositorio.this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        txtNenhumRegistro.setVisibility(View.GONE);
    }
}
