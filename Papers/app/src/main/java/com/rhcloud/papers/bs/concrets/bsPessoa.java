package com.rhcloud.papers.bs.concrets;

import android.os.StrictMode;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rhcloud.papers.bs.core.itfGeneric;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.generic.hlpConstants;
import com.rhcloud.papers.helpers.rest.hlpConnectionURL;
import com.rhcloud.papers.model.entity.Pessoa;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Rodolfo on 26/11/16.
 */

public class bsPessoa implements itfGeneric<Pessoa> {
    private final Gson gson;
    private hlpConnectionURL connRest;
    private Pessoa pessoa;

    public bsPessoa() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        gson = new Gson();
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
}

