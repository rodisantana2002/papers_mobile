package com.rhcloud.papers.control;

import com.rhcloud.papers.bs.concrets.bsDestino;
import com.rhcloud.papers.bs.concrets.bsPessoa;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.model.entity.Destino;
import com.rhcloud.papers.model.entity.Pessoa;

import java.util.List;

/**
 * Created by rodolfosantana on 04/09/17.
 */

public class ctrlDestino {
    private Destino destino;
    private bsDestino bsDestino;

    public ctrlDestino(Destino destino){
        this.destino = destino;
        bsDestino = new bsDestino();
    }

    public String criar() throws excPassaErro {
        return bsDestino.create(destino);
    }

    public String remover() throws excPassaErro {
        return bsDestino.delete(destino.getId());
    }

    public String atualizar() throws excPassaErro {
        return bsDestino.update(destino);
    }

    public Destino obterByID(Integer id) throws excPassaErro {
        return bsDestino.findByID(id);
    }

    public List<Destino> obterAll() throws excPassaErro {
        return bsDestino.findAll();
    }
}
