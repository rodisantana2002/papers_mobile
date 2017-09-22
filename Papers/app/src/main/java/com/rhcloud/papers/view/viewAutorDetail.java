package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlPessoa;
import com.rhcloud.papers.control.ctrlPessoaFoto;
import com.rhcloud.papers.control.ctrlUsuario;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.model.entity.Pessoa;
import com.rhcloud.papers.model.entity.PessoaFoto;
import com.rhcloud.papers.model.entity.Usuario;

public class viewAutorDetail extends AppCompatActivity implements View.OnClickListener{
    private TextView lblNome, lblEmail, lblInstituicao, lblLocal, lblFone, lblResumoProfissional, lblAviso;
    private Pessoa pessoa;
    private Usuario usuario;
    private Button btnEditar;
    private ImageButton btnVoltar;
    private ImageView imgAutor;
    private ProgressDialog progressDialog;
    private procDados procDados;
    private procDadosFoto procDadosFoto;
    private PessoaFoto pessoaFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_autor_detail);

        prepararComponentes(getIntent().getExtras());
        procDados = new procDados(pessoa);
        procDados.execute();

        procDadosFoto = new procDadosFoto(pessoa);
        procDadosFoto.execute();
    }


    private void prepararComponentes(Bundle bundle) {
        pessoa = (Pessoa) bundle.getSerializable("autor");
        usuario = (Usuario) bundle.getSerializable("usuario");

        btnEditar = (Button) findViewById(R.id.btnEditarAutor);
        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarAutorDetail);

        imgAutor = (ImageView) findViewById(R.id.imgAutor);
        lblNome = (TextView) findViewById(R.id.lblAutorName);
        lblEmail = (TextView) findViewById(R.id.lblEmail);
        lblFone= (TextView) findViewById(R.id.lblFone);
        lblInstituicao = (TextView) findViewById(R.id.lblInstituicao);
        lblLocal = (TextView) findViewById(R.id.lblLocalizacao);
        lblResumoProfissional = (TextView) findViewById(R.id.lblResumoProfissional);
        lblAviso = (TextView) findViewById(R.id.lblAviso);

        btnEditar.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);

        lblNome.setText(pessoa.getPrimeiroNome() + " " + pessoa.getSegundoNome());
        lblEmail.setText(pessoa.getEmail());
        lblFone.setText("(" + (pessoa.getDDD()!= null ? pessoa.getDDD() : "") + ") " +
                (pessoa.getFoneCelular()!=null ? pessoa.getFoneCelular():""));
        lblInstituicao.setText(pessoa.getInstituicao()!=null ? pessoa.getInstituicao() : "");
        lblLocal.setText((pessoa.getCidade()!=null ? pessoa.getCidade() + " " : "")  +
                (pessoa.getEstado()!=null ? pessoa.getEstado() + " " : "") +
                (pessoa.getPais()!=null ? pessoa.getPais(): ""));
        lblResumoProfissional.setText((pessoa.getBiografia()!=null ? pessoa.getBiografia() : "Não foram registradas informaçôes profissionais até o momento"));
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == btnEditar.getId()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("autor", pessoa);
            bundle.putSerializable("usuario", usuario);
            intent = new Intent(viewAutorDetail.this, viewAutorEdit.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        if (view.getId() == btnVoltar.getId()){
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            intent = new Intent(viewAutorDetail.this, viewAutor.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private class procDados extends AsyncTask<Void, Void, String> {
        private Pessoa pessoa;

        public procDados(Pessoa pessoa) {
            this.pessoa = pessoa;
        }

        @Override
        protected String doInBackground(Void... voids) {
            Usuario usuario = new Usuario();
            usuario.setPessoa(pessoa);
            ctrlUsuario ctrlUsuario = new ctrlUsuario(usuario);
            try {
                String valor = ctrlUsuario.isUsuario();
                return valor;
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                excPassaErro.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onPostExecute(String result) {
            if (result!=null && result.equals("0\n")){
                btnEditar.setVisibility(View.VISIBLE);
                lblAviso.setText("Atenção somente os dados básicos do Autor podem ser adicionados/alterados. Caso o Autor venha a se tornar um Usuário do sistema, a edição não será mais permitida.");
                lblAviso.setTextColor(Color.DKGRAY);
            }
            else {
                btnEditar.setVisibility(View.GONE);
                lblAviso.setText("Atenção o Autor selecionado já é Usuário do sistema, agora somente ele poderá atualizar os dados do seu Perfil.");
                lblAviso.setTextColor(Color.RED);
            }
        }
    }

    private class procDadosFoto extends AsyncTask<Void, Void, String> {
        private Pessoa pessoa;

        public procDadosFoto(Pessoa pessoa){
            this.pessoa = pessoa;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String msg = "";
            try {
                ctrlPessoaFoto ctrlPessoaFoto = new ctrlPessoaFoto(new PessoaFoto());
                pessoaFoto = ctrlPessoaFoto.obterByAutorId(pessoa.getId());
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                msg = excPassaErro.getMessage();
                hlpDialog.getAlertDialog(viewAutorDetail.this, "Atenção", msg, "Ok", new itfDialogGeneric() {
                    @Override
                    public void onButtonAction(boolean value) throws com.rhcloud.papers.excecoes.excPassaErro {
                    }
                });

            }
            return msg;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewAutorDetail.this, "Aguarde", "Carregando dados...");
        }

        @Override
        protected void onPostExecute(final String result) {
            progressDialog.dismiss();
            if ((pessoaFoto==null) || (pessoaFoto.getFoto()==null)){
                imgAutor.setImageDrawable(getDrawable(R.drawable.ic_account_circle_black_48dp));
            }
            else {
                Bitmap bmUser = BitmapFactory.decodeByteArray(pessoaFoto.getFoto(), 0, pessoaFoto.getFoto().length);
                imgAutor.setImageBitmap(bmUser);
            }
        }
    }
}
