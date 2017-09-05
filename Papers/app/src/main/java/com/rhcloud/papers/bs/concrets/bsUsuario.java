package com.rhcloud.papers.bs.concrets;

import android.os.StrictMode;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rhcloud.papers.bs.core.itfGeneric;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.generic.hlpConstants;
import com.rhcloud.papers.helpers.rest.hlpConnectionURL;
import com.rhcloud.papers.model.entity.Pessoa;
import com.rhcloud.papers.model.entity.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodolfosantana on 29/08/17.
 */

public class bsUsuario implements itfGeneric<Usuario>{
    private final Gson gson;
    private hlpConnectionURL connRest;
    private Usuario usuario;
    private String token;

    public bsUsuario(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        gson = new Gson();
        connRest = new hlpConnectionURL();
    }

    @Override
    public String create(Usuario entity) throws excPassaErro {
        return connRest.put(hlpConstants.URL_BASE + hlpConstants.URL_USUARIO + "add/", gson.toJson(entity));
    }

    @Override
    public String delete(Integer id) throws excPassaErro{
        return connRest.delete(hlpConstants.URL_BASE + hlpConstants.URL_USUARIO + id);
    }

    public String alterarSenha(Usuario entity) throws excPassaErro {
        connRest.addHearders("email", entity.getPessoa().getEmail().toString());
        connRest.addHearders("token", entity.getToken().toString());
        return connRest.post(hlpConstants.URL_BASE + hlpConstants.URL_USUARIO + "alterarsenha/", gson.toJson(entity));
    }

    public String isUsuario(Usuario entity) throws excPassaErro{
        return connRest.post(hlpConstants.URL_BASE + hlpConstants.URL_USUARIO + "isusuario/", gson.toJson(entity));
    }

    //não precisa implementar - questões de segurança
    @Override
    public String update(Usuario entity) throws excPassaErro{return null;}
    @Override
    public Usuario findByID(Integer id) throws excPassaErro{return null;}
    @Override
    public List<Usuario> findAll() throws excPassaErro{return null;}
}
