package com.rhcloud.papers.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlDestino;
import com.rhcloud.papers.control.ctrlSubmissoes;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.model.entity.Destino;
import com.rhcloud.papers.model.entity.Documento;
import com.rhcloud.papers.model.entity.FilaSubmissao;
import com.rhcloud.papers.model.entity.HistoricoFilaSubmissao;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.model.enumeration.Situacao;
import com.rhcloud.papers.model.transitorio.AutorPerfil;
import com.rhcloud.papers.view.adapters.adpDestinos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class viewPublicacaoEdit extends AppCompatActivity implements View.OnClickListener {
    private Spinner txtDestino;
    private EditText txtDataLimite;
    private AutoCompleteTextView txtIdioma;
    private adpDestinos adpDestino;
    private Documento documento;
    private Usuario usuario;
    private Destino destino;
    private AutorPerfil autorPerfil;
    private FilaSubmissao filaSubmissao;
    private HistoricoFilaSubmissao historicoFilaSubmissao;
    private Button btnEnviarPublicacao;
    private ImageButton btnVoltar, btnDataLimite;
    private ProgressDialog progressDialog;
    private procDados procDados;
    private pouplarDados pouplarDados;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_publicacao_edit);

        prepararComponentes(getIntent().getExtras());
        pouplarDados = new pouplarDados();
        pouplarDados.execute();
    }

    private void prepararComponentes(Bundle bundle) {
        documento = (Documento) bundle.getSerializable("documento");
        usuario = (Usuario) bundle.getSerializable("usuario");
        filaSubmissao = (FilaSubmissao) bundle.getSerializable("publicacao");
        autorPerfil = (AutorPerfil) bundle.getSerializable("autorPerfil");

        String[] lstIdiomas = getResources().getStringArray(R.array.listIdiomas);
        ArrayAdapter adapterIdioma = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, lstIdiomas);

        txtDestino = (Spinner) findViewById(R.id.txtDestinoEdit);
        txtIdioma = (AutoCompleteTextView) findViewById(R.id.txtIdiomaEdit);
        txtDataLimite = (EditText) findViewById(R.id.txtDataLimiteEdit);

        btnEnviarPublicacao = (Button) findViewById(R.id.btnEnviarPublicacao);
        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarPublicacaoEdit);

        btnDataLimite = (ImageButton) findViewById(R.id.btnDataLimite);
        btnDataLimite.setOnClickListener(this);

        btnEnviarPublicacao.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);

        txtIdioma.setText(filaSubmissao.getIdioma()!=null?filaSubmissao.getIdioma():"");
        txtDataLimite.setText(filaSubmissao.getDtLimiteSubmissao()!=null?filaSubmissao.getDtLimiteSubmissao():"");
        txtIdioma.setAdapter(adapterIdioma);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
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
        txtDataLimite.setText(strDataLimite);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == btnEnviarPublicacao.getId()) {
            if (validarDados()) {
                atualizarObjeto();
                procDados = new procDados(filaSubmissao);
                procDados.execute();
            }
        }

        if (view.getId() == btnVoltar.getId()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            bundle.putSerializable("autorPerfil", autorPerfil);

            if (filaSubmissao.getId()==null){
                bundle.putSerializable("documento", documento);
                intent = new Intent(viewPublicacaoEdit.this, viewDocumentoDetail.class);
            }
            else{
                bundle.putSerializable("publicacao", filaSubmissao);
                intent = new Intent(viewPublicacaoEdit.this, viewPublicacaoDetail.class);
            }

            intent.putExtras(bundle);
            startActivity(intent);
        }

        if(view.getId() == btnDataLimite.getId()){
            setDate(view);
        }
    }

    private void atualizarObjeto() {
        Destino destino = (Destino) txtDestino.getSelectedItem();
        if(filaSubmissao.getId()==null){
            filaSubmissao.setDocumento(documento);
            filaSubmissao.setCriadoPor(usuario.getPessoa());
            filaSubmissao.setSituacao(Situacao.INICIADO);
            filaSubmissao.setVersao("1");
        }
        filaSubmissao.setDestino(destino);
        filaSubmissao.setDtLimiteSubmissao(txtDataLimite.getText().toString());
        filaSubmissao.setIdioma(txtIdioma.getText().toString());
    }

    private boolean validarDados() {
        if (txtDestino.getSelectedItem() == null) {
            hlpDialog.getAlertDialog(this, "Atenção", "O Repositório para o envio da Publicação deve ser informado", "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    txtDestino.requestFocus();
                }
            });
            return false;
        }

        return true;
    }

    private class procDados extends AsyncTask<Void, Void, String> {
        private FilaSubmissao submissao;

        public procDados(FilaSubmissao submissao) {
            this.submissao = submissao;
        }

        @Override
        protected String doInBackground(Void... voids) {
            ctrlSubmissoes ctrlSubmissoes = new ctrlSubmissoes(filaSubmissao);
            String msg = "";
            try {
                if (filaSubmissao.getId()==null){
                    return ctrlSubmissoes.criar();
                }
                else{
                    return ctrlSubmissoes.atualizar();
                }
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                msg = excPassaErro.getMessage();
            }
            return msg;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewPublicacaoEdit.this, "Aguarde", "Enviando solicitação...");
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            final String finalResult = result;
            hlpDialog.getAlertDialog(viewPublicacaoEdit.this, "Atenção", result, "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    if (finalResult.trim().equals("Submissão registrada com sucesso")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("usuario", usuario);
                        Intent intent = new Intent(viewPublicacaoEdit.this, viewPublicacao.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        txtIdioma.requestFocus();
                    }
                }
            });
        }
    }

    private class pouplarDados extends AsyncTask<Void, Void, List<Destino>> {

        @Override
        protected List<Destino> doInBackground(Void... voids) {
            ctrlDestino ctrlDestino = new ctrlDestino(new Destino());
            List<Destino> destinoList = new ArrayList<Destino>();

            try {
                return ctrlDestino.obterAll();
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                String msg = excPassaErro.getMessage();
                hlpDialog.getAlertDialog(viewPublicacaoEdit.this, "Atenção", msg, "Ok", new itfDialogGeneric() {
                    @Override
                    public void onButtonAction(boolean value) throws com.rhcloud.papers.excecoes.excPassaErro {
                    }
                });
            }
            return destinoList;

        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewPublicacaoEdit.this, "Aguarde", "Carregando dados...");
        }

        @Override
        protected void onPostExecute(List<Destino> result) {
            progressDialog.dismiss();

            adpDestino = new adpDestinos(viewPublicacaoEdit.this, android.R.layout.simple_spinner_dropdown_item, result);
            txtDestino.setAdapter(adpDestino);
            if (filaSubmissao.getId()!=null){
                for (int i=0; i<result.size(); i++){
                    if (result.get(i).getId().equals(filaSubmissao.getDestino().getId())){
                        txtDestino.setSelection(i);
                    }
                }
                adpDestino.notifyDataSetChanged();
            }

            txtDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    destino = (Destino) adapterView.getItemAtPosition(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }

    }
}



