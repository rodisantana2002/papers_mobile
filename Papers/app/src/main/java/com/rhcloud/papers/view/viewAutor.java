package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlPessoa;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.model.entity.Pessoa;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.view.adapters.adpAutores;
import com.rhcloud.papers.view.decorator.dividerItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class viewAutor extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Pessoa> lstAutores;
    private adpAutores mAdapter;
    private ImageButton btnVoltar;
    private Pessoa pessoa;
    private Usuario usuario;
    private ProgressDialog progressDialog;
    private procDados procDados;
    private TextView txtNenhumRegistro;
    private FloatingActionButton btnFloat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_autor);

        popularLista(getIntent().getExtras());
    }

    private void popularLista(Bundle bundle) {
        lstAutores = new ArrayList<Pessoa>();

        if (bundle!=null){
            usuario = (Usuario) bundle.getSerializable("usuario");
        }

        prepararComponenetes();
        procDados = new procDados();
        procDados.execute();
    }

    private void prepararComponenetes(){
        recyclerView = (RecyclerView) findViewById(R.id.lstAutores);

        txtNenhumRegistro = (TextView) findViewById(R.id.txtNenhumRegistroAutor);
        btnFloat = (FloatingActionButton) findViewById(R.id.btnFloatAutor);
        btnFloat.setOnClickListener(viewAutor.this);
        btnVoltar = (ImageButton)  findViewById(R.id.btnVoltarHomeAutor);
        btnVoltar.setOnClickListener(viewAutor.this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if(view.getId() == btnVoltar.getId()){
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);

            intent = new Intent(this, viewHome.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        if(view.getId() == btnFloat.getId()){
            Bundle bundle = new Bundle();
            bundle.putSerializable("autor", new Pessoa());
            intent = new Intent(viewAutor.this, viewAutorEdit.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }

    private void popularLista() {
        mAdapter = new adpAutores(viewAutor.this, lstAutores);
        mAdapter.setOnItemClickListener(new itfOnItemClickListener<Pessoa>(){

            @Override
            public void onItemClick(Pessoa item) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("autor", item);
                Intent intent = new Intent(viewAutor.this, viewAutorDetail.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }) ;

        recyclerView.setLayoutManager(new LinearLayoutManager(viewAutor.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new dividerItemDecorator(viewAutor.this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        txtNenhumRegistro.setVisibility(View.GONE);
    }

    private class procDados extends AsyncTask<Void, Void, List<Pessoa>> {

        @Override
        protected List<Pessoa> doInBackground(Void... voids) {
            ctrlPessoa ctrlPessoa = new ctrlPessoa(new Pessoa());
            try {
                lstAutores = ctrlPessoa.obterAll();
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                excPassaErro.getMessage();
                lstAutores = new ArrayList<Pessoa>();
            }
            return lstAutores;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewAutor.this, "Aguarde", "Carregando dados...");
        }

        @Override
        protected void onPostExecute(List<Pessoa> destinos) {
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
}

