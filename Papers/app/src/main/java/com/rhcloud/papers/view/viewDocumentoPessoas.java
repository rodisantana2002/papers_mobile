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
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.model.entity.Documento;
import com.rhcloud.papers.model.entity.DocumentosPessoas;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.model.transitorio.AutorPerfil;
import com.rhcloud.papers.view.adapters.adpParticipantes;
import com.rhcloud.papers.view.decorator.dividerItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class viewDocumentoPessoas extends AppCompatActivity implements View.OnClickListener{
    private adpParticipantes mAdapterParticipantes;
    private TextView txtNenhumRegistro;
    private Documento documento;
    private Usuario usuario;
    private AutorPerfil autorPerfil;
    private ImageButton btnVoltar;
    private FloatingActionButton btnNovoParticipante;
    private ProgressDialog progressDialog;
    private List<DocumentosPessoas> lstParticipantes;

    private RecyclerView recyclerViewParticipantes;
    private RecyclerView.LayoutManager layoutManagerParticipantes;

    private procDados procDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_documento_pessoas);
        prepararComponentes(getIntent().getExtras());

        lstParticipantes = new ArrayList<DocumentosPessoas>();
        procDados = new procDados();
        procDados.execute();
    }

    private void prepararComponentes(Bundle bundle) {
        documento = (Documento) bundle.getSerializable("documento");
        usuario = (Usuario) bundle.getSerializable("usuario");
        autorPerfil = (AutorPerfil) bundle.getSerializable("autorPerfil");

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
        mAdapterParticipantes = new adpParticipantes(viewDocumentoPessoas.this, lstParticipantes);
        mAdapterParticipantes.setOnItemClickListener(new itfOnItemClickListener<DocumentosPessoas>(){
            @Override
            public void onItemClick(DocumentosPessoas item) {
                Intent intent;
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario", usuario);
                bundle.putSerializable("documento", documento);
                bundle.putSerializable("participante", item);
                bundle.putSerializable("autorPerfil", autorPerfil);

                intent = new Intent(viewDocumentoPessoas.this, viewDocumentoPessoasEdit.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }}) ;

        recyclerViewParticipantes.setLayoutManager(new LinearLayoutManager(viewDocumentoPessoas.this));
        recyclerViewParticipantes.setItemAnimator(new DefaultItemAnimator());
        recyclerViewParticipantes.addItemDecoration(new dividerItemDecorator(viewDocumentoPessoas.this, LinearLayoutManager.VERTICAL,0));
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
            bundle.putSerializable("autorPerfil", autorPerfil);

            intent = new Intent(viewDocumentoPessoas.this, viewDocumentoDetail.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        if (view.getId()==btnNovoParticipante.getId()){
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            bundle.putSerializable("documento", documento);
            bundle.putSerializable("participante", new DocumentosPessoas());
            bundle.putSerializable("autorPerfil", autorPerfil);

            intent = new Intent(viewDocumentoPessoas.this, viewDocumentoPessoasEdit.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private class procDados extends AsyncTask<Void, Void, List<DocumentosPessoas>> {
        @Override
        protected List<DocumentosPessoas> doInBackground(Void... voids) {
            ctrlDocumentoPessoas ctrlDocumentoPessoas  = new ctrlDocumentoPessoas(new DocumentosPessoas());
            try {
                lstParticipantes = ctrlDocumentoPessoas.obterAllByDocumento(documento.getId());
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                String msg = excPassaErro.getMessage();
                hlpDialog.getAlertDialog(viewDocumentoPessoas.this, "Atenção", msg, "Ok", new itfDialogGeneric() {
                    @Override
                    public void onButtonAction(boolean value) throws com.rhcloud.papers.excecoes.excPassaErro {
                    }
                });
                lstParticipantes = new ArrayList<DocumentosPessoas>();
            }
            return lstParticipantes;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewDocumentoPessoas.this, "Aguarde", "Carregando dados...");
        }

        @Override
        protected void onPostExecute(List<DocumentosPessoas> destinos) {
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


