package com.rhcloud.papers;

import android.app.Activity;
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

import com.rhcloud.papers.control.ctrlLogin;
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
    private UserLogin mUserLogin =null;
    private SharedPreferences sharedPreferences;

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
                if (validarAcesso()) {
                    Editor editor= sharedPreferences.edit();
                    editor.putString(hlpConstants.PREF_ID, String.valueOf(usuario.getId()));
                    editor.putString(hlpConstants.PREF_NAME, usuario.getPessoa().getPrimeiroNome() + " " + usuario.getPessoa().getSegundoNome());
                    editor.putString(hlpConstants.PREF_EMAIL, usuario.getPessoa().getEmail());
                    editor.putString(hlpConstants.PREF_SENHA, txtSenha.getText().toString());
                    editor.putString(hlpConstants.PREF_TOKEN, usuario.getToken());
                    editor.commit();
                    intent = new Intent(this, viewHome.class);
                    startActivity(intent);
                }
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
            hlpDialog.getAlertDialog(this, "Alerta", "O Email deve ser informado", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtLogin.requestFocus();
                }
            });
            return false;
        }

        if (!hlpValidaDados.isValidEmail(txtLogin.getText().toString())) {
            hlpDialog.getAlertDialog(this, "Alerta", "Email com formato inválido", "Ok", new itfDialogGeneric() {

                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtLogin.requestFocus();
                }
            });
            return false;
        }

        if (txtSenha.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Alerta", "A Senha deve ser informada", "Ok", new itfDialogGeneric() {

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

    private Boolean validarAcesso() {
         try{
            mUserLogin = new UserLogin();
            usuario = mUserLogin.execute(txtLogin.getText().toString(), txtSenha.getText().toString()).get();

            if (usuario.getToken() == null) {
                hlpDialog.getAlertDialog(this, "Alerta", hlpConstants.MSG_401, "Ok", new itfDialogGeneric() {
                    @Override
                    public void onButtonAction(boolean value) throws excPassaErro {
                        txtLogin.requestFocus();
                    }
                });
                return false;
            }
        }
        catch (InterruptedException e) {e.printStackTrace(); return false;}
        catch (ExecutionException e) {e.printStackTrace(); return false;}

        return true;
    }


    public class UserLogin extends AsyncTask<String, Void, Usuario>{

        @Override
        protected Usuario doInBackground(String... params) {
            try {
                Usuario user = new Usuario();
                user.setSenha(params[1]);
                user.getPessoa().setEmail(params[0]);
                ctrlLogin ctrlLogin = new ctrlLogin(user);
                user = (Usuario) ctrlLogin.efetuarLogin();
                return user;

            }
            catch (excPassaErro excPassaErro) {
                excPassaErro.printStackTrace();}
            return new Usuario();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
        }
    }
}

