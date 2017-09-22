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
import com.rhcloud.papers.control.ctrlDestino;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.model.entity.Destino;
import com.rhcloud.papers.model.entity.Usuario;

public class viewRepositorioDetail extends AppCompatActivity implements View.OnClickListener {
    private EditText txtClassificacao, txtDescricao;
    private Destino destino;
    private Usuario usuario;
    private Button btnEnviar;
    private ImageButton btnVoltar;
    private ProgressDialog progressDialog;
    private procDados procDados;
    private ImageButton btnExcluirRepositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_repositorio_detail);
        prepararComponentes(getIntent().getExtras());
    }

    private void prepararComponentes(Bundle bundle) {
        destino = (Destino) bundle.getSerializable("destino");
        usuario = (Usuario) bundle.getSerializable("usuario");

        btnEnviar = (Button) findViewById(R.id.btnEnviarRepositorio);
        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarHomeRepositorio);
        txtClassificacao = (EditText) findViewById(R.id.txtClassificaoDetail);
        txtDescricao = (EditText) findViewById(R.id.txtDescricaoRepositorioDetail);
        btnEnviar.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);


        txtClassificacao.setText(destino.getClassificacao());
        txtDescricao.setText(destino.getDescricao());

        btnExcluirRepositorio = (ImageButton) findViewById(R.id.btnExcluirRepositorio);
        btnExcluirRepositorio.setOnClickListener(this);

        if (destino.getId() == null) {
            btnExcluirRepositorio.setVisibility(View.GONE);
        } else {
            btnExcluirRepositorio.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == btnEnviar.getId()) {
            if (validarDados()) {
                atualizarObjeto();
                procDados = new procDados(destino);
                procDados.execute();
            }
        }

        if (view.getId() == btnVoltar.getId()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            intent = new Intent(viewRepositorioDetail.this, viewRepositorio.class);
            intent.putExtras(bundle);
            startActivity(intent);

            intent = new Intent(viewRepositorioDetail.this, viewRepositorio.class);
            startActivity(intent);
        }

        if (view.getId() == btnExcluirRepositorio.getId()) {
            hlpDialog.getConfirmDialog(viewRepositorioDetail.this, "Atenção", "Confirma a exclusão do Repositório?", "Sim", "Não", false, new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    if (value) {
                        ctrlDestino ctrlDestino = new ctrlDestino(destino);
                        final String msg = ctrlDestino.remover();
                        hlpDialog.getAlertDialog(viewRepositorioDetail.this, "Atenção", msg, "Ok", new itfDialogGeneric() {
                            @Override
                            public void onButtonAction(boolean value) throws excPassaErro {
                                if (msg.trim().equals("Repositório removido do sistema com sucesso")) {
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("usuario", usuario);
                                    Intent intent = new Intent(viewRepositorioDetail.this, viewRepositorio.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                } else {
                                    txtClassificacao.requestFocus();
                                }
                            }
                        });
                    } else {
                        txtClassificacao.requestFocus();
                    }
                }
            });
        }
    }

    private void atualizarObjeto() {
        destino.setClassificacao(txtClassificacao.getText().toString());
        destino.setDescricao(txtDescricao.getText().toString());
    }

    private boolean validarDados() {
        if (txtClassificacao.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Atenção", "A Classificação do Repositório deve ser informada", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtClassificacao.requestFocus();
                }
            });
            return false;
        }
        if (txtDescricao.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Atenção", "A Descrição do Repositório deve ser informada", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtDescricao.requestFocus();
                }
            });
            return false;
        }
        return true;
    }

    private class procDados extends AsyncTask<Void, Void, String> {
        private Destino destino;

        public procDados(Destino destino) {
            this.destino = destino;
        }

        @Override
        protected String doInBackground(Void... voids) {
            ctrlDestino ctrlDestino = new ctrlDestino(destino);
            String msg = "";
            try {
                if (destino.getId() == null) {
                    return ctrlDestino.criar();
                } else {
                    return ctrlDestino.atualizar();
                }

            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                msg = excPassaErro.getMessage();
            }
            return msg;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewRepositorioDetail.this, "Aguarde", "Enviando solicitação...");
        }

        @Override
        protected void onPostExecute(final String result) {
            progressDialog.dismiss();
            hlpDialog.getAlertDialog(viewRepositorioDetail.this, "Atenção", result, "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    if (result.trim().equals("Repositório registrado com sucesso")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("usuario", usuario);
                        Intent intent = new Intent(viewRepositorioDetail.this, viewRepositorio.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        txtClassificacao.requestFocus();
                    }
                }
            });
        }
    }


}

