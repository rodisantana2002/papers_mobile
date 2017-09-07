package com.rhcloud.papers.bs.concrets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rhcloud.papers.bs.core.itfGeneric;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.generic.hlpConstants;
import com.rhcloud.papers.helpers.rest.hlpConnectionURL;
import com.rhcloud.papers.model.entity.TipoDocumento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodolfosantana on 06/09/17.
 */

public class bsTipoDocumento implements itfGeneric<TipoDocumento>{
    private Gson gson;
    private hlpConnectionURL connRest;
    private TipoDocumento tipoDocumento;

    public bsTipoDocumento() {
        gson = new Gson();
        connRest = new hlpConnectionURL();
    }

    @Override
    public String create(TipoDocumento entity) throws excPassaErro {
        return connRest.put(hlpConstants.URL_BASE + hlpConstants.URL_TIPO_DOCUMENTO + "add/", gson.toJson(entity));
    }

    @Override
    public String update(TipoDocumento entity) throws excPassaErro{
        return connRest.post(hlpConstants.URL_BASE + hlpConstants.URL_TIPO_DOCUMENTO, gson.toJson(entity));
    }

    @Override
    public String delete(Integer id) throws excPassaErro{
        return connRest.delete(hlpConstants.URL_BASE + hlpConstants.URL_TIPO_DOCUMENTO + id);
    }

    @Override
    public TipoDocumento findByID(Integer id) throws excPassaErro{
        tipoDocumento = new TipoDocumento();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_TIPO_DOCUMENTO + id);
        tipoDocumento = gson.fromJson(response, TipoDocumento.class);

        return tipoDocumento;
    }

    @Override
    public List<TipoDocumento> findAll() throws excPassaErro{
        List<TipoDocumento> lstTipoDocumentos = new ArrayList<TipoDocumento>();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_TIPO_DOCUMENTO + "list");
        lstTipoDocumentos = gson.fromJson(response, new TypeToken<List<TipoDocumento>>() {}.getType());

        return lstTipoDocumentos;
    }
}
