package com.rhcloud.papers.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlAutentication;
import com.rhcloud.papers.control.ctrlPessoa;
import com.rhcloud.papers.helpers.generic.hlpConstants;
import com.rhcloud.papers.model.entity.Pessoa;
import com.rhcloud.papers.model.entity.Usuario;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class viewHome extends AppCompatActivity implements View.OnClickListener{
    private ctrlAutentication ctrlAutentication;
    private Usuario usuario;
    private SharedPreferences sharedPreferences;
    private TextView lblUsuario, lblDtUltAcesso;
    private ImageButton btnPerfil;
    private ImageView imgUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //prepararControles();
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
                    popularTela();
                    efetuarLogout();
                    return true;
                case R.id.navigation_autores:
                    return true;
                case R.id.navigation_servicos:
                    return true;
            }
            return false;
        }
    };

    private void popularTela() {

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
        finish();
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
        lblDtUltAcesso.setText("acessando o sistema desde " + usuario.getDtUltAcesso().substring(0,5) + " às " + usuario.getDtUltAcesso().substring(11,16));

        btnPerfil.setOnClickListener(this);

        carregarFoto();
    }

    private void carregarFoto()  {
         ctrlPessoa ctrlPessoa = new ctrlPessoa(usuario.getPessoa());
         try {
             usuario.setPessoa(ctrlPessoa.obterByID(usuario.getPessoa().getId()));
         } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
             excPassaErro.printStackTrace();
         }

        if (usuario.getPessoa().getFoto()==null){
            imgUsuario.setImageDrawable(getDrawable(R.drawable.ic_account_circle_black_48dp));
        }
        else {
            Bitmap bmUser = BitmapFactory.decodeByteArray(usuario.getPessoa().getFoto(), 0, usuario.getPessoa().getFoto().length);
            imgUsuario.setImageBitmap(bmUser);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btnPerfil.getId()){
            Intent intent = new Intent(viewHome.this, viewPerfil.class);
            startActivity(intent);
        }
    }

}
