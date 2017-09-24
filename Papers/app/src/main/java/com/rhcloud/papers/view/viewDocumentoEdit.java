package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlDocumento;
import com.rhcloud.papers.control.ctrlNotificacao;
import com.rhcloud.papers.control.ctrlTipoDocumento;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.model.entity.Documento;
import com.rhcloud.papers.model.entity.Notificacao;
import com.rhcloud.papers.model.entity.Pessoa;
import com.rhcloud.papers.model.entity.TipoDocumento;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.model.enumeration.Status;
import com.rhcloud.papers.model.transitorio.AutorPerfil;
import com.rhcloud.papers.view.adapters.adpTipoDocumento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodolfosantana on 06/09/17.
 */

public class viewDocumentoEdit extends AppCompatActivity implements View.OnClickListener {
    private Spinner txtTipo;
    private EditText txtTitulo, txtPalavrasChave;
    private adpTipoDocumento adpTipoDocumento;
    private Documento documento;
    private Usuario usuario;
    private AutorPerfil autorPerfil;
    private TipoDocumento tipoDocumento;
    private Button btnEnviarDocumento;
    private ImageButton btnVoltar, btnExcluirDocumento;
    private ProgressDialog progressDialog;
    private procDados procDados;
    private pouplarDados pouplarDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_documento_edit);
        prepararComponentes(getIntent().getExtras());
        pouplarDados = new pouplarDados();
        pouplarDados.execute();
    }

    private void prepararComponentes(Bundle bundle) {
        documento = (Documento) bundle.getSerializable("documento");
        usuario = (Usuario) bundle.getSerializable("usuario");
        autorPerfil = (AutorPerfil) bundle.getSerializable("autorPerfil");

        txtTipo = (Spinner) findViewById(R.id.txtTipo);
        txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        txtPalavrasChave = (EditText) findViewById(R.id.txtPalavrasChave);

        btnEnviarDocumento = (Button) findViewById(R.id.btnEnviarDocumento);
        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarDocumentoEdit);
        btnExcluirDocumento = (ImageButton) findViewById(R.id.btnExcluirDocumento);

        btnEnviarDocumento.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);
        btnExcluirDocumento.setOnClickListener(this);

        txtTitulo.setText(documento.getTitulo());

        if (documento.getId() == null) {
            btnExcluirDocumento.setVisibility(View.GONE);
        } else {
            txtPalavrasChave.setText(documento.getPalavrasChave());
            btnExcluirDocumento.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == btnEnviarDocumento.getId()) {
            if (validarDados()) {
                atualizarObjeto();
                procDados = new procDados(documento);
                procDados.execute();
            }
        }

        if (view.getId() == btnVoltar.getId()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            if (documento.getId()==null){
                intent = new Intent(viewDocumentoEdit.this, viewDocumento.class);
            }
            else{
                bundle.putSerializable("documento", documento);
                bundle.putSerializable("autorPerfil", autorPerfil);
                intent = new Intent(viewDocumentoEdit.this, viewDocumentoDetail.class);
            }

            intent.putExtras(bundle);
            startActivity(intent);
        }

        if (view.getId() == btnExcluirDocumento.getId()) {
            hlpDialog.getConfirmDialog(viewDocumentoEdit.this, "Atenção", "Confirma a exclusão do Artigo?", "Sim", "Não", false, new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    if (value) {
                        ctrlDocumento ctrlDocumento = new ctrlDocumento(documento);
                        final String msg = ctrlDocumento.remover();

                        hlpDialog.getAlertDialog(viewDocumentoEdit.this, "Atenção", msg, "Ok", new itfDialogGeneric() {
                            @Override
                            public void onButtonAction(boolean value) throws excPassaErro {
                                if (msg.trim().equals("Artigo removido do sistema com sucesso")){
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("usuario", usuario);
                                    Intent intent = new Intent(viewDocumentoEdit.this, viewDocumento.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);                               }
                                else{
                                    txtTitulo.requestFocus();
                                }
                            }
                        });
                    } else {
                        txtTitulo.requestFocus();
                    }
                }
            });
        }
    }

    private void atualizarObjeto() {
        documento.setTitulo(txtTitulo.getText().toString());
        documento.setPalavrasChave(txtPalavrasChave.getText().toString());
        tipoDocumento = (TipoDocumento) txtTipo.getSelectedItem();
        documento.setTipoDocumento(tipoDocumento);
        Pessoa pessoa = new Pessoa();
        pessoa.setId(usuario.getPessoa().getId());
        documento.setPessoa(pessoa);
    }

    private boolean validarDados() {
        if (txtTipo.getSelectedItem() == null) {
            hlpDialog.getAlertDialog(this, "Atenção", "O Tipo do Artigo deve ser informado", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtTitulo.requestFocus();
                }
            });
            return false;
        }

        if (txtTitulo.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Atenção", "O Titulo do Artigo deve ser informado", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtTitulo.requestFocus();
                }
            });
            return false;
        }

        if (txtPalavrasChave.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Atenção", "Ao menos uma palavra-chave de ser informada", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtPalavrasChave.requestFocus();
                }
            });
            return false;
        }

        return true;
    }


    private class procDados extends AsyncTask<Void, Void, String> {
        private Documento documento;

        public procDados(Documento documento) {
            this.documento = documento;
        }

        @Override
        protected String doInBackground(Void... voids) {
            ctrlDocumento ctrlDocumento = new ctrlDocumento(documento);
            String msg = "";
            try {
                return ctrlDocumento.atualizar();
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                msg = excPassaErro.getMessage();
            }
            return msg;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewDocumentoEdit.this, "Aguarde", "Enviando solicitação...");
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            final String finalResult = result;
            hlpDialog.getAlertDialog(viewDocumentoEdit.this, "Atenção", result, "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    if (finalResult.trim().equals("Artigo registrado com sucesso")) {
                        Intent intent;
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("usuario", usuario);
                        if (documento.getId()==null){
                            intent = new Intent(viewDocumentoEdit.this, viewDocumento.class);
                        }
                        else{
                            bundle.putSerializable("documento", documento);
                            bundle.putSerializable("autorPerfil", autorPerfil);
                            intent = new Intent(viewDocumentoEdit.this, viewDocumentoDetail.class);
                        }
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        txtTitulo.requestFocus();
                    }
                }
            });
        }
    }

    private class pouplarDados extends AsyncTask<Void, Void, List<TipoDocumento>> {

        @Override
        protected List<TipoDocumento> doInBackground(Void... voids) {
            ctrlTipoDocumento ctrlTipoDocumento = new ctrlTipoDocumento(new TipoDocumento());
            List<TipoDocumento> tipoDocumentoList = new ArrayList<TipoDocumento>();

            try {
                return ctrlTipoDocumento.obterAll();
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                String msg = excPassaErro.getMessage();
                hlpDialog.getAlertDialog(viewDocumentoEdit.this, "Atenção", msg, "Ok", new itfDialogGeneric() {
                    @Override
                    public void onButtonAction(boolean value) throws com.rhcloud.papers.excecoes.excPassaErro {
                    }
                });

            }
            return tipoDocumentoList;

        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewDocumentoEdit.this, "Aguarde", "Carregando dados...");
        }

        @Override
        protected void onPostExecute(List<TipoDocumento> result) {
            progressDialog.dismiss();

            adpTipoDocumento = new adpTipoDocumento(viewDocumentoEdit.this, android.R.layout.simple_spinner_dropdown_item, result);
            txtTipo.setAdapter(adpTipoDocumento);
            for (int i=0; i<result.size(); i++){
                if (result.get(i).getId().equals(documento.getTipoDocumento().getId())){
                    txtTipo.setSelection(i);
                }
            }
            adpTipoDocumento.notifyDataSetChanged();
            txtTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    tipoDocumento = (TipoDocumento) adapterView.getItemAtPosition(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }
    }
}


