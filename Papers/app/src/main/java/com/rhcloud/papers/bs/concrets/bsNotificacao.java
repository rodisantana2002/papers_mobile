package com.rhcloud.papers.bs.concrets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rhcloud.papers.bs.core.itfGeneric;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.generic.hlpConstants;
import com.rhcloud.papers.helpers.rest.hlpConnectionURL;
import com.rhcloud.papers.model.entity.Notificacao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodolfosantana on 19/09/17.
 */

public class bsNotificacao implements itfGeneric<Notificacao> {
    private Gson gson;
    private hlpConnectionURL connRest;
    private Notificacao notificacao;

    public bsNotificacao(){
        gson = new Gson();
        connRest = new hlpConnectionURL();
    }

    @Override
    public String create(Notificacao entity) throws excPassaErro {
        return connRest.put(hlpConstants.URL_BASE + hlpConstants.URL_NOTIFICACAO + "add/", gson.toJson(entity));
    }

    public List<Notificacao> obterAllByAutor(String id) throws excPassaErro {
        List<Notificacao> notificacaoList = new ArrayList<Notificacao>();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_NOTIFICACAO + "obterbyautor/" + id);
        notificacaoList = gson.fromJson(response, new TypeToken<List<Notificacao>>() {}.getType());
        return notificacaoList;
    }

    @Override
    public String update(Notificacao entity) throws excPassaErro {
        return connRest.put(hlpConstants.URL_BASE + hlpConstants.URL_NOTIFICACAO, gson.toJson(entity));
    }

    @Override
    public String delete(Integer id) throws excPassaErro {return null;}

    @Override
    public Notificacao findByID(Integer id) throws excPassaErro {return null;}

    @Override
    public List<Notificacao> findAll() throws excPassaErro {return null;}

}
