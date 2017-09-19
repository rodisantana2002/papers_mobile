package com.rhcloud.papers.bs.concrets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rhcloud.papers.bs.core.itfGeneric;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.generic.hlpConstants;
import com.rhcloud.papers.helpers.rest.hlpConnectionURL;
import com.rhcloud.papers.model.entity.FilaSubmissao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodolfosantana on 09/09/17.
 */

public class bsSubmissoes implements itfGeneric<FilaSubmissao> {
    private Gson gson;
    private hlpConnectionURL connRest;
    private FilaSubmissao filaSubmissao;

    public bsSubmissoes(){
        gson = new Gson();
        connRest = new hlpConnectionURL();
    }

    @Override
    public String create(FilaSubmissao entity) throws excPassaErro {
        return connRest.put(hlpConstants.URL_BASE + hlpConstants.URL_FILA_SUBMISSAO + "add/", gson.toJson(entity));
    }

    @Override
    public String update(FilaSubmissao entity) throws excPassaErro{
        return connRest.post(hlpConstants.URL_BASE + hlpConstants.URL_FILA_SUBMISSAO, gson.toJson(entity));
    }

    public String atualizarSituacao(FilaSubmissao entity) throws excPassaErro{
        return connRest.post(hlpConstants.URL_BASE + hlpConstants.URL_FILA_SUBMISSAO + "situacao/", gson.toJson(entity));
    }

    @Override
    public String delete(Integer id) throws excPassaErro{
        return connRest.delete(hlpConstants.URL_BASE + hlpConstants.URL_FILA_SUBMISSAO + id);
    }

    @Override
    public FilaSubmissao findByID(Integer id) throws excPassaErro{
        FilaSubmissao filaSubmissao = new FilaSubmissao();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_FILA_SUBMISSAO + id);
        filaSubmissao = gson.fromJson(response, FilaSubmissao.class);

        return filaSubmissao;
    }

    @Override
    public List<FilaSubmissao> findAll() throws excPassaErro{
        List<FilaSubmissao> lstFilaSubmissaos = new ArrayList<FilaSubmissao>();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_FILA_SUBMISSAO + "list");
        lstFilaSubmissaos = gson.fromJson(response, new TypeToken<List<FilaSubmissao>>() {}.getType());

        return lstFilaSubmissaos;
    }

    public List<FilaSubmissao> findAllByDocumento(Integer id) throws excPassaErro {
        List<FilaSubmissao> lstFilaSubmissaos = new ArrayList<FilaSubmissao>();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_FILA_SUBMISSAO + "documento/" + String.valueOf(id) + "/list");
        lstFilaSubmissaos = gson.fromJson(response, new TypeToken<List<FilaSubmissao>>() {}.getType());

        return lstFilaSubmissaos;
    }

    public List<FilaSubmissao> findAllByDocumentoBySituacao(Integer id) throws excPassaErro {
        List<FilaSubmissao> lstFilaSubmissaos = new ArrayList<FilaSubmissao>();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_FILA_SUBMISSAO + "documento/" + String.valueOf(id) + "/arquivadas");
        lstFilaSubmissaos = gson.fromJson(response, new TypeToken<List<FilaSubmissao>>() {}.getType());

        return lstFilaSubmissaos;
    }
}
