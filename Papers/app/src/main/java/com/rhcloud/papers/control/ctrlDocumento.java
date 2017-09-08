package com.rhcloud.papers.control;

import com.rhcloud.papers.bs.concrets.bsDocumento;
import com.rhcloud.papers.bs.concrets.bsPessoa;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.model.entity.Documento;

import java.util.List;

/**
 * Created by rodolfosantana on 06/09/17.
 */

public class ctrlDocumento {
    private Documento documento;
    private bsDocumento bsDocumento;

    public ctrlDocumento(Documento documento){
        this.documento = documento;
        bsDocumento = new bsDocumento();
    }

    public String criar() throws excPassaErro {
        return bsDocumento.create(documento);
    }

    public String remover() throws excPassaErro {
        return bsDocumento.delete(documento.getId());
    }

    public String atualizar() throws excPassaErro {
        return bsDocumento.update(documento);
    }

    public Documento obterByID(Integer id) throws excPassaErro {
        return bsDocumento.findByID(id);
    }

    public List<Documento> obterAll() throws excPassaErro {
        return bsDocumento.findAll();
    }

    public List<Documento> obterAllByAutor(Integer id) throws excPassaErro {
        return bsDocumento.findAllByAutor(id);
    }
}
