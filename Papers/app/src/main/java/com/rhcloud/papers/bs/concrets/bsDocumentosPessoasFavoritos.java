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
import com.rhcloud.papers.model.entity.DocumentosPessoasFavoritos;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodolfosantana on 09/09/17.
 */

public class bsDocumentosPessoasFavoritos implements itfGeneric<DocumentosPessoasFavoritos> {
    private Gson gson;
    private hlpConnectionURL connRest;
    private DocumentosPessoasFavoritos documentosPessoasFavoritos;

    public bsDocumentosPessoasFavoritos() {
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
    public String create(DocumentosPessoasFavoritos entity) throws excPassaErro {
        return connRest.put(hlpConstants.URL_BASE + hlpConstants.URL_FAVORITOS + "add/", gson.toJson(entity));
    }

    @Override
    public String update(DocumentosPessoasFavoritos entity) throws excPassaErro{
        return connRest.post(hlpConstants.URL_BASE + hlpConstants.URL_FAVORITOS, gson.toJson(entity));
    }

    @Override
    public String delete(Integer id) throws excPassaErro {
        return null;
    }

    public String delete(Integer autorid, Integer docid) throws excPassaErro{
        return connRest.delete(hlpConstants.URL_BASE + hlpConstants.URL_FAVORITOS + "autor/" + autorid + "/documento/" + docid);
    }

    @Override
    public DocumentosPessoasFavoritos findByID(Integer id) throws excPassaErro{
        documentosPessoasFavoritos = new DocumentosPessoasFavoritos();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_FAVORITOS + id);
        documentosPessoasFavoritos = gson.fromJson(response, DocumentosPessoasFavoritos.class);

        return documentosPessoasFavoritos;
    }

    @Override
    public List<DocumentosPessoasFavoritos> findAll() throws excPassaErro{
        List<DocumentosPessoasFavoritos> lstDocumentosPessoasFavoritos = new ArrayList<DocumentosPessoasFavoritos>();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_FAVORITOS + "list");
        lstDocumentosPessoasFavoritos = gson.fromJson(response, new TypeToken<List<DocumentosPessoasFavoritos>>() {}.getType());

        return lstDocumentosPessoasFavoritos;
    }

    public List<DocumentosPessoasFavoritos> findAllByAutor(Integer id) throws excPassaErro {
        List<DocumentosPessoasFavoritos> documentosPessoasFavoritos = new ArrayList<DocumentosPessoasFavoritos>();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_FAVORITOS + "autor/" + String.valueOf(id) + "/list");
        documentosPessoasFavoritos = gson.fromJson(response, new TypeToken<List<DocumentosPessoasFavoritos>>() {}.getType());

        return documentosPessoasFavoritos;
    }
}
