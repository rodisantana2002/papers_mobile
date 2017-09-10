package com.rhcloud.papers.model.transitorio;

import com.rhcloud.papers.model.entity.Documento;
import com.rhcloud.papers.model.enumeration.Tipo;

import java.io.Serializable;

/**
 * Created by rodolfosantana on 09/09/17.
 */

public class Artigo implements Serializable{
    private Tipo tipo;
    private Documento documento;

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }
}
