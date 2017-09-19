package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlNotificacao;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.model.entity.Notificacao;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.model.enumeration.Status;

public class viewNotificacaoDetail extends AppCompatActivity implements View.OnClickListener {
    private TextView txtNotificacao;
    private Notificacao notificacao;
    private Usuario usuario;
    private ImageButton btnVoltar;
    private Button btnArquivar;
    private ProgressDialog progressDialog;
    private procDados procDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notificacao_detail);

        prepararComponentes(getIntent().getExtras());
        if (notificacao.getStatus().equals(Status.PENDENTE)){
            notificacao.setStatus(Status.LIDA);
            procDados = new procDados();
            procDados.execute();
        }
    }

    private void prepararComponentes(Bundle bundle) {
        usuario = (Usuario) bundle.getSerializable("usuario");
        notificacao = (Notificacao)  bundle.getSerializable("notificacao");

        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarNotificacaoDetail);
        btnVoltar.setOnClickListener(this);

        btnArquivar = (Button) findViewById(R.id.btnArquivar);
        btnArquivar.setOnClickListener(this);
        if (notificacao.getStatus().equals(Status.ARQUIVADA)){
            btnArquivar.setVisibility(View.GONE);
        }

        txtNotificacao = (TextView) findViewById(R.id.txtNotificacaoDetail);
        txtNotificacao.setText("Novidades sobre o Artigo: " + notificacao.getDocumento().getTitulo() + ":\n" + notificacao.getConteudo());
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        if (view.getId() == btnVoltar.getId()){
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            intent = new Intent(viewNotificacaoDetail.this, viewNotificacao.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        if (view.getId() == btnArquivar.getId()){
            hlpDialog.getConfirmDialog(viewNotificacaoDetail.this, "Atenção", "Confirma o arquivamento da Notificação?", "Sim", "Não", false, new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    if (value){
                        notificacao.setStatus(Status.ARQUIVADA);
                        procDados = new procDados();
                        procDados.execute();

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("usuario", usuario);
                        Intent intent = new Intent(viewNotificacaoDetail.this, viewNotificacao.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private class procDados extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            ctrlNotificacao ctrlNotificacao = new ctrlNotificacao(notificacao);
            try {
                ctrlNotificacao.atualizar();
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                excPassaErro.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewNotificacaoDetail.this, "Aguarde", "Carregando dados...");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
        }
    }

}
