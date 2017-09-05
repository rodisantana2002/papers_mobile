package com.rhcloud.papers.control;

import com.rhcloud.papers.bs.concrets.bsPessoa;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.model.entity.Pessoa;
import com.rhcloud.papers.model.entity.Usuario;

import java.util.List;

/**
 * Created by rodolfosantana on 31/08/17.
 */

public class ctrlPessoa {
    private Pessoa pessoa;
    private bsPessoa bsPessoa;

    public ctrlPessoa(Pessoa pessoa){
        this.pessoa = pessoa;
        bsPessoa = new bsPessoa();
    }

    public String criar() throws excPassaErro {
        return bsPessoa.create(pessoa);
    }

    public String remover() throws excPassaErro {
        return bsPessoa.delete(pessoa.getId());
    }

    public String atualizar() throws excPassaErro {
        return bsPessoa.update(pessoa);
    }

    public Pessoa obterByID(Integer id) throws excPassaErro {
        return bsPessoa.findByID(id);
    }

    public List <Pessoa> obterAll() throws excPassaErro {
        return bsPessoa.findAll();
    }

}
