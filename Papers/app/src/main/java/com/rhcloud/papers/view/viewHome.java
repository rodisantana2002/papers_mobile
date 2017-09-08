package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhcloud.papers.Principal;
import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlAutentication;
import com.rhcloud.papers.control.ctrlPessoa;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpConstants;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.model.entity.Pessoa;
import com.rhcloud.papers.model.entity.Usuario;

public class viewHome extends AppCompatActivity implements View.OnClickListener{
    private ctrlAutentication ctrlAutentication;
    private Usuario usuario;
    private SharedPreferences sharedPreferences;
    private TextView lblUsuario, lblDtUltAcesso;
    private ImageButton btnPerfil;
    private ImageView imgUsuario;

    private ProgressDialog progressDialog;
    private procDados procDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onResume() {
        prepararControles();
        super.onResume();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_documentos:
                    carregarDocumentos();
                    return true;
                case R.id.navigation_autores:
                    carregarAutores();
                    return true;
                case R.id.navigation_servicos:
                    carregarServicos();
                    return true;
            }
            return false;
        }
    };

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

    private void efetuarLogout() {
        ctrlAutentication = new ctrlAutentication(usuario);
        sharedPreferences = getSharedPreferences(hlpConstants.MYPREFERENCES, Context.MODE_PRIVATE);
        try {
            ctrlAutentication.efetuarLogin();
        } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {}

        Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        finishAffinity();
        Intent intent = new Intent(this, Principal.class);
        startActivity(intent);
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

        lblDtUltAcesso = (TextView) findViewById(R.id.lblUltAcesso);
        lblUsuario = (TextView) findViewById(R.id.lblUsuario);
        btnPerfil = (ImageButton) findViewById(R.id.btnPerfil);
        imgUsuario = (ImageView) findViewById(R.id.imgUsuario);

        lblUsuario.setText("olá, " + usuario.getPessoa().getPrimeiroNome() );
        lblDtUltAcesso.setText("conectado no sistema desde " + usuario.getDtUltAcesso().substring(0,5) + " às " + usuario.getDtUltAcesso().substring(11,16));

        btnPerfil.setOnClickListener(this);

        //carregarFoto();
        procDados = new procDados(usuario);
        procDados.execute();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btnPerfil.getId()){
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);

            Intent intent = new Intent(viewHome.this, viewPerfil.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtras(bundle);
            startActivity(intent);
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
            String msg = "";
            try {
                usuario.setPessoa(ctrlPessoa.obterByID(usuario.getPessoa().getId()));
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                msg = excPassaErro.getMessage();
            }
            return msg;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewHome.this, "Aguarde", "Carregando dados...");
        }

        @Override
        protected void onPostExecute(final String result) {
            progressDialog.dismiss();
            if (usuario.getPessoa().getFoto()==null){
                imgUsuario.setImageDrawable(getDrawable(R.drawable.ic_account_circle_black_48dp));
            }
            else {
                Bitmap bmUser = BitmapFactory.decodeByteArray(usuario.getPessoa().getFoto(), 0, usuario.getPessoa().getFoto().length);
                imgUsuario.setImageBitmap(bmUser);
            }

        }
    }
}
