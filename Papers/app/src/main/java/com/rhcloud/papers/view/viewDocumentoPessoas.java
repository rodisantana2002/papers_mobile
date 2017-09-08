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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
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

public class viewDocumentoPessoas extends AppCompatActivity implements View.OnClickListener{
    private adpDocumentoParticipantes mAdapterParticipantes;
    private TextView txtNenhumRegistro;
    private Documento documento;
    private Usuario usuario;
    private ImageButton btnVoltar;
    private FloatingActionButton btnNovoParticipante;
    private ProgressDialog progressDialog;
    private List<Pessoa> lstParticipantes;

    private RecyclerView recyclerViewParticipantes;
    private RecyclerView.LayoutManager layoutManagerParticipantes;

    private procDados procDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_documento_pessoas);
        prepararComponentes(getIntent().getExtras());

        lstParticipantes = new ArrayList<Pessoa>();
        procDados = new procDados();
        procDados.execute();
    }

    private void prepararComponentes(Bundle bundle) {
        documento = (Documento) bundle.getSerializable("documento");
        usuario = (Usuario) bundle.getSerializable("usuario");

        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarDocumentoPessoas);
        btnVoltar.setOnClickListener(this);

        btnNovoParticipante = (FloatingActionButton) findViewById(R.id.btnFloatDocumentoPessoas);
        btnNovoParticipante.setOnClickListener(this);

        documento = (Documento) bundle.getSerializable("documento");
        usuario = (Usuario) bundle.getSerializable("usuario");

        txtNenhumRegistro = (TextView) findViewById(R.id.txtNenhumRegistroDocumentoPessoas);
        recyclerViewParticipantes = (RecyclerView) findViewById(R.id.lstParticipantes);
    }

    private void prepararLista() {
        mAdapterParticipantes = new adpDocumentoParticipantes(viewDocumentoPessoas.this, lstParticipantes);
        mAdapterParticipantes.setOnItemClickListener(new itfOnItemClickListener<Pessoa>(){
            @Override
            public void onItemClick(Pessoa item) {}}) ;

        recyclerViewParticipantes.setLayoutManager(new LinearLayoutManager(viewDocumentoPessoas.this));
        recyclerViewParticipantes.setItemAnimator(new DefaultItemAnimator());
        recyclerViewParticipantes.addItemDecoration(new dividerItemDecorator(viewDocumentoPessoas.this, LinearLayoutManager.VERTICAL));
        recyclerViewParticipantes.setAdapter(mAdapterParticipantes);
        recyclerViewParticipantes.setVisibility(View.VISIBLE);
    }
    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == btnVoltar.getId()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            bundle.putSerializable("documento", documento);
            intent = new Intent(viewDocumentoPessoas.this, viewDocumentoDetail.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        if (view.getId()==btnNovoParticipante.getId()){
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            bundle.putSerializable("documento", documento);
            bundle.putSerializable("participante", new DocumentosPessoas());
            intent = new Intent(viewDocumentoPessoas.this, viewDocumentoPessoasEdit.class);
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

            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                excPassaErro.getMessage();
                lstParticipantes = new ArrayList<Pessoa>();
            }
            return lstParticipantes;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewDocumentoPessoas.this, "Aguarde", "Carregando dados...");
        }

        @Override
        protected void onPostExecute(List<Pessoa> destinos) {
            progressDialog.dismiss();
            if (lstParticipantes.isEmpty()){
                recyclerViewParticipantes.setVisibility(View.GONE);
                txtNenhumRegistro.setVisibility(View.VISIBLE);
            }
            else {
                prepararLista();
            }
        }
    }

}


