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
import com.rhcloud.papers.control.ctrlPessoa;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.helpers.generic.hlpValidaDados;
import com.rhcloud.papers.model.entity.Usuario;

public class viewAlterarDadosPessoais extends AppCompatActivity implements View.OnClickListener {
    private EditText txtPrimeiroNome, txtSegundoNome, txtEmailUsuario, txtDDD, txtFoneCelular, txtInstituicao;
    private AutoCompleteTextView txtPais, txtEstado, txtCidade;
    private Usuario usuario;
    private Button btnEnviar;
    private ImageButton btnVoltar;
    private ProgressDialog progressDialog;
    private procDados procDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alterar_dados_pessoais);

        prepararComponentes(getIntent().getExtras());
    }

    private void prepararComponentes(Bundle bundle) {
        btnEnviar = (Button) findViewById(R.id.btnEnviarDP);
        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarDadosPessoais);

        txtPrimeiroNome = (EditText) findViewById(R.id.txtPrimeiroNomeDP);
        txtSegundoNome = (EditText) findViewById(R.id.txtSegundoNomeDP);
        txtEmailUsuario = (EditText) findViewById(R.id.txtEmailUsuarioDP);
        txtDDD = (EditText) findViewById(R.id.txtDDDDP);
        txtFoneCelular = (EditText) findViewById(R.id.txtFoneCelularDP);
        txtInstituicao = (EditText) findViewById(R.id.txtInstituicaoDP);
        txtPais = (AutoCompleteTextView) findViewById(R.id.txtPaisDP);
        txtEstado = (AutoCompleteTextView) findViewById(R.id.txtEstadoDP);
        txtCidade = (AutoCompleteTextView) findViewById(R.id.txtCidadeDP);

        btnEnviar.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);

        usuario = (Usuario) bundle.getSerializable("usuario");
        txtPrimeiroNome.setText(usuario.getPessoa().getPrimeiroNome());
        txtSegundoNome.setText(usuario.getPessoa().getSegundoNome());
        txtEmailUsuario.setText(usuario.getPessoa().getEmail());
        txtDDD.setText(usuario.getPessoa().getDDD());
        txtFoneCelular.setText(usuario.getPessoa().getFoneCelular());
        txtInstituicao.setText(usuario.getPessoa().getInstituicao());
        txtPais.setText(usuario.getPessoa().getPais());
        txtEstado.setText(usuario.getPessoa().getEstado());
        txtCidade.setText(usuario.getPessoa().getCidade());
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
            intent = new Intent(viewAlterarDadosPessoais.this, viewPerfil.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private void atualizarObjeto() {
        usuario.getPessoa().setPrimeiroNome(txtPrimeiroNome.getText().toString());
        usuario.getPessoa().setSegundoNome(txtSegundoNome.getText().toString());
        usuario.getPessoa().setEmail(txtEmailUsuario.getText().toString());
        usuario.getPessoa().setDDD(txtDDD.getText().toString());
        usuario.getPessoa().setFoneCelular(txtFoneCelular.getText().toString());
        usuario.getPessoa().setInstituicao(txtInstituicao.getText().toString());
        usuario.getPessoa().setPais(txtPais.getText().toString());
        usuario.getPessoa().setEstado(txtEstado.getText().toString());
        usuario.getPessoa().setCidade(txtCidade.getText().toString());
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

        return true;
    }

    private class procDados extends AsyncTask<Void, Void, String> {
        private Usuario usuario;

        public procDados(Usuario usuario) {
            this.usuario = usuario;
        }

        @Override
        protected String doInBackground(Void... voids) {
            ctrlPessoa ctrlPessoa = new ctrlPessoa(usuario.getPessoa());
            try {
                return ctrlPessoa.atualizar();
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                excPassaErro.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewAlterarDadosPessoais.this, "Aguarde", "Enviando solicitação...");
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (result.trim().equals("Autor registrado com sucesso")) {
                result = "Dados pessoais registrados com sucesso";
            }
            final String finalResult = result;
            hlpDialog.getAlertDialog(viewAlterarDadosPessoais.this, "Atenção", result, "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    if (finalResult.trim().equals("Dados pessoais registrados com sucesso")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("usuario", usuario);
                        Intent intent = new Intent(viewAlterarDadosPessoais.this, viewPerfil.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        txtPrimeiroNome.requestFocus();
                    }
                }
            });
        }
    }
}




