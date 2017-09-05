package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.rhcloud.papers.model.entity.Pessoa;
import com.rhcloud.papers.model.entity.Usuario;

public class viewAutorEdit extends AppCompatActivity implements View.OnClickListener {
    private EditText txtPrimeiroNome, txtSegundoNome, txtEmailUsuario, txtInstituicao;
    private AutoCompleteTextView txtPais, txtEstado, txtCidade;
    private Usuario usuario;
    private Pessoa pessoa;
    private Button btnEnviar;
    private ImageButton btnVoltar, btnExcluirAutor;
    private ProgressDialog progressDialog;
    private procDados procDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_autor_edit);
        prepararComponentes(getIntent().getExtras());
    }

    private void prepararComponentes(Bundle bundle) {
        pessoa = (Pessoa) bundle.getSerializable("autor");

        String[] lstPaises = getResources().getStringArray(R.array.listPaises);
        String[] lstEstados = getResources().getStringArray(R.array.listEstados);
        String[] lstCapitais = getResources().getStringArray(R.array.listCapitais);

        ArrayAdapter adapterPais = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, lstPaises);
        ArrayAdapter adapterEstados = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, lstEstados);
        ArrayAdapter adapterCapitais = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, lstCapitais);

        btnEnviar = (Button) findViewById(R.id.btnEnviarAutor);
        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarAutorEdit);
        btnExcluirAutor = (ImageButton) findViewById(R.id.btnExcluirAutor);

        txtPrimeiroNome = (EditText) findViewById(R.id.txtPrimeiroNomeAutor);
        txtSegundoNome = (EditText) findViewById(R.id.txtSegundoNomeAutor);
        txtEmailUsuario = (EditText) findViewById(R.id.txtEmailUsuarioAutor);
        txtInstituicao = (EditText) findViewById(R.id.txtInstituicaoAutor);
        txtPais = (AutoCompleteTextView) findViewById(R.id.txtPaisAutor);
        txtEstado = (AutoCompleteTextView) findViewById(R.id.txtEstadoAutor);
        txtCidade = (AutoCompleteTextView) findViewById(R.id.txtCidadeAutor);

        btnEnviar.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);
        btnExcluirAutor.setOnClickListener(this);

        txtPrimeiroNome.setText(pessoa.getPrimeiroNome());
        txtSegundoNome.setText(pessoa.getSegundoNome());
        txtEmailUsuario.setText(pessoa.getEmail());
        txtInstituicao.setText(pessoa.getInstituicao());
        txtPais.setText(pessoa.getPais());
        txtEstado.setText(pessoa.getEstado());
        txtCidade.setText(pessoa.getCidade());

        txtPais.setAdapter(adapterPais);
        txtEstado.setAdapter(adapterEstados);
        txtCidade.setAdapter(adapterCapitais);

        if (pessoa.getId()==null){
            btnExcluirAutor.setVisibility(View.GONE);
        }
        else{
            btnExcluirAutor.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == btnEnviar.getId()) {
            if (validarDados()) {
                atualizarObjeto();
                procDados = new procDados(pessoa);
                procDados.execute();
            }
        }

        if (view.getId() == btnVoltar.getId()){
            intent = new Intent(viewAutorEdit.this, viewAutor.class);
            startActivity(intent);
        }

        if(view.getId() == btnExcluirAutor.getId()){
            hlpDialog.getConfirmDialog(viewAutorEdit.this, "Atenção", "Confirma a exclusão do Autor?", "Sim", "Não", false, new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    if (value){
                        ctrlPessoa ctrlPessoa = new ctrlPessoa(pessoa);
                        ctrlPessoa.remover();
                        Intent intent = new Intent(viewAutorEdit.this, viewAutor.class);
                        startActivity(intent);
                    }
                    else{
                        txtPrimeiroNome.requestFocus();
                    }
                }
            });
        }
    }

    private void atualizarObjeto() {
        pessoa.setPrimeiroNome(txtPrimeiroNome.getText().toString());
        pessoa.setSegundoNome(txtSegundoNome.getText().toString());
        pessoa.setEmail(txtEmailUsuario.getText().toString());
        pessoa.setInstituicao(txtInstituicao.getText().toString());
        pessoa.setPais(txtPais.getText().toString());
        pessoa.setEstado(txtEstado.getText().toString());
        pessoa.setCidade(txtCidade.getText().toString());
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
        private Pessoa pessoa;

        public procDados(Pessoa pessoa) {
            this.pessoa = pessoa;
        }

        @Override
        protected String doInBackground(Void... voids) {
            ctrlPessoa ctrlPessoa = new ctrlPessoa(pessoa);
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
            progressDialog = ProgressDialog.show(viewAutorEdit.this, "Aguarde", "Enviando solicitação...");
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            final String finalResult = result;
            hlpDialog.getAlertDialog(viewAutorEdit.this, "Atenção", result, "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    if (finalResult.trim().equals("Autor registrado com sucesso")) {
                        Intent intent = new Intent(viewAutorEdit.this, viewAutor.class);
                        startActivity(intent);
                    } else {
                        txtPrimeiroNome.requestFocus();
                    }
                }
            });
        }
    }
}
