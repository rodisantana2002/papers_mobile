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
import com.rhcloud.papers.model.entity.Pessoa;
import com.rhcloud.papers.model.entity.Usuario;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Rodolfo on 26/11/16.
 */

public class bsPessoa implements itfGeneric<Pessoa> {
    private Gson gson;
    private hlpConnectionURL connRest;
    private Pessoa pessoa;

    public bsPessoa() {
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
    public String create(Pessoa entity) throws excPassaErro {
        return connRest.put(hlpConstants.URL_BASE + hlpConstants.URL_PESSOA + "add/", gson.toJson(entity));
    }

    @Override
    public String update(Pessoa entity) throws excPassaErro{
        return connRest.post(hlpConstants.URL_BASE + hlpConstants.URL_PESSOA, gson.toJson(entity));
    }

    @Override
    public String delete(Integer id) throws excPassaErro{
        return connRest.delete(hlpConstants.URL_BASE + hlpConstants.URL_PESSOA + id);
    }

    @Override
    public Pessoa findByID(Integer id) throws excPassaErro{
        pessoa = new Pessoa();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_PESSOA + id);
        pessoa = gson.fromJson(response, Pessoa.class);

        return pessoa;
    }

    @Override
    public List<Pessoa> findAll() throws excPassaErro{
        List<Pessoa> lstPessoa = new ArrayList<Pessoa>();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_PESSOA + "list");
        lstPessoa = gson.fromJson(response, new TypeToken<List<Pessoa>>() {}.getType());

        return lstPessoa;
    }

    public Pessoa findByEmail(String email) throws excPassaErro {
        pessoa = new Pessoa();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_PESSOA + "obterbyemail/" + email);
        pessoa = gson.fromJson(response, Pessoa.class);

        return pessoa;
    }

    public List<Pessoa> findAllById(Integer id) throws excPassaErro{
        List<Pessoa> lstPessoa = new ArrayList<Pessoa>();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_PESSOA + "obterbyfilter/" + String.valueOf(id));
        lstPessoa = gson.fromJson(response, new TypeToken<List<Pessoa>>() {}.getType());

        return lstPessoa;
    }

}

