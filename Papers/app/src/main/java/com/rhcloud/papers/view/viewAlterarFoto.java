package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlPessoa;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.model.entity.Usuario;

public class viewAlterarFoto extends AppCompatActivity implements View.OnClickListener{
    private Button btnSelecionarImg, btnLimparImg, btnEnviar;
    private ImageButton btnVoltar;
    private ImageView imgFoto;
    private ProgressDialog progressDialog;
    private procDados procDados;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alterar_foto);

        prepararComponentes(getIntent().getExtras());
    }

    private void prepararComponentes(Bundle bundle) {
        btnEnviar = (Button) findViewById(R.id.btnEnviarFoto);
        btnSelecionarImg = (Button) findViewById(R.id.btnSelecionarFoto);
        btnLimparImg = (Button) findViewById(R.id.btnLimpar);
        btnVoltar = (ImageButton)  findViewById(R.id.btnVoltarAlterarFoto);

        btnEnviar.setOnClickListener(this);
        btnSelecionarImg.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);
        btnLimparImg.setOnClickListener(this);

        imgFoto = (ImageView) findViewById(R.id.imgFotoUsuario);
        usuario = (Usuario) bundle.getSerializable("usuario");
        btnEnviar.setVisibility(View.INVISIBLE);
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
            intent = new Intent(viewAlterarFoto.this, viewPerfil.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        if(view.getId() ==btnLimparImg.getId()){}
        if(view.getId() ==btnSelecionarImg.getId()){}
    }

    private void atualizarObjeto() {
        //usuario.getPessoa().setFoto();
    }

    private void carregarImagem(){

    }

    private boolean validarDados() {

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
            try {
                return ctrlPessoa.atualizar();
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                excPassaErro.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewAlterarFoto.this, "Aguarde", "Enviando solicitação...");
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (result.trim().equals("Autor registrado com sucesso")) {
                result = "Foto atualizada com sucesso";
            }
            final String finalResult = result;
            hlpDialog.getAlertDialog(viewAlterarFoto.this, "Atenção", result, "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    if (finalResult.trim().equals("Foto atualizada com sucesso")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("usuario", usuario);
                        Intent intent = new Intent(viewAlterarFoto.this, viewPerfil.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        btnLimparImg.requestFocus();
                    }
                }
            });
        }
    }

}

