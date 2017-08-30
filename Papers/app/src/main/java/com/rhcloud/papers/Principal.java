package com.rhcloud.papers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rhcloud.papers.control.ctrlAutentication;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpConstants;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.helpers.generic.hlpValidaDados;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.view.viewCadastrarUsuario;
import com.rhcloud.papers.view.viewHome;
import com.rhcloud.papers.view.viewRecuperarSenha;
import java.util.concurrent.ExecutionException;

public class Principal extends AppCompatActivity implements View.OnClickListener{
    private Button btnEsqueciSenha, btnCadastrarNovoUsuario, btnEntrar;
    private EditText txtLogin, txtSenha;
    private Usuario usuario;
    private procLogin procLogin =null;
    private SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        prepararComponentes();
    }

    @Override
    protected void onResume() {
        sharedPreferences= getSharedPreferences(hlpConstants.MYPREFERENCES, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(hlpConstants.PREF_EMAIL) && sharedPreferences.contains(hlpConstants.PREF_SENHA)){
            Intent intent= new Intent(this, viewHome.class);
            startActivity(intent);
        }
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        if (view.getId() == btnEsqueciSenha.getId()) {
            intent = new Intent(Principal.this, viewRecuperarSenha.class);
            startActivity(intent);
        }
        if (view.getId() == btnCadastrarNovoUsuario.getId()) {
            intent = new Intent(Principal.this, viewCadastrarUsuario.class);
            startActivity(intent);
        }
        if (view.getId() == btnEntrar.getId()) {
            if (validarDados()) {
                atualizarObjeto();
                procLogin = new procLogin(usuario);
                procLogin.execute();
            }
        }
    }

    private void atualizarObjeto() {
        usuario = new Usuario();
        usuario.getPessoa().setEmail(txtLogin.getText().toString());
        usuario.setSenha(txtSenha.getText().toString());
    }

    private boolean validarDados() {
        if (txtLogin.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Atenção", "O Email deve ser informado", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtLogin.requestFocus();
                }
            });
            return false;
        }

        if (!hlpValidaDados.isValidEmail(txtLogin.getText().toString())) {
            hlpDialog.getAlertDialog(this, "Atenção", "Email com formato inválido", "Ok", new itfDialogGeneric() {

                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtLogin.requestFocus();
                }
            });
            return false;
        }

        if (txtSenha.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Atenção", "A Senha deve ser informada", "Ok", new itfDialogGeneric() {

                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtSenha.requestFocus();
                }
            });
            return false;
        }

        return true;
    }

    private void prepararComponentes() {
        btnEsqueciSenha = (Button) findViewById(R.id.btnEsqueciMinhaSenha);
        btnCadastrarNovoUsuario = (Button) findViewById(R.id.btnCadastrarNovoUsuario);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnEsqueciSenha.setOnClickListener(this);
        btnCadastrarNovoUsuario.setOnClickListener(this);
        btnEntrar.setOnClickListener(this);

        txtLogin = (EditText) findViewById(R.id.txtLogin);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
    }

    public class procLogin extends AsyncTask<String, Void, Usuario>{
        private Usuario usuario;

        public procLogin(Usuario usuario){
            this.usuario = usuario;
        }

        @Override
        protected Usuario doInBackground(String... params) {
            try {
                ctrlAutentication ctrlAutentication = new ctrlAutentication(usuario);
                usuario = (Usuario) ctrlAutentication.efetuarLogin();
                return usuario;

            }
            catch (excPassaErro excPassaErro) {
                excPassaErro.printStackTrace();}
            return new Usuario();
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Principal.this, "Aguarde", "Enviando solicitação...");
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            Intent intent;

            if (usuario.getToken() == null) {
                progressDialog.dismiss();
                hlpDialog.getAlertDialog(Principal.this, "Atenção", hlpConstants.MSG_401, "Ok", new itfDialogGeneric() {
                    @Override
                    public void onButtonAction(boolean value) throws excPassaErro {
                        txtLogin.requestFocus();
                    }
                });
            }
            else{
                Editor editor= sharedPreferences.edit();
                editor.putString(hlpConstants.PREF_ID, String.valueOf(usuario.getId()));
                editor.putString(hlpConstants.PREF_PRIMEIRO_NAME, usuario.getPessoa().getPrimeiroNome());
                editor.putString(hlpConstants.PREF_SEGUNDO_NAME, usuario.getPessoa().getSegundoNome());
                editor.putString(hlpConstants.PREF_EMAIL, usuario.getPessoa().getEmail());
                editor.putString(hlpConstants.PREF_SENHA, txtSenha.getText().toString());
                editor.putString(hlpConstants.PREF_TOKEN, usuario.getToken());
                editor.putString(hlpConstants.PREF_ULTACESSO, usuario.getDtUltAcesso());
                editor.putString(hlpConstants.PREF_FOTO, usuario.getPessoa().getFoto());
                editor.commit();
                progressDialog.dismiss();
                intent = new Intent(Principal.this, viewHome.class);
                startActivity(intent);

            }
        }
    }
}

