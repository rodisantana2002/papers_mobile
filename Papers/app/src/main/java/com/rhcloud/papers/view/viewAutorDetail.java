package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlPessoa;
import com.rhcloud.papers.control.ctrlUsuario;
import com.rhcloud.papers.model.entity.Pessoa;
import com.rhcloud.papers.model.entity.Usuario;

public class viewAutorDetail extends Activity implements View.OnClickListener{
    private TextView lblNome, lblEmail, lblInstituicao, lblLocal, lblFone, lblResumoProfissional, lblAviso;
    private Pessoa pessoa;
    private Button btnEditar;
    private ImageButton btnVoltar;
    private ImageView imgAutor;
    private ProgressDialog progressDialog;
    private procDados procDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_autor_detail);

        prepararComponentes(getIntent().getExtras());
        procDados = new procDados(pessoa);
        procDados.execute();
    }


    private void prepararComponentes(Bundle bundle) {
        pessoa = (Pessoa) bundle.getSerializable("autor");

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

        if (pessoa.getFoto()==null){
            imgAutor.setImageDrawable(getDrawable(R.drawable.ic_account_circle_black_48dp));
        }
        else {
            Bitmap bmUser = BitmapFactory.decodeByteArray(pessoa.getFoto(), 0, pessoa.getFoto().length);
            imgAutor.setImageBitmap(bmUser);
        }
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
            intent = new Intent(viewAutorDetail.this, viewAutorEdit.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        if (view.getId() == btnVoltar.getId()){
            intent = new Intent(viewAutorDetail.this, viewAutor.class);
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
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewAutorDetail.this, "Aguarde", "Enviando solicitação...");
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (result!=null && result.equals("0\n")){
                btnEditar.setVisibility(View.VISIBLE);
                lblAviso.setText("Atenção somente os dados básicos do Autor podem ser adicionados/alterados. Caso o Autor venha a se tornar um Usuário do sistema, a edição não será mais permitida.");
                lblAviso.setTextColor(Color.DKGRAY);
            }
            else {
                btnEditar.setVisibility(View.GONE);
                lblAviso.setText("Atenção o Autor é um Usuário do sistema, dessa maneira as alterações não podem ser realizadas por outros usuários do sistema.");
                lblAviso.setTextColor(Color.RED);
            }
        }
    }
}
