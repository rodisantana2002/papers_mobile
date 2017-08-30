package com.rhcloud.papers.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.rhcloud.papers.R;

import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_perfil);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPerfil);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        popularListaAcoes();
        prepararComponenetes();
    }

    private void popularListaAcoes() {
        lstAcoes = new ArrayList<Acao>();

        Acao acao = new Acao();
        acao.setId(1);
        acao.setNomeAcao("Minha Foto");
        acao.setComentarioAcao("permite que a foto do perfil seja alterada.");
        acao.setImgAcao(getDrawable(R.drawable.ic_camera_alt_black_24dp));
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(2);
        acao.setNomeAcao("Dados Pessoais");
        acao.setComentarioAcao("atualizar os dados pessoais do seu perfil.");
        acao.setImgAcao(getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(3);
        acao.setNomeAcao("Resumo Profissional");
        acao.setComentarioAcao("registrar um breve resumo da sua experiência profissional.");
        acao.setImgAcao(getDrawable(R.drawable.ic_camera_alt_black_24dp));
        lstAcoes.add(acao);

        acao = new Acao();
        acao.setId(4);
        acao.setNomeAcao("Segurança");
        acao.setComentarioAcao("alter a sua senha de acesso ao sistema.");
        acao.setImgAcao(getDrawable(R.drawable.ic_camera_alt_black_24dp));
        lstAcoes.add(acao);
    }

    private void prepararComponenetes(){
        recyclerView = (RecyclerView) findViewById(R.id.lstAcoesPerfil);

        mAdapter = new adpAcoes(lstAcoes);
        mAdapter.setOnItemClickListener(new itfOnItemClickListener<Acao>() {
            @Override
            public void onItemClick(Acao item) {
                //colocar regras para as açõe;
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new dividerItemDecorator(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
    }
}
