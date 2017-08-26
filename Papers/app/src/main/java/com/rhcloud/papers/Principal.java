package com.rhcloud.papers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rhcloud.papers.view.viewCadastrarUsuario;
import com.rhcloud.papers.view.viewRecuperarSenha;

public class Principal extends Activity implements View.OnClickListener{
    private Button btnEsqueciSenha, btnCadastrarNovoUsuario, btnEntrar;
    private EditText txtLogin, txtSenha;

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
            validarAcesso();;
        }
    }

    private void prepararComponentes(){
        btnEsqueciSenha = findViewById(R.id.btnEsqueciMinhaSenha);
        btnCadastrarNovoUsuario = findViewById(R.id.btnCadastrarNovoUsuario);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnEsqueciSenha.setOnClickListener(this);
        btnCadastrarNovoUsuario.setOnClickListener(this);

        txtLogin = findViewById(R.id.txtLogin);
        txtSenha = findViewById(R.id.txtSenha);
    }

    private void validarAcesso(){

    }
}
