package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlPessoa;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.helpers.generic.hlpDialog;
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
    private ImageButton btnVoltar, btnPesquisar, btnEncerrarPesquisa;
    private Pessoa pessoa;
    private Usuario usuario;
    private ProgressDialog progressDialog;
    private procDados procDados;
    private TextView txtNenhumRegistro;
    private FloatingActionButton btnFloat;
    private EditText txtPesquisa;
    private GridLayout gridPesquisar;


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
        txtPesquisa = (EditText) findViewById(R.id.txtPesquisa);

        btnFloat = (FloatingActionButton) findViewById(R.id.btnFloatAutor);
        btnFloat.setOnClickListener(viewAutor.this);

        btnVoltar = (ImageButton)  findViewById(R.id.btnVoltarHomeAutor);
        btnVoltar.setOnClickListener(viewAutor.this);

        btnPesquisar = (ImageButton) findViewById(R.id.btnPesquisar);
        btnPesquisar.setOnClickListener(viewAutor.this);

        btnEncerrarPesquisa = (ImageButton) findViewById(R.id.btnEncerrarPesquisa);
        btnEncerrarPesquisa.setOnClickListener(viewAutor.this);

        gridPesquisar = (GridLayout) findViewById(R.id.gridPesquisar);

        txtPesquisa.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH){
                    if(!txtPesquisa.getText().toString().trim().isEmpty()){
                        procPesquisa procPesquisa = new procPesquisa(txtPesquisa.getText().toString());
                        procPesquisa.execute();
                    }
                    esconderTeclado(textView);
                    txtPesquisa.setText("");
                    gridPesquisar.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });
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

        if(view.getId() == btnPesquisar.getId()){
            txtPesquisa.requestFocus();
            gridPesquisar.setVisibility(View.VISIBLE);
        }

        if (view.getId() == btnEncerrarPesquisa.getId()){
            esconderTeclado(view);
            txtPesquisa.setText("");
            gridPesquisar.setVisibility(View.GONE);
        }
    }


    private void popularLista(List<Pessoa> listAutor) {
        mAdapter = new adpAutores(viewAutor.this, listAutor);
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
        recyclerView.addItemDecoration(new dividerItemDecorator(viewAutor.this, LinearLayoutManager.VERTICAL,0));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        txtNenhumRegistro.setVisibility(View.GONE);
    }

    public void esconderTeclado(View v){
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
    }

    private class procDados extends AsyncTask<Void, Void, List<Pessoa>> {

        @Override
        protected List<Pessoa> doInBackground(Void... voids) {
            ctrlPessoa ctrlPessoa = new ctrlPessoa(new Pessoa());
            try {
                lstAutores = ctrlPessoa.obterAll();
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                String msg = excPassaErro.getMessage();
                hlpDialog.getAlertDialog(viewAutor.this, "Atenção", msg, "Ok", new itfDialogGeneric() {
                    @Override
                    public void onButtonAction(boolean value) throws com.rhcloud.papers.excecoes.excPassaErro {
                    }
                });
                lstAutores = new ArrayList<Pessoa>();
            }
            return lstAutores;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewAutor.this, "Aguarde", "Carregando dados...");
        }

        @Override
        protected void onPostExecute(List<Pessoa> autores) {
            progressDialog.dismiss();

            if (autores.isEmpty()){
                recyclerView.setVisibility(View.GONE);
                txtNenhumRegistro.setVisibility(View.VISIBLE);
            }
            else {
                popularLista(autores);
            }
        }
    }

    private class procPesquisa extends AsyncTask<Void, Void, List<Pessoa>> {
        private String strNome;

        public procPesquisa(String strNome){
            this.strNome = strNome;
        }

        @Override
        protected List<Pessoa> doInBackground(Void... voids) {
            ctrlPessoa ctrlPessoa = new ctrlPessoa(new Pessoa());
            try {
                lstAutores = ctrlPessoa.obterAllByNome(strNome);
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                String msg = excPassaErro.getMessage();
                hlpDialog.getAlertDialog(viewAutor.this, "Atenção", msg, "Ok", new itfDialogGeneric() {
                    @Override
                    public void onButtonAction(boolean value) throws com.rhcloud.papers.excecoes.excPassaErro {
                    }
                });
                lstAutores = new ArrayList<Pessoa>();
            }
            return lstAutores;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewAutor.this, "Aguarde", "Carregando dados...");
        }

        @Override
        protected void onPostExecute(List<Pessoa> autores) {
            progressDialog.dismiss();

            if (autores.isEmpty()){
                recyclerView.setVisibility(View.GONE);
                txtNenhumRegistro.setVisibility(View.VISIBLE);
            }
            else {
                popularLista(autores);
            }
        }
    }
}

