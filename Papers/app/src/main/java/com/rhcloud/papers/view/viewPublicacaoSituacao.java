package com.rhcloud.papers.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlHistorico;
import com.rhcloud.papers.control.ctrlNotificacao;
import com.rhcloud.papers.control.ctrlSubmissoes;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.helpers.generic.hlpMapasValoresEnuns;
import com.rhcloud.papers.model.entity.Documento;
import com.rhcloud.papers.model.entity.FilaSubmissao;
import com.rhcloud.papers.model.entity.HistoricoFilaSubmissao;
import com.rhcloud.papers.model.entity.Notificacao;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.model.enumeration.AcoesPublicacao;
import com.rhcloud.papers.model.enumeration.Situacao;
import com.rhcloud.papers.model.enumeration.Status;
import com.rhcloud.papers.model.transitorio.Acao;
import com.rhcloud.papers.model.transitorio.AutorPerfil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class viewPublicacaoSituacao extends AppCompatActivity implements View.OnClickListener{
    private EditText txtComentario, txtDtPublicacao;
    private Usuario usuario;
    private FilaSubmissao filaSubmissao;
    private HistoricoFilaSubmissao historicoFilaSubmissao;
    private Notificacao notificacao;
    private Acao acao;
    private AutorPerfil autorPerfil;
    private Button btnEnviar;
    private ImageButton btnVoltar, btnDataPublicacao;
    private ProgressDialog progressDialog;
    private procDados procDados;
    private hlpMapasValoresEnuns mapasValoresEnuns;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;

    private GridLayout gridDtPublicacao;


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
        txtDtPublicacao = (EditText) findViewById(R.id.txtDataPublicacaoSituacao);
        gridDtPublicacao = (GridLayout) findViewById(R.id.gridDtPublicacao);

        btnEnviar.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);

        btnDataPublicacao = (ImageButton) findViewById(R.id.btnDataPublicacao);
        btnDataPublicacao.setOnClickListener(this);

        usuario = (Usuario) bundle.getSerializable("usuario");
        autorPerfil = (AutorPerfil) bundle.getSerializable("autorPerfil");
        filaSubmissao = (FilaSubmissao) bundle.getSerializable("publicacao");
        acao = (Acao) bundle.getSerializable("acao");
        mapasValoresEnuns = new hlpMapasValoresEnuns();

        if(acao.getNomeAcao().equals("Registrar Publicação")){
            gridDtPublicacao.setVisibility(View.VISIBLE);
        }
        else{
            gridDtPublicacao.setVisibility(View.GONE);
        }
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
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

        if(view.getId() == btnDataPublicacao.getId()){
            setDate(view);
        }
    }

    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        String dia, mes = "";
        if(day<10){
            dia = "0" + String.valueOf(day);
        }
        else {
            dia = String.valueOf(day);
        }

        if(month<10){
            mes = "0" + String.valueOf(month);
        }
        else {
            mes = String.valueOf(month);
        }

        String strDataLimite = dia + "/" + mes + "/" + year;
        txtDtPublicacao.setText(strDataLimite);
    }

    private void atualizarObjeto() {
        historicoFilaSubmissao = new HistoricoFilaSubmissao();

        filaSubmissao.setSituacao(acao.getSituacao());
        if(acao.getSituacao().equals(Situacao.AGUARDANDO_AJUSTES)){
            filaSubmissao.setVersao(String.valueOf(Integer.valueOf(filaSubmissao.getVersao())+1));

        }
        if(acao.getNomeAcao().equals("Registrar Publicação")){
            filaSubmissao.setDtPublicacao(txtDtPublicacao.getText().toString());
        }
        historicoFilaSubmissao.setFilaSubmissao(filaSubmissao);
        historicoFilaSubmissao.setSituacao(acao.getSituacao());
        historicoFilaSubmissao.setVersao(filaSubmissao.getVersao());
        historicoFilaSubmissao.setComentario(usuario.getPessoa().getPrimeiroNome() + " alterou a situação da Publicação para " +
                                             mapasValoresEnuns.getDescricaoSituacao(acao.getSituacao()) + " e registrou o comentário: " +
                                             txtComentario.getText().toString());
        historicoFilaSubmissao.setCriadoPor(usuario.getPessoa());

        notificacao = new Notificacao();
        notificacao.setDocumento(filaSubmissao.getDocumento());
        notificacao.setConteudo(historicoFilaSubmissao.getComentario());
        notificacao.setStatus(Status.PENDENTE);
    }

    private boolean validarDados() {
        if(acao.getNomeAcao().equals("Registrar Publicação")){
            if (txtDtPublicacao.getText().toString().isEmpty()) {
                hlpDialog.getAlertDialog(this, "Atenção", "A Data de Publicação deve ser informada", "Ok", new itfDialogGeneric() {
                    @Override
                    public void onButtonAction(boolean value) throws excPassaErro {
                        txtDtPublicacao.requestFocus();
                    }
                });
                return false;
            }
        }

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
            ctrlNotificacao ctrlNotificacao = new ctrlNotificacao(notificacao);
            String msg = "";
            try {
                ctrlHistorico.criar();
                ctrlNotificacao.criar(usuario);
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
