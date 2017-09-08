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
import com.rhcloud.papers.model.entity.DocumentosPessoas;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Rodolfo on 26/11/16.
 */

public class bsDocumentoPessoas implements itfGeneric<DocumentosPessoas> {
    private Gson gson;
    private hlpConnectionURL connRest;
    private DocumentosPessoas documentosPessoas;

    public bsDocumentoPessoas() {
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
    public String create(DocumentosPessoas entity) throws excPassaErro {
        return connRest.put(hlpConstants.URL_BASE + hlpConstants.URL_DOCUMENTOS_PESSOAS + "add/", gson.toJson(entity));
    }

    @Override
    public String update(DocumentosPessoas entity) throws excPassaErro{
        return connRest.post(hlpConstants.URL_BASE + hlpConstants.URL_DOCUMENTOS_PESSOAS, gson.toJson(entity));
    }

    @Override
    public String delete(Integer id) throws excPassaErro{
        return connRest.delete(hlpConstants.URL_BASE + hlpConstants.URL_DOCUMENTOS_PESSOAS + id);
    }

    @Override
    public DocumentosPessoas findByID(Integer id) throws excPassaErro{
        documentosPessoas = new DocumentosPessoas();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_DOCUMENTOS_PESSOAS + id);
        documentosPessoas = gson.fromJson(response, DocumentosPessoas.class);

        return documentosPessoas;
    }

    @Override
    public List<DocumentosPessoas> findAll() throws excPassaErro{
        List<DocumentosPessoas> lstDocumentosPessoas = new ArrayList<DocumentosPessoas>();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_DOCUMENTOS_PESSOAS + "list");
        lstDocumentosPessoas = gson.fromJson(response, new TypeToken<List<DocumentosPessoas>>() {}.getType());

        return lstDocumentosPessoas;
    }

    public List<DocumentosPessoas> findAllByAutor(Integer id) throws excPassaErro {
        List<DocumentosPessoas> lstDocumentosPessoas = new ArrayList<DocumentosPessoas>();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_DOCUMENTOS_PESSOAS + "autor/" + String.valueOf(id) + "/list");
        lstDocumentosPessoas = gson.fromJson(response, new TypeToken<List<DocumentosPessoas>>() {}.getType());

        return lstDocumentosPessoas;
    }

    public List<DocumentosPessoas> findAllByDocumento(Integer id) throws excPassaErro {
        List<DocumentosPessoas> lstDocumentosPessoas = new ArrayList<DocumentosPessoas>();
        String response = connRest.get(hlpConstants.URL_BASE + hlpConstants.URL_DOCUMENTOS_PESSOAS + "documento/" + String.valueOf(id) + "/list");
        lstDocumentosPessoas = gson.fromJson(response, new TypeToken<List<DocumentosPessoas>>() {}.getType());

        return lstDocumentosPessoas;
    }

}

