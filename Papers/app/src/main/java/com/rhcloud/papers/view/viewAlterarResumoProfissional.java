package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlPessoa;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.model.entity.Usuario;

public class viewAlterarResumoProfissional extends AppCompatActivity implements View.OnClickListener{
    private EditText txtResumoProfissional;
    private Usuario usuario;
    private Button btnEnviar;
    private ImageButton btnVoltar;
    private ProgressDialog progressDialog;
    private procDados procDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alterar_resumo_profissional);

        prepararComponentes(getIntent().getExtras());
    }

    private void prepararComponentes(Bundle bundle) {
        btnEnviar = (Button) findViewById(R.id.btnEnviarResumoProfissional);
        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarResumoProfissional);
        txtResumoProfissional = (EditText) findViewById(R.id.txtResumoProfissional);
        btnEnviar.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);

        usuario = (Usuario) bundle.getSerializable("usuario");
        txtResumoProfissional.setText(usuario.getPessoa().getBiografia());
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

        if (view.getId() == btnVoltar.getId()){
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            intent = new Intent(viewAlterarResumoProfissional.this, viewPerfil.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private void atualizarObjeto() {
        usuario.getPessoa().setBiografia(txtResumoProfissional.getText().toString());
    }

    private boolean validarDados() {
        if (txtResumoProfissional.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Atenção", "Resumo profissional deve ser informado", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtResumoProfissional.requestFocus();
                }
            });
            return false;
        }
        if (txtResumoProfissional.getText().length()>500) {
            hlpDialog.getAlertDialog(this, "Atenção", "O resumo informado excede o tamanho máximo de 500 caracteres", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtResumoProfissional.requestFocus();
                }
            });
            return false;
        }
        return true;
    }

    private class procDados extends AsyncTask<Void, Void, String> {
        private Usuario usuario;

        public procDados(Usuario usuario){
            this.usuario = usuario;
        }

        @Override
        protected String doInBackground(Void... voids) {
            ctrlPessoa ctrlPessoa = new ctrlPessoa(usuario.getPessoa());
            String msg = "";
            try {
                return ctrlPessoa.atualizar();
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                msg = excPassaErro.getMessage();
            }
            return msg;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewAlterarResumoProfissional.this, "Aguarde", "Enviando solicitação...");
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (result.trim().equals("Autor registrado com sucesso")){
                result = "Resumo profissiomal registrado com sucesso";
            }
            final String finalResult = result;
            hlpDialog.getAlertDialog(viewAlterarResumoProfissional.this, "Atenção", result, "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    if (finalResult.trim().equals("Resumo profissiomal registrado com sucesso")){
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("usuario", usuario);
                        Intent intent = new Intent(viewAlterarResumoProfissional.this, viewPerfil.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else{
                        txtResumoProfissional.requestFocus();
                    }
                }
            });
        }
    }
}


