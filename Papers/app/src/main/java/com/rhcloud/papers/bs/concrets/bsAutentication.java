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
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.generic.hlpConstants;
import com.rhcloud.papers.helpers.rest.hlpConnectionURL;
import com.rhcloud.papers.model.entity.Usuario;

import java.lang.reflect.Type;

/**
 * Created by Rodolfo on 02/12/16.
 */

public class bsAutentication {
    private final Gson gson;
    private hlpConnectionURL connRest;
    private Usuario usuario;

    public bsAutentication() {
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

    public Usuario efetuarLogin(Usuario entity) throws excPassaErro {
        usuario = new Usuario();
        String response = connRest.post(hlpConstants.URL_BASE + hlpConstants.URL_AUTENTICATION + "login/", gson.toJson(entity));
        usuario = gson.fromJson(response, Usuario.class);

        return usuario;
    }

    public void efetuarLogout(Usuario entity) throws excPassaErro{
        usuario = new Usuario();
        connRest.post(hlpConstants.URL_BASE + hlpConstants.URL_AUTENTICATION + "logout/", gson.toJson(entity));
    }

    public String recuperarAcesso(Usuario entity) throws excPassaErro {
        return connRest.post(hlpConstants.URL_BASE + hlpConstants.URL_AUTENTICATION + "esquecisenha/", gson.toJson(entity));
    }
}
