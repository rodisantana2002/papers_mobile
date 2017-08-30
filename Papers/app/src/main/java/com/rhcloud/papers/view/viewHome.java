package com.rhcloud.papers.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlAutentication;
import com.rhcloud.papers.helpers.generic.hlpConstants;
import com.rhcloud.papers.model.entity.Usuario;

public class viewHome extends AppCompatActivity implements View.OnClickListener{
    private ctrlAutentication ctrlAutentication;
    private Usuario usuario;
    private SharedPreferences sharedPreferences;
    private TextView lblUsuario, lblDtUltAcesso;
    private ImageButton btnPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        prepararControles();
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
                    return true;
                case R.id.navigation_autores:
                    return true;
                case R.id.navigation_servicos:
                    return true;
                case R.id.navigation_sair:
                    efetuarLogout();
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
        usuario.getPessoa().setFoto(sharedPreferences.getString(hlpConstants.PREF_FOTO, ""));

        lblDtUltAcesso = (TextView) findViewById(R.id.lblUltAcesso);
        lblUsuario = (TextView) findViewById(R.id.lblUsuario);
        btnPerfil = (ImageButton) findViewById(R.id.btnPerfil);

        lblUsuario.setText("olá, " + usuario.getPessoa().getPrimeiroNome() );
        lblDtUltAcesso.setText("último acesso foi em " + usuario.getDtUltAcesso());

        btnPerfil.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btnPerfil.getId()){
            Intent intent = new Intent(viewHome.this, viewPerfil.class);
            startActivity(intent);
        }
    }
}
