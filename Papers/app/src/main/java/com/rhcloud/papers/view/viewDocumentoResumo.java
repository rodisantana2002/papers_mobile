package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlDocumento;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.model.entity.Documento;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.model.transitorio.AutorPerfil;

public class viewDocumentoResumo extends AppCompatActivity implements View.OnClickListener {
    private EditText txtResumoArtigo;
    private Usuario usuario;
    private Documento documento;
    private Button btnEnviar;
    private ImageButton btnVoltar;
    private ProgressDialog progressDialog;
    private procDados procDados;
    private AutorPerfil autorPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_documento_resumo);
        prepararComponentes(getIntent().getExtras());
    }

    private void prepararComponentes(Bundle bundle) {
        btnEnviar = (Button) findViewById(R.id.btnEnviarResumoArtigo);
        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarDocumentoResumo);
        txtResumoArtigo = (EditText) findViewById(R.id.txtResumoArtigo);
        btnEnviar.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);

        usuario = (Usuario) bundle.getSerializable("usuario");
        documento = (Documento) bundle.getSerializable("documento");
        autorPerfil = (AutorPerfil) bundle.getSerializable("autorPerfil");

        if (documento.getResumo()!=null){
            txtResumoArtigo.setText(documento.getResumo().toString());
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == btnEnviar.getId()) {
            if (validarDados()) {
                atualizarObjeto();
                procDados = new procDados(usuario);
                procDados.execute();
            }
        }

        if (view.getId() == btnVoltar.getId()){
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            bundle.putSerializable("documento", documento);
            bundle.putSerializable("autorPerfil", autorPerfil);
            intent = new Intent(viewDocumentoResumo.this, viewDocumentoDetail.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private void atualizarObjeto() {
        documento.setResumo(txtResumoArtigo.getText().toString());
    }

    private boolean validarDados() {
        if (txtResumoArtigo.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Atenção", "Resumo do artigo deve ser informado", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtResumoArtigo.requestFocus();
                }
            });
            return false;
        }
        if (txtResumoArtigo.getText().length()>500) {
            hlpDialog.getAlertDialog(this, "Atenção", "O resumo informado excede o tamanho máximo de 500 caracteres", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtResumoArtigo.requestFocus();
                }
            });
            return false;
        }
        return true;
    }

    private class procDados extends AsyncTask<Void, Void, String> {
        private Usuario usuario;

        public procDados(Usuario usuario){
            this.usuario = usuario;
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
            progressDialog = ProgressDialog.show(viewDocumentoResumo.this, "Aguarde", "Enviando solicitação...");
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (result.trim().equals("Artigo registrado com sucesso")){
                result = "Resumo registrado com sucesso";
            }
            final String finalResult = result;
            hlpDialog.getAlertDialog(viewDocumentoResumo.this, "Atenção", result, "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    if (finalResult.trim().equals("Resumo registrado com sucesso")){
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("usuario", usuario);
                        bundle.putSerializable("documento", documento);
                        bundle.putSerializable("autorPerfil", autorPerfil);
                        Intent intent = new Intent(viewDocumentoResumo.this, viewDocumentoDetail.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else{
                        txtResumoArtigo.requestFocus();
                    }
                }
            });
        }
    }
}
