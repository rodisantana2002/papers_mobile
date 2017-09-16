package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rhcloud.papers.R;

import com.rhcloud.papers.control.ctrlPessoa;
import com.rhcloud.papers.control.ctrlPessoaFoto;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.model.entity.PessoaFoto;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.model.transitorio.Acao;
import com.rhcloud.papers.view.adapters.adpAcoes;
import com.rhcloud.papers.view.decorator.dividerItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class viewPerfil extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Acao> lstAcoes;
    private adpAcoes mAdapter;
    private ImageView imgFoto;
    private TextView lblNomeCompleto;
    private Usuario usuario;
    private PessoaFoto pessoaFoto;

    private ProgressDialog progressDialog;
    private procDados procDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_perfil);
        popularListaAcoes(getIntent().getExtras());
        prepararComponenetes();
    }

    private void popularListaAcoes(Bundle bundle) {
        lstAcoes = new ArrayList<Acao>();
        if (bundle!=null){
            usuario = (Usuario) bundle.getSerializable("usuario");
        }

        Acao acao = new Acao();
        acao.setId(1);
        acao.setNomeAcao("Minha Foto");
        acao.setComentarioAcao("mantenha sua foto do perfil atualizada.");
        acao.setImgAcao(R.drawable.ic_person_pin_black_18dp);
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(2);
        acao.setNomeAcao("Dados Pessoais");
        acao.setComentarioAcao("mantenha seus dados de contato atualizados em seu perfil.");
        acao.setImgAcao(R.drawable.ic_chrome_reader_mode_black_18dp);
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(3);
        acao.setNomeAcao("Resumo Profissional");
        acao.setComentarioAcao("conte-nos um pouco sobre sua experiência profissional.");
        acao.setImgAcao(R.drawable.ic_reorder_black_18dp);
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(4);
        acao.setNomeAcao("Segurança");
        acao.setComentarioAcao("cuide de sua senha de acesso ao sistema.");
        acao.setImgAcao(R.drawable.ic_https_black_18dp);
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(5);
        acao.setNomeAcao("Início");
        acao.setComentarioAcao("para outras opções retorne a tela inicial.");
        acao.setImgAcao(R.drawable.ic_chevron_left_black_18dp);
        lstAcoes.add(acao);
    }

    private void prepararComponenetes(){
        recyclerView = (RecyclerView) findViewById(R.id.lstAcoesPerfil);

        imgFoto = (ImageView) findViewById(R.id.imgUsuarioPerfil);
        lblNomeCompleto = (TextView) findViewById(R.id.lblUsuarioPerfil);

        mAdapter = new adpAcoes(viewPerfil.this, lstAcoes);
        mAdapter.setOnItemClickListener(new itfOnItemClickListener<Acao>() {
            @Override
            public void onItemClick(Acao item) {
                if(item.getId()==1){
                    carregarImagemDispositivo();
                }
                else  if(item.getId()==2){
                    alterarDadosPessoais();
                }
                else if(item.getId()==3){
                    alterarResumoProficcional();
                }
                else if(item.getId()==4){
                    alterarSenha();
                }
                else if(item.getId()==5){
                    retornarMenu();
                }
            }
        });

        //carrega cabecalho
        lblNomeCompleto.setText(usuario.getPessoa().getPrimeiroNome() + " " + usuario.getPessoa().getSegundoNome());
        procDados = new procDados(usuario);
        procDados.execute();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new dividerItemDecorator(this, LinearLayoutManager.VERTICAL,100));
        recyclerView.setAdapter(mAdapter);
    }

    private void carregarImagemDispositivo() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);

        Intent intent = new Intent(this, viewAlterarFoto.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void alterarSenha(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);

        Intent intent = new Intent(this, viewAlterarSenha.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void alterarDadosPessoais(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);

        Intent intent = new Intent(this, viewAlterarDadosPessoais.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void alterarResumoProficcional(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);

        Intent intent = new Intent(this, viewAlterarResumoProfissional.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void retornarMenu(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);

        Intent intent = new Intent(this, viewHome.class);

        intent.putExtras(bundle);
        startActivity(intent);
    }


    private class procDados extends AsyncTask<Void, Void, String> {
        private Usuario usuario;

        public procDados(Usuario usuario){
            this.usuario = usuario;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String msg = "";
            try {
                ctrlPessoaFoto ctrlPessoaFoto = new ctrlPessoaFoto(new PessoaFoto());
                pessoaFoto = ctrlPessoaFoto.obterByAutorId(usuario.getPessoa().getId());
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                msg = excPassaErro.getMessage();
            }
            return msg;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewPerfil.this, "Aguarde", "Carregando dados...");
        }

        @Override
        protected void onPostExecute(final String result) {
            progressDialog.dismiss();

            if (pessoaFoto.getFoto()==null){
                imgFoto.setImageDrawable(getDrawable(R.drawable.ic_account_circle_black_48dp));
            }
            else {
                Bitmap bmUser = BitmapFactory.decodeByteArray(pessoaFoto.getFoto(), 0, pessoaFoto.getFoto().length);
                imgFoto.setImageBitmap(bmUser);
            }
        }
    }
}
