package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rhcloud.papers.Principal;
import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlUsuario;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.helpers.generic.hlpValidaDados;
import com.rhcloud.papers.model.entity.Usuario;

public class viewCadastrarUsuario extends AppCompatActivity implements  View.OnClickListener {
    private EditText txtPrimeiroNome, txtSegundoNome, txtEmailUsuario, txtCrieSenha, txtRepitaSenha;
    private Usuario usuario;
    private Button btnEnviar;
    private ProgressDialog progressDialog;
    private procDados procDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_cadastrar_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prepararComponentes();
    }

    private void prepararComponentes() {
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(this);

        txtPrimeiroNome = (EditText) findViewById(R.id.txtPrimeiroNome);
        txtSegundoNome = (EditText) findViewById(R.id.txtSegundoNome);
        txtEmailUsuario = (EditText) findViewById(R.id.txtEmailUsuario);
        txtCrieSenha = (EditText) findViewById(R.id.txtCrieSenha);
        txtRepitaSenha = (EditText) findViewById(R.id.txtRepitaSenha);
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
    }

    private void atualizarObjeto() {
        usuario = new Usuario();
        usuario.getPessoa().setPrimeiroNome(txtPrimeiroNome.getText().toString());
        usuario.getPessoa().setSegundoNome(txtSegundoNome.getText().toString());
        usuario.getPessoa().setEmail(txtEmailUsuario.getText().toString());
        usuario.setSenha(txtCrieSenha.getText().toString());
    }

    private boolean validarDados() {
        if (txtPrimeiroNome.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Atenção", "O Primeiro Nome deve ser informado", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtPrimeiroNome.requestFocus();
                }
            });
            return false;
        }

        if (txtSegundoNome.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Atenção", "O Segundo Nome deve ser informado", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtSegundoNome.requestFocus();
                }
            });
            return false;
        }

        if (txtEmailUsuario.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Atenção", "O Email deve ser informado", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtEmailUsuario.requestFocus();
                }
            });
            return false;
        }

        if (!hlpValidaDados.isValidEmail(txtEmailUsuario.getText().toString())) {
            hlpDialog.getAlertDialog(this, "Atenção", "Email com formato inválido", "Ok", new itfDialogGeneric() {

                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtEmailUsuario.requestFocus();
                }
            });
            return false;
        }

        if (txtCrieSenha.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Atenção", "A Senha deve ser informada", "Ok", new itfDialogGeneric() {

                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtCrieSenha.requestFocus();
                }
            });
            return false;
        }

        if(!hlpValidaDados.isSenhaForte(txtCrieSenha.getText().toString())){
            hlpDialog.getAlertDialog(this, "Atenção", "A Senha não atende os requisitos de segurança", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtCrieSenha.requestFocus();
                }
            });
            return false;
        }

        if (txtRepitaSenha.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Atenção", "A Senha deve ser informada", "Ok", new itfDialogGeneric() {

                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtRepitaSenha.requestFocus();
                }
            });
            return false;
        }

        if(!hlpValidaDados.isSenhaForte(txtRepitaSenha.getText().toString())){
            hlpDialog.getAlertDialog(this, "Atenção", "A Senha não atende os requisitos de segurança", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtRepitaSenha.requestFocus();
                }
            });
            return false;
        }

        if(!txtCrieSenha.getText().toString().equals(txtRepitaSenha.getText().toString())){
            hlpDialog.getAlertDialog(this, "Atenção", "As Senhas informadas devem ser identicas", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtRepitaSenha.requestFocus();
                }
            });
            return false;
        }

       return true;
    }

    private class procDados extends AsyncTask<Void, Void, String>{
        private Usuario usuario;

        public procDados(Usuario usuario){
            this.usuario = usuario;
        }

        @Override
        protected String doInBackground(Void... voids) {
            ctrlUsuario ctrlUsuario = new ctrlUsuario(usuario);
            String msg="";
            try {
                return ctrlUsuario.criar();
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                msg = excPassaErro.getMessage();
            }
            return msg;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewCadastrarUsuario.this, "Aguarde", "Enviando solicitação...");
        }

        @Override
        protected void onPostExecute(final String result) {
            progressDialog.dismiss();
                hlpDialog.getAlertDialog(viewCadastrarUsuario.this, "Atenção", result, "Ok", new itfDialogGeneric() {
                    @Override
                    public void onButtonAction(boolean value) throws excPassaErro {
                        if (result.trim().equals("Usuário registrado com sucesso")){
                            Intent intent = new Intent(viewCadastrarUsuario.this, Principal.class);
                            startActivity(intent);
                        }
                        else{
                            txtPrimeiroNome.requestFocus();
                        }
                    }
                });

        }
    }
}
