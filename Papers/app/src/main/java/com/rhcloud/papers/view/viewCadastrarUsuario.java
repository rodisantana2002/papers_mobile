package com.rhcloud.papers.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rhcloud.papers.R;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.helpers.generic.hlpValidaDados;

public class viewCadastrarUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_cadastrar_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

//            if(!hlpValidaDados.isSenhaForte(txtSenha.getText().toString())){
//        hlpDialog.getAlertDialog(this, "Alerta", "A Senha deve ser forte", "Ok", new itfDialogGeneric() {
//
//            @Override
//            public void onButtonAction(boolean value) throws excPassaErro {
//                txtSenha.requestFocus();
//            }
//        });
//        return false;
//    }

//            if (!hlpValidaDados.isValidEmail(txtLogin.getText().toString())){
//        hlpDialog.getAlertDialog(this, "Alerta", "Email com formato inválido", "Ok", new itfDialogGeneric() {
//
//            @Override
//            public void onButtonAction(boolean value) throws excPassaErro {
//                txtLogin.requestFocus();
//            }
//        });
//        return false;
//    }


//    private boolean validarDados() {
//        if(txtLogin.getText().toString().isEmpty()){
//            hlpDialog.getAlertDialog(this, "Alerta", "O Email deve ser informado", "Ok", new itfDialogGeneric() {
//                @Override
//                public void onButtonAction(boolean value) throws excPassaErro {
//                    txtLogin.requestFocus();
//                }
//            });
//            return false;
//        }
//
//        if (!hlpValidaDados.isValidEmail(txtLogin.getText().toString())){
//            hlpDialog.getAlertDialog(this, "Alerta", "Email com formato inválido", "Ok", new itfDialogGeneric() {
//
//                @Override
//                public void onButtonAction(boolean value) throws excPassaErro {
//                    txtLogin.requestFocus();
//                }
//            });
//            return false;
//        }
//
//        if(txtSenha.getText().toString().isEmpty()){
//            hlpDialog.getAlertDialog(this, "Alerta", "A Senha deve ser informada", "Ok", new itfDialogGeneric() {
//
//                @Override
//                public void onButtonAction(boolean value) throws excPassaErro {
//                    txtSenha.requestFocus();
//                }
//            });
//            return false;
//        }
//
//        return true;
//    }
}
