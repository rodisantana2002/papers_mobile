/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rhcloud.papers.model.entity;

import android.support.annotation.NonNull;

import com.rhcloud.papers.model.enumeration.Situacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rodolfo
 */
public class FilaSubmissao implements Serializable, Comparable<FilaSubmissao>{
    private Integer id;
    private Situacao situacao;
    private String versao;
    private Destino destino;
    private Documento documento;
    private String idioma;
    private String observacao;
    private String dtLimiteSubmissao;
    private String dtPublicacao;
    private String dtUltAtualizacao;
    private String horaUltAtualizacao;
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

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getDtLimiteSubmissao() {
        return dtLimiteSubmissao;
    }

    public void setDtLimiteSubmissao(String dtLimiteSubmissao) {
        this.dtLimiteSubmissao = dtLimiteSubmissao;
    }

    public String getDtPublicacao() {
        return dtPublicacao;
    }

    public void setDtPublicacao(String dtPublicacao) {
        this.dtPublicacao = dtPublicacao;
    }

    public String getDtUltAtualizacao() {
        return dtUltAtualizacao;
    }

    public void setDtUltAtualizacao(String dtUltAtualizacao) {
        this.dtUltAtualizacao = dtUltAtualizacao;
    }

    public String getHoraUltAtualizacao() {
        return horaUltAtualizacao;
    }

    public void setHoraUltAtualizacao(String horaUltAtualizacao) {
        this.horaUltAtualizacao = horaUltAtualizacao;
    }

    public Pessoa getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(Pessoa criadoPor) {
        this.criadoPor = criadoPor;
    }

    @Override
    public String toString(){
        return this.getSituacao().toString();
    }

    @Override
    public int compareTo(@NonNull FilaSubmissao filaSubmissao) {
        if(filaSubmissao!=null){
            this.toString().compareTo(filaSubmissao.toString());
        }
        return 0;
    }
}
