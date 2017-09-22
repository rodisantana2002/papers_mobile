package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rhcloud.papers.R;
import com.rhcloud.papers.Principal;
import com.rhcloud.papers.view.viewRecuperarSenha;
import com.rhcloud.papers.control.ctrlAutentication;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.helpers.generic.hlpValidaDados;
import com.rhcloud.papers.model.entity.Usuario;

public class viewRecuperarSenha extends AppCompatActivity implements View.OnClickListener {
    private ctrlAutentication ctrlAutentication;
    private Button btnEnviar;
    private EditText txtEmail;
    private String msg;
    private ProgressDialog progressDialog;
    private procDados procDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prepararComponentes();
    }

    private void prepararComponentes() {
        btnEnviar = (Button) findViewById(R.id.btnEnviarSenha);
        btnEnviar.setOnClickListener(this);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnEnviar.getId()) {
            if (validarDados()) {
                Usuario usuario = new Usuario();
                usuario.getPessoa().setEmail(txtEmail.getText().toString());
                procDados = new procDados(usuario);
                procDados.execute();
            }
        }
    }

    private boolean validarDados() {
        if (txtEmail.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Atenção", "O Email deve ser informado", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtEmail.requestFocus();
                }
            });
            return false;
        }

        if (!hlpValidaDados.isValidEmail(txtEmail.getText().toString())) {
            hlpDialog.getAlertDialog(this, "Atenção", "Email com formato inválido", "Ok", new itfDialogGeneric() {

                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtEmail.requestFocus();
                }
            });
            return false;
        }

        return true;
    }

    private class procDados extends AsyncTask<Void, Void, String> {
        private Usuario usuario;

        public procDados(Usuario usuario) {
            this.usuario = usuario;
        }

        @Override
        protected String doInBackground(Void... voids) {
            ctrlAutentication ctrlAutentication = new ctrlAutentication(usuario);
            String msg = "";
            try {
                return ctrlAutentication.recuperarAcesso();
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                msg = excPassaErro.getMessage();
            }
            return msg;

        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewRecuperarSenha.this, "Aguarde", "Enviando solicitação...");
        }

        @Override
        protected void onPostExecute(final String result) {
            progressDialog.dismiss();
            hlpDialog.getAlertDialog(viewRecuperarSenha.this, "Atenção", result, "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    if (result.trim().equals("O email foi enviado com sucesso")) {
                        Intent intent = new Intent(viewRecuperarSenha.this, Principal.class);
                        startActivity(intent);
                    } else {
                        txtEmail.requestFocus();
                    }
                }
            });
        }
    }
}

