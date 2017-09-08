package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlDocumentoPessoas;
import com.rhcloud.papers.control.ctrlPessoa;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.model.entity.Documento;
import com.rhcloud.papers.model.entity.DocumentosPessoas;
import com.rhcloud.papers.model.entity.Pessoa;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.view.adapters.adpPessoas;

import java.util.ArrayList;
import java.util.List;

public class viewDocumentoPessoasEdit extends AppCompatActivity implements View.OnClickListener{
    private Spinner txtParticipante;
    private adpPessoas adpPessoas;
    private Documento documento;
    private Usuario usuario;
    private Pessoa pessoa;
    private DocumentosPessoas documentosPessoas;
    private Button btnEnviar;
    private ImageButton btnVoltar,  btnExcluir;
    private ProgressDialog progressDialog;
    private procDados procDados;
    private pouplarDados pouplarDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_documento_pessoas_edit);
        prepararComponentes(getIntent().getExtras());
        pouplarDados = new pouplarDados();
        pouplarDados.execute();
    }

    private void prepararComponentes(Bundle bundle) {
        documento = (Documento) bundle.getSerializable("documento");
        usuario = (Usuario) bundle.getSerializable("usuario");
        documentosPessoas = (DocumentosPessoas) bundle.getSerializable("participante");

        txtParticipante = (Spinner) findViewById(R.id.txtTipo);

        btnEnviar = (Button) findViewById(R.id.btnEnviarParticipanteDocumento);
        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarDocumentoPessoasEdit);
        btnExcluir = (ImageButton) findViewById(R.id.btnExcluirParticipanteDocumento);

        btnEnviar.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);
        btnExcluir.setOnClickListener(this);


        if (documentosPessoas.getId() == null) {
            btnExcluir.setVisibility(View.GONE);
        } else {
            txtParticipante.setSelection(documentosPessoas.getPessoa().getId());
            btnExcluir.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == btnEnviar.getId()) {
            if (validarDados()) {
                atualizarObjeto();
                procDados = new procDados(documentosPessoas);
                procDados.execute();
            }
        }

        if (view.getId() == btnVoltar.getId()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            bundle.putSerializable("documento", documento);
            intent = new Intent(viewDocumentoPessoasEdit.this, viewDocumentoPessoas.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

//        if (view.getId() == btnExcluirDocumento.getId()) {
//            hlpDialog.getConfirmDialog(viewDocumentoEdit.this, "Atenção", "Confirma a exclusão do Artigo?", "Sim", "Não", false, new itfDialogGeneric() {
//                @Override
//                public void onButtonAction(boolean value) throws excPassaErro {
//                    if (value) {
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("usuario", usuario);
//                        ctrlDocumento ctrlDocumento = new ctrlDocumento(documento);
//                        ctrlDocumento.remover();
//                        Intent intent = new Intent(viewDocumentoEdit.this, viewDocumento.class);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                    } else {
//                        txtTitulo.requestFocus();
//                    }
//                }
//            });
//        }
    }

    private void atualizarObjeto() {
        Pessoa pessoa = (Pessoa) txtParticipante.getSelectedItem();
        documentosPessoas.setPessoa(pessoa);
        documentosPessoas.setDocumento(documento);
    }

    private boolean validarDados() {
        if (txtParticipante.getSelectedItem() == null) {
            hlpDialog.getAlertDialog(this, "Atenção", "O Participante deve ser informado", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtParticipante.requestFocus();
                }
            });
            return false;
        }
        return true;
    }

    private class procDados extends AsyncTask<Void, Void, String> {
        private DocumentosPessoas documentosPessoas;

        public procDados(DocumentosPessoas documentosPessoas) {
            this.documentosPessoas = documentosPessoas;
        }

        @Override
        protected String doInBackground(Void... voids) {
            ctrlDocumentoPessoas ctrlDocumentoPessoas = new ctrlDocumentoPessoas(documentosPessoas);
            String msg = "";
            try {
                return ctrlDocumentoPessoas.atualizar();
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                msg = excPassaErro.getMessage();
            }
            return msg;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewDocumentoPessoasEdit.this, "Aguarde", "Enviando solicitação...");
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            final String finalResult = result;
            hlpDialog.getAlertDialog(viewDocumentoPessoasEdit.this, "Atenção", result, "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    if (finalResult.trim().equals("Participante registrado com sucesso")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("usuario", usuario);
                        bundle.putSerializable("documento", documento);
                        Intent intent = new Intent(viewDocumentoPessoasEdit.this, viewDocumento.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        txtParticipante.requestFocus();
                    }
                }
            });
        }
    }



    private class pouplarDados extends AsyncTask<Void, Void, List<Pessoa>> {
        @Override
        protected List<Pessoa> doInBackground(Void... voids) {
            ctrlPessoa ctrlPessoa = new ctrlPessoa(new Pessoa());
            List<Pessoa> pessoaList = new ArrayList<Pessoa>();

            try {
                return ctrlPessoa.obterAll();
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                excPassaErro.printStackTrace();
            }
            return pessoaList;

        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewDocumentoPessoasEdit.this, "Aguarde", "Enviando carregando dados...");
        }

        @Override
        protected void onPostExecute(List<Pessoa> result) {
            progressDialog.dismiss();

            adpPessoas = new adpPessoas(viewDocumentoPessoasEdit.this, android.R.layout.simple_spinner_dropdown_item, result);
            txtParticipante.setAdapter(adpPessoas);
            txtParticipante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    pessoa = (Pessoa) adapterView.getItemAtPosition(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }

    }
}

