package com.rhcloud.papers.bs.concrets;

import android.os.StrictMode;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.rhcloud.papers.bs.core.itfGeneric;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.generic.hlpConstants;
import com.rhcloud.papers.helpers.rest.hlpConnectionURL;
import com.rhcloud.papers.model.entity.Destino;
import com.rhcloud.papers.model.entity.Pessoa;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Rodolfo on 26/11/16.
 */

public class bsDestino implements itfGeneric<Destino> {
    private Gson gson;
    private hlpConnectionURL connRest;
    private Destino destino;

    public bsDestino() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        gson = new Gson();
        connRest = new hlpConnectionURL();
    }

    @Override
    public String create(Destino entity) throws excPassaErro {
        return connRest.put(hlpConstants.URL_BASE + hlpConstants.URL_DESTINO + "add/", gson.toJson(entity));
    }

    @Override
    public String update(Destino entity) throws excPassaErro{
        return connRest.post(hlpConstants.URL_BASE + hlpConstants.URL_DESTINO, gson.toJson(entity));
    }

    @Override
    public String delete(Integer id) throws excPassaErro{
        return connRest.delete(hlpConstants.URL_BASE + hlpConstants.URL_DESTINO + id);
    }

    @Override
    public Destino findByID(Integer id) throws excPassaErro{
        destino = new Destino();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_DESTINO + id);
        destino = gson.fromJson(response, Destino.class);

        return destino;
    }

    @Override
    public List<Destino> findAll() throws excPassaErro{
        List<Destino> lstDestinos = new ArrayList<Destino>();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_DESTINO + "list");
        lstDestinos = gson.fromJson(response, new TypeToken<List<Destino>>() {}.getType());

        return lstDestinos;
    }
}

