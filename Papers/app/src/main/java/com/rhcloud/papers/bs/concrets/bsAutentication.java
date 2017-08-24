package com.rhcloud.papers.bs.concrets;

import android.os.StrictMode;

import com.google.gson.Gson;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.generic.hlpConstants;
import com.rhcloud.papers.helpers.rest.hlpConnectionURL;
import com.rhcloud.papers.model.entity.Usuario;

/**
 * Created by Rodolfo on 02/12/16.
 */

public class bsAutentication {
    private final Gson gson;
    private hlpConnectionURL connRest;
    private Usuario usuario;

    public bsAutentication() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        gson = new Gson();
        connRest = new hlpConnectionURL();
    }

    public Usuario efetuarLogin(Usuario entity) throws excPassaErro {
        usuario = new Usuario();
        String response = connRest.post(hlpConstants.URL_BASE + hlpConstants.URL_AUTENTICATION + "login/", gson.toJson(entity));
        usuario = gson.fromJson(response, Usuario.class);

        return usuario;
    }

    public void efetuarLogout(Usuario entity) throws excPassaErro{
        usuario = new Usuario();
        String response = connRest.post(hlpConstants.URL_BASE + hlpConstants.URL_AUTENTICATION + "logout/", gson.toJson(entity));
    }
}
