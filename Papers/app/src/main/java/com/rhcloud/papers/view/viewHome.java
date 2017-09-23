package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rhcloud.papers.Principal;
import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlAutentication;
import com.rhcloud.papers.control.ctrlNotificacao;
import com.rhcloud.papers.control.ctrlPessoa;
import com.rhcloud.papers.control.ctrlPessoaFoto;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.helpers.generic.hlpConstants;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.model.entity.Notificacao;
import com.rhcloud.papers.model.entity.PessoaFoto;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.model.transitorio.Acao;
import com.rhcloud.papers.view.adapters.adpAcoes;
import com.rhcloud.papers.view.decorator.dividerItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class viewHome extends AppCompatActivity implements View.OnClickListener{
    private ctrlAutentication ctrlAutentication;
    private Usuario usuario;
    private PessoaFoto pessoaFoto;

    private SharedPreferences sharedPreferences;
    private TextView lblUsuario, lblDtUltAcesso;
    private ImageButton btnNotificacoes;
    private ImageView imgUsuario;

    private procDados procDados;
    private procLogout procLogout;
    private ProgressDialog progressDialog;

    private RecyclerView recyclerView;
    private List<Acao> lstAcoes;
    private adpAcoes mAdapter;
    private LinearLayout frameAcoes;

    private Integer totalNotificacoesPendentes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home);
        popularListaAcoes();
    }

    @Override
    protected void onResume() {
        prepararControles();
        super.onResume();
    }

    private void popularListaAcoes() {
        lstAcoes = new ArrayList<Acao>();

        Acao acao = new Acao();
        acao.setId(1);
        acao.setNomeAcao("Publicações");
        acao.setComentarioAcao("acompanhe e gerencie as suas publicações.");
        acao.setImgAcao(R.drawable.ic_send_black_18dp);
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(2);
        acao.setNomeAcao("Artigos");
        acao.setComentarioAcao("crie e atualize seus artigos.");
        acao.setImgAcao(R.drawable.ic_description_black_24dp);
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(3);
        acao.setNomeAcao("Autores");
        acao.setComentarioAcao("registre novos autores no grupo de autores do sistema.");
        acao.setImgAcao(R.drawable.ic_group_black_24dp);
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(4);
        acao.setNomeAcao("Repositórios Publicação");
        acao.setComentarioAcao("mantenha a lista de repositórios de publicações atualizada.");
        acao.setImgAcao(R.drawable.ic_account_balance_black_24dp);
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(5);
        acao.setNomeAcao("Perfil do Usuário");
        acao.setComentarioAcao("mantenha os dados do seu perfil atualizados.");
        acao.setImgAcao(R.drawable.ic_settings_black_24dp);
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(6);
        acao.setNomeAcao("Sair do Sistema");
        acao.setComentarioAcao("encerre o sistema e retorne a tela de login.");
        acao.setImgAcao(R.drawable.ic_exit_to_app_black_24dp);
        lstAcoes.add(acao);

        prepararLista();
    }

    private void prepararLista(){
        recyclerView = (RecyclerView) findViewById(R.id.listaAcoesMenu);

        mAdapter = new adpAcoes(viewHome.this, lstAcoes);
        mAdapter.setOnItemClickListener(new itfOnItemClickListener<Acao>() {
            @Override
            public void onItemClick(Acao item) {
                if(item.getId()==1){
                    carregarPublicacoes();
                }
                else  if(item.getId()==2){
                    carregarDocumentos();
                }
                else if(item.getId()==3){
                    carregarAutores();
                }
                else if(item.getId()==4){
                    carregarServicos();
                }
                else if(item.getId()==5){
                    carregarConfiguracoes();
                }
                else if(item.getId()==6){
                    efetuarLogout();
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new dividerItemDecorator(this, LinearLayoutManager.VERTICAL,100));
        recyclerView.setAdapter(mAdapter);
    }

    private void carregarConfiguracoes(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);

        Intent intent = new Intent(viewHome.this, viewPerfil.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void carregarNotificacoes() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);

        Intent intent = new Intent(viewHome.this, viewNotificacao.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void carregarAutores() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);

        Intent intent = new Intent(viewHome.this, viewAutor.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void carregarServicos() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);

        Intent intent = new Intent(viewHome.this, viewRepositorio.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void carregarDocumentos() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);

        Intent intent = new Intent(viewHome.this, viewDocumento.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void carregarPublicacoes(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario);

        Intent intent = new Intent(viewHome.this, viewPublicacao.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void efetuarLogout() {
        procLogout = new procLogout();
        procLogout.execute();
    }

    private void prepararControles() {
        //populaobjeto Usuario
        sharedPreferences = getSharedPreferences(hlpConstants.MYPREFERENCES, Context.MODE_PRIVATE);
        usuario = new Usuario();
        usuario.setId(Integer.valueOf(sharedPreferences.getString(hlpConstants.PREF_ID, "0")));
        usuario.setToken(sharedPreferences.getString(hlpConstants.PREF_TOKEN, ""));
        usuario.setDtUltAcesso(sharedPreferences.getString(hlpConstants.PREF_ULTACESSO, ""));
        usuario.getPessoa().setEmail(sharedPreferences.getString(hlpConstants.PREF_EMAIL, ""));
        usuario.getPessoa().setPrimeiroNome(sharedPreferences.getString(hlpConstants.PREF_PRIMEIRO_NAME, ""));
        usuario.getPessoa().setSegundoNome(sharedPreferences.getString(hlpConstants.PREF_SEGUNDO_NAME, ""));
        usuario.getPessoa().setId(Integer.valueOf(sharedPreferences.getString(hlpConstants.PREF_PESSOA_ID, "")));

        totalNotificacoesPendentes = 0;
        lblDtUltAcesso = (TextView) findViewById(R.id.lblUltAcesso);
        lblUsuario = (TextView) findViewById(R.id.lblUsuario);
        btnNotificacoes = (ImageButton) findViewById(R.id.btnNotificacoes);
        imgUsuario = (ImageView) findViewById(R.id.imgUsuario);

        lblUsuario.setText("Olá, " + usuario.getPessoa().getPrimeiroNome());
        lblDtUltAcesso.setText("conectado no sistema desde " + usuario.getDtUltAcesso().substring(0,5) + " às " + usuario.getDtUltAcesso().substring(11,16));

        btnNotificacoes.setOnClickListener(this);
        frameAcoes = (LinearLayout) findViewById(R.id.frameAcoes);

        procDados = new procDados(usuario);
        procDados.execute();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btnNotificacoes.getId()){
            carregarNotificacoes();
        }
    }

    private class procDados extends AsyncTask<Void, Void, String> {
        private Usuario usuario;

        public procDados(Usuario usuario){
            this.usuario = usuario;
        }

        @Override
        protected String doInBackground(Void... voids) {
            ctrlPessoa ctrlPessoa = new ctrlPessoa(usuario.getPessoa());
            ctrlNotificacao ctrlNotificacao = new ctrlNotificacao(new Notificacao());

            String msg = "";
            try {
                usuario.setPessoa(ctrlPessoa.obterByID(usuario.getPessoa().getId()));
                ctrlPessoaFoto ctrlPessoaFoto = new ctrlPessoaFoto(new PessoaFoto());
                pessoaFoto = ctrlPessoaFoto.obterByAutorId(usuario.getPessoa().getId());
                totalNotificacoesPendentes = ctrlNotificacao.obterTotalPendentesByAutor(usuario.getPessoa().getId().toString());

            } catch (excPassaErro excPassaErro) {
                msg = excPassaErro.getMessage();
                hlpDialog.getAlertDialog(viewHome.this, "Atenção", msg, "Ok", new itfDialogGeneric() {
                    @Override
                    public void onButtonAction(boolean value) {
                    }
                });
            }
            return msg;
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onPostExecute(final String result) {
            if ((pessoaFoto==null) || (pessoaFoto.getFoto()==null)){
                imgUsuario.setImageDrawable(getDrawable(R.drawable.ic_account_circle_black_48dp));
            }
            else {
                Bitmap bmUser = BitmapFactory.decodeByteArray(pessoaFoto.getFoto(), 0, pessoaFoto.getFoto().length);
                imgUsuario.setImageBitmap(bmUser);
            }
            if(totalNotificacoesPendentes == 0){
                btnNotificacoes.setImageResource(R.drawable.ic_notifications_none_black_24dp);
            }
            else{
                btnNotificacoes.setImageResource(R.drawable.ic_notifications_active_black_24dp);
            }
            btnNotificacoes.setVisibility(View.VISIBLE);
        }
    }

    private class procLogout extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            ctrlAutentication = new ctrlAutentication(usuario);
            sharedPreferences = getSharedPreferences(hlpConstants.MYPREFERENCES, Context.MODE_PRIVATE);
            try {
                ctrlAutentication.efetuarLogin();
            } catch (excPassaErro excPassaErro) {
                String msg = excPassaErro.getMessage();
                hlpDialog.getAlertDialog(viewHome.this, "Atenção", msg, "Ok", new itfDialogGeneric() {
                    @Override
                    public void onButtonAction(boolean value) {
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewHome.this, "Aguarde", "Encerrando o sistema...");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            finishAffinity();
            Intent intent = new Intent(viewHome.this, Principal.class);
            startActivity(intent);
        }
    }

}

