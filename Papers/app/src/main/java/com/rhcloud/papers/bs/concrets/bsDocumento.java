package com.rhcloud.papers.bs.concrets;

/**
 * Created by rodolfosantana on 06/09/17.
 */

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
import com.rhcloud.papers.model.entity.Documento;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Rodolfo on 26/11/16.
 */

public class bsDocumento implements itfGeneric<Documento> {
    private Gson gson;
    private hlpConnectionURL connRest;
    private Documento documento;

    public bsDocumento() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(byte[].class, new JsonSerializer<byte[]>() {
            @Override
            public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(Base64.encodeToString(src, Base64.DEFAULT));
            }
        });

        gsonBuilder.registerTypeAdapter(byte[].class, new JsonDeserializer<byte[]>() {
            @Override
            public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return Base64.decode(json.getAsString(), Base64.DEFAULT);
            }
        });
        gson = gsonBuilder.create();

        connRest = new hlpConnectionURL();
    }

    @Override
    public String create(Documento entity) throws excPassaErro {
        return connRest.put(hlpConstants.URL_BASE + hlpConstants.URL_DOCUMENTO + "add/", gson.toJson(entity));
    }

    @Override
    public String update(Documento entity) throws excPassaErro{
        return connRest.post(hlpConstants.URL_BASE + hlpConstants.URL_DOCUMENTO, gson.toJson(entity));
    }

    @Override
    public String delete(Integer id) throws excPassaErro{
        return connRest.delete(hlpConstants.URL_BASE + hlpConstants.URL_DOCUMENTO + id);
    }

    @Override
    public Documento findByID(Integer id) throws excPassaErro{
        documento = new Documento();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_DOCUMENTO + id);
        documento = gson.fromJson(response, Documento.class);

        return documento;
    }

    @Override
    public List<Documento> findAll() throws excPassaErro{
        List<Documento> lstDocumentos = new ArrayList<Documento>();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_DOCUMENTO + "list");
        lstDocumentos = gson.fromJson(response, new TypeToken<List<Documento>>() {}.getType());

        return lstDocumentos;
    }

    public List<Documento> findAllByAutor(Integer id) throws excPassaErro {
        List<Documento> lstDocumentos = new ArrayList<Documento>();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_DOCUMENTO + "obterbyautor/" + String.valueOf(id));
        lstDocumentos = gson.fromJson(response, new TypeToken<List<Documento>>() {}.getType());

        return lstDocumentos;
    }
}

