/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rhcloud.papers.model.entity;

import com.rhcloud.papers.model.enumeration.Situacao;

import java.io.Serializable;

/**
 *
 * @author Rodolfo
 */
public class HistoricoFilaSubmissao implements Serializable{
    private Integer id;
    private Situacao situacao;
    private String versao;
    private String dtAtualizacao;
    private String horaAtualizacao;
    private String comentario;
    private FilaSubmissao filaSubmissao;
    private Pessoa criadoPor;        

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getDtAtualizacao() {
        return dtAtualizacao;
    }

    public void setDtAtualizacao(String dtAtualizacao) {
        this.dtAtualizacao = dtAtualizacao;
    }

    public String getHoraAtualizacao() {
        return horaAtualizacao;
    }

    public void setHoraAtualizacao(String horaAtualizacao) {
        this.horaAtualizacao = horaAtualizacao;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public FilaSubmissao getFilaSubmissao() {
        return filaSubmissao;
    }

    public void setFilaSubmissao(FilaSubmissao filaSubmissao) {
        this.filaSubmissao = filaSubmissao;
    }

    public Pessoa getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(Pessoa criadoPor) {
        this.criadoPor = criadoPor;
    }
}
