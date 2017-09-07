package com.rhcloud.papers.control;

import com.rhcloud.papers.bs.concrets.bsTipoDocumento;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.model.entity.TipoDocumento;

import java.util.List;

/**
 * Created by rodolfosantana on 06/09/17.
 */

public class ctrlTipoDocumento {
    private TipoDocumento tipoDocumento;
    private bsTipoDocumento bsTipoDocumento;

    public ctrlTipoDocumento(TipoDocumento tipoDocumento){
        this.tipoDocumento = tipoDocumento;
        bsTipoDocumento = new bsTipoDocumento();
    }

    public String criar() throws excPassaErro {
        return bsTipoDocumento.create(tipoDocumento);
    }

    public String remover() throws excPassaErro {
        return bsTipoDocumento.delete(tipoDocumento.getId());
    }

    public String atualizar() throws excPassaErro {
        return bsTipoDocumento.update(tipoDocumento);
    }

    public TipoDocumento obterByID(Integer id) throws excPassaErro {
        return bsTipoDocumento.findByID(id);
    }

    public List<TipoDocumento> obterAll() throws excPassaErro {
        return bsTipoDocumento.findAll();
    }
}
