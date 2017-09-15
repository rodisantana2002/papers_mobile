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
import com.rhcloud.papers.control.ctrlHistorico;
import com.rhcloud.papers.control.ctrlSubmissoes;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.helpers.generic.hlpMapasValoresEnuns;
import com.rhcloud.papers.model.entity.FilaSubmissao;
import com.rhcloud.papers.model.entity.HistoricoFilaSubmissao;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.model.enumeration.Situacao;
import com.rhcloud.papers.model.transitorio.Acao;
import com.rhcloud.papers.model.transitorio.AutorPerfil;

public class viewPublicacaoSituacao extends AppCompatActivity implements View.OnClickListener{
    private EditText txtComentario;
    private Usuario usuario;
    private FilaSubmissao filaSubmissao;
    private HistoricoFilaSubmissao historicoFilaSubmissao;
    private Acao acao;
    private AutorPerfil autorPerfil;
    private Button btnEnviar;
    private ImageButton btnVoltar;
    private ProgressDialog progressDialog;
    private procDados procDados;
    private hlpMapasValoresEnuns mapasValoresEnuns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_publicacao_situacao);
        prepararComponentes(getIntent().getExtras());
    }


    private void prepararComponentes(Bundle bundle) {
        btnEnviar = (Button) findViewById(R.id.btnEnviarHistorico);
        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarPublicacaoHistorico);
        txtComentario = (EditText) findViewById(R.id.txtHistorico);
        btnEnviar.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);

        usuario = (Usuario) bundle.getSerializable("usuario");
        autorPerfil = (AutorPerfil) bundle.getSerializable("autorPerfil");
        filaSubmissao = (FilaSubmissao) bundle.getSerializable("publicacao");
        acao = (Acao) bundle.getSerializable("acao");
        mapasValoresEnuns = new hlpMapasValoresEnuns();
    }

    public void onClick(View view) {
        Intent intent;
        if (view.getId() == btnEnviar.getId()) {
            if (validarDados()) {
                atualizarObjeto();
                procDados = new procDados(filaSubmissao, historicoFilaSubmissao);
                procDados.execute();
            }
        }

        if (view.getId() == btnVoltar.getId()){
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            bundle.putSerializable("publicacao", filaSubmissao);
            bundle.putSerializable("autorPerfil", autorPerfil);

            intent = new Intent(viewPublicacaoSituacao.this, viewPublicacaoDetail.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private void atualizarObjeto() {
        historicoFilaSubmissao = new HistoricoFilaSubmissao();

        filaSubmissao.setSituacao(acao.getSituacao());
        if(acao.getSituacao().equals(Situacao.AGUARDANDO_AJUSTES)){
            filaSubmissao.setVersao(String.valueOf(Integer.valueOf(filaSubmissao.getVersao())+1));

        }
        historicoFilaSubmissao.setFilaSubmissao(filaSubmissao);
        historicoFilaSubmissao.setSituacao(acao.getSituacao());
        historicoFilaSubmissao.setVersao(filaSubmissao.getVersao());
        historicoFilaSubmissao.setComentario(usuario.getPessoa().getPrimeiroNome() + " alterou a situação da Publicação para " +
                                             mapasValoresEnuns.getDescricaoSituacao(acao.getSituacao()) + " e registrou o comentário: " +
                                             txtComentario.getText().toString());
        historicoFilaSubmissao.setCriadoPor(usuario.getPessoa());
    }

    private boolean validarDados() {
        if (txtComentario.getText().toString().isEmpty()) {
            hlpDialog.getAlertDialog(this, "Atenção", "O Comentário deve ser informado", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtComentario.requestFocus();
                }
            });
            return false;
        }
        if (txtComentario.getText().length()>450) {
            hlpDialog.getAlertDialog(this, "Atenção", "O comentário informado excede o tamanho máximo de 450 caracteres", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtComentario.requestFocus();
                }
            });
            return false;
        }
        return true;
    }

    private class procDados extends AsyncTask<Void, Void, String> {
        private FilaSubmissao filaSub;
        private HistoricoFilaSubmissao historico;

        public procDados(FilaSubmissao filaSubmissao, HistoricoFilaSubmissao historico){
            this.filaSub = filaSubmissao;
            this.historico = historico;
        }

        @Override
        protected String doInBackground(Void... voids) {
            ctrlSubmissoes ctrlSubmissoes = new ctrlSubmissoes(filaSub) ;
            ctrlHistorico  ctrlHistorico = new ctrlHistorico(historico);
            String msg = "";
            try {
                ctrlHistorico.criar();
                return ctrlSubmissoes.atualizar();

            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                msg = excPassaErro.getMessage();
            }
            return msg;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewPublicacaoSituacao.this, "Aguarde", "Enviando solicitação...");
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            final String finalResult = result;
            hlpDialog.getAlertDialog(viewPublicacaoSituacao.this, "Atenção", result, "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    if (finalResult.trim().equals("Submissão registrada com sucesso")){
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("usuario", usuario);
                        bundle.putSerializable("publicacao", filaSub);
                        bundle.putSerializable("autorPerfil", autorPerfil);
                        Intent intent = new Intent(viewPublicacaoSituacao.this, viewPublicacaoDetail.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else{
                        txtComentario.requestFocus();
                    }
                }
            });
        }
    }
}
