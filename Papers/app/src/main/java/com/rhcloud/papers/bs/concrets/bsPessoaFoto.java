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
import com.rhcloud.papers.bs.core.itfGeneric;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.generic.hlpConstants;
import com.rhcloud.papers.helpers.rest.hlpConnectionURL;
import com.rhcloud.papers.model.entity.PessoaFoto;


import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by rodolfosantana on 16/09/17.
 */

public class bsPessoaFoto implements itfGeneric<PessoaFoto> {
    private Gson gson;
    private hlpConnectionURL connRest;
    private PessoaFoto pessoaFoto;

    public bsPessoaFoto() {
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
    public String create(PessoaFoto entity) throws excPassaErro {
        return connRest.put(hlpConstants.URL_BASE + hlpConstants.URL_PESSOA_FOTO + "add/", gson.toJson(entity));
    }

    @Override
    public String update(PessoaFoto entity) throws excPassaErro{
        return connRest.post(hlpConstants.URL_BASE + hlpConstants.URL_PESSOA_FOTO, gson.toJson(entity));
    }

    @Override
    public String delete(Integer id) throws excPassaErro{
        return connRest.delete(hlpConstants.URL_BASE + hlpConstants.URL_PESSOA_FOTO + id);
    }

    @Override
    public PessoaFoto findByID(Integer id) throws excPassaErro{
        pessoaFoto = new PessoaFoto();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_PESSOA_FOTO + id);
        pessoaFoto = gson.fromJson(response, PessoaFoto.class);

        return pessoaFoto;
    }

    @Override
    public List<PessoaFoto> findAll() {
        return null;
    }

    public PessoaFoto finByAutorId(Integer id) throws excPassaErro{
        pessoaFoto = new PessoaFoto();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_PESSOA_FOTO + "obterbyautor/" +  id);
        pessoaFoto = gson.fromJson(response, PessoaFoto.class);

        return pessoaFoto;
    }
}
