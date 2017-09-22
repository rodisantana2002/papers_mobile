package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlUsuario;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.helpers.generic.hlpValidaDados;
import com.rhcloud.papers.model.entity.Pessoa;
import com.rhcloud.papers.model.entity.Usuario;

public class viewAlterarSenha extends AppCompatActivity implements View.OnClickListener {
    private EditText txtSenhaAtual, txtNovaSenha, txtRepitaNovaSenha;
    private Button btnEnviar;
    private ImageButton btnVoltar;
    private ProgressDialog progressDialog;
    private procDados procDados;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alterar_senha);

        prepararComponentes(getIntent().getExtras());
    }

    private void prepararComponentes(Bundle bundle) {
        btnEnviar = (Button) findViewById(R.id.btnEnviarAlterarSenha);
        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarAlterarSenha);
        btnEnviar.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);

        txtSenhaAtual = (EditText) findViewById(R.id.txtSenhaAtual);
        txtNovaSenha = (EditText) findViewById(R.id.txtNovaSenha);
        txtRepitaNovaSenha = (EditText) findViewById(R.id.txtRepitaNovaSenha);

        usuario = (Usuario) bundle.getSerializable("usuario");
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

        if (view.getId() == btnVoltar.getId()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            intent = new Intent(viewAlterarSenha.this, viewPerfil.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private void atualizarObjeto() {
        usuario.setSenha(txtNovaSenha.getText().toString());
    }

    private boolean validarDados() {

        if (txtSenhaAtual.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Atenção", "A Senha Atual deve ser informada", "Ok", new itfDialogGeneric() {

                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtSenhaAtual.requestFocus();
                }
            });
            return false;
        }

        if (txtNovaSenha.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Atenção", "A Nova Senha deve ser informada", "Ok", new itfDialogGeneric() {

                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtNovaSenha.requestFocus();
                }
            });
            return false;
        }

        if (!hlpValidaDados.isSenhaForte(txtNovaSenha.getText().toString())) {
            hlpDialog.getAlertDialog(this, "Atenção", "A Senha não atende os requisitos de segurança", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtNovaSenha.requestFocus();
                }
            });
            return false;
        }

        if (txtRepitaNovaSenha.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Atenção", "A Senha deve ser informada", "Ok", new itfDialogGeneric() {

                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtRepitaNovaSenha.requestFocus();
                }
            });
            return false;
        }

        if (!hlpValidaDados.isSenhaForte(txtRepitaNovaSenha.getText().toString())) {
            hlpDialog.getAlertDialog(this, "Atenção", "A Senha não atende os requisitos de segurança", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtRepitaNovaSenha.requestFocus();
                }
            });
            return false;
        }

        if (!txtNovaSenha.getText().toString().equals(txtRepitaNovaSenha.getText().toString())) {
            hlpDialog.getAlertDialog(this, "Atenção", "As Senhas informadas devem ser identicas", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtNovaSenha.requestFocus();
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
            ctrlUsuario ctrlUsuario = new ctrlUsuario(usuario);
            String msg = "";
            try {
                return ctrlUsuario.alterarSenha();
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                msg = excPassaErro.getMessage();
            }
            return msg;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewAlterarSenha.this, "Aguarde", "Enviando solicitação...");
        }

        @Override
        protected void onPostExecute(final String result) {
            progressDialog.dismiss();
            hlpDialog.getAlertDialog(viewAlterarSenha.this, "Atenção", result, "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    if (result.trim().equals("Senha alterada com sucesso")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("usuario", usuario);
                        Intent intent = new Intent(viewAlterarSenha.this, viewPerfil.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        txtSenhaAtual.requestFocus();
                    }
                }
            });
        }
    }

}

