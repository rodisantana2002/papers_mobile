package com.rhcloud.papers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rhcloud.papers.control.ctrlLogin;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpConstants;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.view.viewCadastrarUsuario;
import com.rhcloud.papers.view.viewRecuperarSenha;

public class Principal extends Activity implements View.OnClickListener{
    private Button btnEsqueciSenha, btnCadastrarNovoUsuario, btnEntrar;
    private EditText txtLogin, txtSenha;
    private ctrlLogin ctrlLogin;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        prepararComponentes();
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        if (view.getId() == btnEsqueciSenha.getId()){
            intent = new Intent(Principal.this, viewRecuperarSenha.class);
            startActivity(intent);
        }
        if (view.getId() == btnCadastrarNovoUsuario.getId()){
            intent = new Intent(Principal.this, viewCadastrarUsuario.class);
            startActivity(intent);
        }
        if(view.getId() == btnEntrar.getId()){
            if (validarDados()) {
                atualizarObjeto();
                if (validarAcesso()){
                    
                };
            }
        }
    }

    private void atualizarObjeto() {
        usuario = new Usuario();
        usuario.getPessoa().setEmail(txtLogin.getText().toString());
        usuario.setSenha(txtSenha.getText().toString());
    }

    private boolean validarDados() {
        if(txtLogin.getText().toString().isEmpty()){
            hlpDialog.getAlertDialog(this, "Alerta", "O Email deve ser informado", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtLogin.requestFocus();
                }
            });
            return false;
        }

        if(txtSenha.getText().toString().isEmpty()){
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

    private void prepararComponentes(){
        btnEsqueciSenha = findViewById(R.id.btnEsqueciMinhaSenha);
        btnCadastrarNovoUsuario = findViewById(R.id.btnCadastrarNovoUsuario);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnEsqueciSenha.setOnClickListener(this);
        btnCadastrarNovoUsuario.setOnClickListener(this);
        btnEntrar.setOnClickListener(this);

        txtLogin = (EditText) findViewById(R.id.txtLogin);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
    }

    private Boolean validarAcesso(){
        ctrlLogin = new ctrlLogin(usuario);
        try {
            usuario = ctrlLogin.efetuarLogin();
            if (usuario.getToken()==null){
                hlpDialog.getAlertDialog(this, "Alerta", hlpConstants.MSG_401, "Ok", new itfDialogGeneric() {
                    @Override
                    public void onButtonAction(boolean value) throws excPassaErro {
                        txtLogin.requestFocus();
                    }
                });
                return false;
            }
            registrarUsuario();
        } catch (excPassaErro excPassaErro) {
            excPassaErro.printStackTrace();
        }
        return true;
    }

    private void registrarUsuario() {

    }
}
