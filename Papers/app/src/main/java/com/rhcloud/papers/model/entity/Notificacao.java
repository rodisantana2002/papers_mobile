package com.rhcloud.papers.model.entity;

import android.support.annotation.NonNull;

import com.rhcloud.papers.model.enumeration.Status;

import java.io.Serializable;

/**
 * Created by rodolfosantana on 19/09/17.
 */
public class Notificacao implements Serializable, Comparable<Notificacao>{
    private Integer id;
    private Pessoa pessoa;
    private Documento documento;
    private String dtCriacao;
    private String horaCriacao;
    private String conteudo;
    private Status status;

    public Notificacao(){
        pessoa = new Pessoa();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(String dtCriacao) {
        this.dtCriacao = dtCriacao;
    }

    public String getHoraCriacao() {
        return horaCriacao;
    }

    public void setHoraCriacao(String horaCriacao) {
        this.horaCriacao = horaCriacao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    @Override
    public String toString(){
        return this.getDtCriacao() + " " + this.getHoraCriacao();
    }

    @Override
    public int compareTo(@NonNull Notificacao notificacao) {
        if (notificacao!=null){
            return notificacao.toString().compareTo(this.toString());
        }
        return 0;
    }
}
