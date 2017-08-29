package com.rhcloud.papers.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rhcloud.papers.Principal;
import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlLogin;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpConstants;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.helpers.generic.hlpValidaDados;
import com.rhcloud.papers.model.entity.Usuario;

public class viewRecuperarSenha extends AppCompatActivity implements View.OnClickListener {
    private ctrlLogin ctrlLogin;
    private Button btnEnviar;
    private EditText txtEmail;
    private String msg;

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
                msg = recuperarAcesso(txtEmail.getText().toString());
                hlpDialog.getAlertDialog(this, "Alerta", msg, "Ok", new itfDialogGeneric() {
                    @Override
                    public void onButtonAction(boolean value) throws excPassaErro {
                        if (msg.trim().equals("O email foi enviado com sucesso.")){
                            Intent intent = new Intent(viewRecuperarSenha.this, Principal.class);
                            startActivity(intent);
                        }
                        else{
                            txtEmail.requestFocus();
                        }
                    }
                });

            }
        }
    }

    private String recuperarAcesso(String email) {
        Usuario user = new Usuario();
        user.getPessoa().setEmail(email);
        ctrlLogin ctrlLogin = new ctrlLogin(user);
        try {
            return ctrlLogin.recuperarAcesso();
        } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
            excPassaErro.printStackTrace();
        }
        return "Deslculpe, a solicitação não poder ser processada";
    }

    private boolean validarDados() {
        if (txtEmail.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Alerta", "O Email deve ser informado", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtEmail.requestFocus();
                }
            });
            return false;
        }

        if (!hlpValidaDados.isValidEmail(txtEmail.getText().toString())) {
            hlpDialog.getAlertDialog(this, "Alerta", "Email com formato inválido", "Ok", new itfDialogGeneric() {

                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtEmail.requestFocus();
                }
            });
            return false;
        }

        return true;
    }
}
