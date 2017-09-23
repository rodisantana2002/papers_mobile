package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlNotificacao;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.core.itfOnItemClickListener;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.model.entity.Notificacao;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.model.enumeration.Status;
import com.rhcloud.papers.view.adapters.adpNotificacoes;
import com.rhcloud.papers.view.decorator.dividerItemDecorator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class viewNotificacao extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HashMap<Status, List<Notificacao>> lstNotificacoes;
    private adpNotificacoes mAdapter;
    private ImageButton btnVoltar;
    private Usuario usuario;
    private ProgressDialog progressDialog;
    private procDados procDados;
    private TextView txtNenhumRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_noticacao);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_notificacoes);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        popularLista(getIntent().getExtras());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_recebidas:
                    List<Notificacao> lstNotificacaoList = new ArrayList<Notificacao>();
                    if(lstNotificacoes!=null && !lstNotificacoes.isEmpty()) {
                        lstNotificacaoList.addAll(lstNotificacoes.get(com.rhcloud.papers.model.enumeration.Status.LIDA));
                        lstNotificacaoList.addAll(lstNotificacoes.get(com.rhcloud.papers.model.enumeration.Status.PENDENTE));
                        Collections.reverse(lstNotificacaoList);
                    }
                    prepararLista(lstNotificacaoList);
                    return true;
                case R.id.navigation_arquivadas:
                    if(lstNotificacoes!=null && !lstNotificacoes.isEmpty()) {
                        prepararLista(lstNotificacoes.get(Status.ARQUIVADA));
                    }
                    return true;
            }
            return false;
        }

    };

    private void popularLista(Bundle bundle) {
        lstNotificacoes = new HashMap<Status, List<Notificacao>>();

        if (bundle != null) {
            usuario = (Usuario) bundle.getSerializable("usuario");
        }

        prepararComponenetes();
        procDados = new procDados();
        procDados.execute();
    }

    private void prepararComponenetes() {
        recyclerView = (RecyclerView) findViewById(R.id.lstNotificacoes);
        recyclerView.addItemDecoration(new dividerItemDecorator(viewNotificacao.this, LinearLayoutManager.VERTICAL, 90));

        txtNenhumRegistro = (TextView) findViewById(R.id.txtNenhumRegistroNotificacao);
        btnVoltar = (ImageButton) findViewById(R.id.btnVoltarHomeNotificacoes);
        btnVoltar.setOnClickListener(viewNotificacao.this);
    }

    public void onClick(View view) {
        Intent intent;
        if (view.getId() == btnVoltar.getId()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            intent = new Intent(this, viewHome.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private void prepararLista(List<Notificacao> notificacaoList) {
        mAdapter = new adpNotificacoes(viewNotificacao.this, notificacaoList);
        mAdapter.setOnItemClickListener(new itfOnItemClickListener<Notificacao>() {

            @Override
            public void onItemClick(Notificacao item) {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("notificacao", item);
                    bundle.putSerializable("usuario", usuario);
                    Intent intent = new Intent(viewNotificacao.this, viewNotificacaoDetail.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(viewNotificacao.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        if (notificacaoList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            txtNenhumRegistro.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            txtNenhumRegistro.setVisibility(View.GONE);
        }
    }

    private class procDados extends AsyncTask<Void, Void, HashMap<Status, List<Notificacao>>> {

        @Override
        protected HashMap<com.rhcloud.papers.model.enumeration.Status, List<Notificacao>> doInBackground(Void... voids) {
            lstNotificacoes = new HashMap<com.rhcloud.papers.model.enumeration.Status, List<Notificacao>>();

            try {
                ctrlNotificacao ctrlNotificacao = new ctrlNotificacao(new Notificacao());
                lstNotificacoes = ctrlNotificacao.obterAllByAutor(String.valueOf(usuario.getPessoa().getId()));
                return lstNotificacoes;

            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                String msg = excPassaErro.getMessage();
                hlpDialog.getAlertDialog(viewNotificacao.this, "Atenção", msg, "Ok", new itfDialogGeneric() {
                    @Override
                    public void onButtonAction(boolean value) throws com.rhcloud.papers.excecoes.excPassaErro {
                    }
                });
            }
            return lstNotificacoes;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewNotificacao.this, "Aguarde", "Carregando dados...");
        }

        @Override
        protected void onPostExecute(HashMap<com.rhcloud.papers.model.enumeration.Status, List<Notificacao>> statusListHashMap) {
            progressDialog.dismiss();
            List<Notificacao> lstNotificacaoList = new ArrayList<Notificacao>();
            if(lstNotificacoes!=null && !lstNotificacoes.isEmpty()) {
                lstNotificacaoList.addAll(lstNotificacoes.get(com.rhcloud.papers.model.enumeration.Status.LIDA));
                lstNotificacaoList.addAll(lstNotificacoes.get(com.rhcloud.papers.model.enumeration.Status.PENDENTE));
                Collections.reverse(lstNotificacaoList);
            }
            prepararLista(lstNotificacaoList);
        }
    }
}

