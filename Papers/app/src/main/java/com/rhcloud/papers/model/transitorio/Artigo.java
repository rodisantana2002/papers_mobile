package com.rhcloud.papers.model.transitorio;

import com.rhcloud.papers.model.entity.Documento;
import com.rhcloud.papers.model.enumeration.TipoAutor;

import java.io.Serializable;

/**
 * Created by rodolfosantana on 09/09/17.
 */

public class Artigo implements Serializable{
    private TipoAutor tipoAutor;
    private Documento documento;

    public TipoAutor getTipoAutor() {
        return tipoAutor;
    }

    public void setTipoAutor(TipoAutor tipoAutor) {
        this.tipoAutor = tipoAutor;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }
}
