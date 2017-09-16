package com.rhcloud.papers.model.entity;

import java.io.Serializable;

/**
 * Created by rodolfosantana on 16/09/17.
 */

public class PessoaFoto implements Serializable {
    private Integer id;
    private Pessoa pessoa;
    private byte[] foto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
