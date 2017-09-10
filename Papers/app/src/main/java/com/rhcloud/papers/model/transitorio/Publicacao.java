package com.rhcloud.papers.model.transitorio;

import com.rhcloud.papers.model.entity.FilaSubmissao;

import java.io.Serializable;

/**
 * Created by rodolfosantana on 09/09/17.
 */

public class Publicacao implements Serializable{
    private FilaSubmissao filaSubmissao;

    public FilaSubmissao getFilaSubmissao() {
        return filaSubmissao;
    }

    public void setFilaSubmissao(FilaSubmissao filaSubmissao) {
        this.filaSubmissao = filaSubmissao;
    }
}
