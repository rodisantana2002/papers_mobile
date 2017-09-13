package com.rhcloud.papers.bs.concrets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rhcloud.papers.bs.core.itfGeneric;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.generic.hlpConstants;
import com.rhcloud.papers.helpers.rest.hlpConnectionURL;
import com.rhcloud.papers.model.entity.HistoricoFilaSubmissao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodolfosantana on 12/09/17.
 */

public class bsHistorico implements itfGeneric<HistoricoFilaSubmissao> {
    private Gson gson;
    private hlpConnectionURL connRest;
    private HistoricoFilaSubmissao historicoFilaSubmissao;

    public bsHistorico(){
        gson = new Gson();
        connRest = new hlpConnectionURL();
    }

    @Override
    public String create(HistoricoFilaSubmissao entity) throws excPassaErro {
        return connRest.put(hlpConstants.URL_BASE + hlpConstants.URL_HISTORICO + "add/", gson.toJson(entity));
    }

    public List<HistoricoFilaSubmissao> obterAllByPublicacao(HistoricoFilaSubmissao historicoFilaSubmissao) throws excPassaErro {
        List<HistoricoFilaSubmissao> historicoFilaSubmissaos = new ArrayList<HistoricoFilaSubmissao>();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_HISTORICO + "submissao/" + historicoFilaSubmissao.getFilaSubmissao().getId() + "/list");
        historicoFilaSubmissaos = gson.fromJson(response, new TypeToken<List<HistoricoFilaSubmissao>>() {}.getType());
        return historicoFilaSubmissaos;
    }


    @Override
    public String update(HistoricoFilaSubmissao entity) throws excPassaErro {return null;}

    @Override
    public String delete(Integer id) throws excPassaErro {return null;}

    @Override
    public HistoricoFilaSubmissao findByID(Integer id) throws excPassaErro {return null;}

    @Override
    public List<HistoricoFilaSubmissao> findAll() throws excPassaErro {return null;}
}

