package com.rhcloud.papers.model.transitorio;

import com.rhcloud.papers.model.entity.Documento;
import com.rhcloud.papers.model.entity.FilaSubmissao;
import com.rhcloud.papers.model.entity.Usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodolfosantana on 09/09/17.
 */

public class AutorPerfil implements Serializable{
    private Usuario usuario;
    private List<Documento> lstDocumentosParticipante;
    private List<Documento> lstDocumentosResponsavel;
    private List<Documento> lstDocumentosFavoritos;

    private List<FilaSubmissao> lstResponsavelPublicacao;
    private List<FilaSubmissao> lstParticipantePublicacao;
    private List<FilaSubmissao> lstFavoritosPublicacao;

    public AutorPerfil(){
        lstDocumentosResponsavel = new ArrayList<Documento>();
        lstDocumentosParticipante = new ArrayList<Documento>();
        lstDocumentosFavoritos = new ArrayList<Documento>();

        lstResponsavelPublicacao = new ArrayList<FilaSubmissao>();
        lstParticipantePublicacao = new ArrayList<FilaSubmissao>();
        lstFavoritosPublicacao = new ArrayList<FilaSubmissao>();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Documento> getLstDocumentosParticipante() {
        return lstDocumentosParticipante;
    }

    public void setLstDocumentosParticipante(List<Documento> lstDocumentosParticipante) {
        this.lstDocumentosParticipante = lstDocumentosParticipante;
    }

    public List<Documento> getLstDocumentosResponsavel() {
        return lstDocumentosResponsavel;
    }

    public void setLstDocumentosResponsavel(List<Documento> lstDocumentosResponsavel) {
        this.lstDocumentosResponsavel = lstDocumentosResponsavel;
    }

    public List<Documento> getLstDocumentosFavoritos() {
        return lstDocumentosFavoritos;
    }

    public void setLstDocumentosFavoritos(List<Documento> lstDocumentosFavoritos) {
        this.lstDocumentosFavoritos = lstDocumentosFavoritos;
    }

    public List<FilaSubmissao> getLstResponsavelPublicacao() {
        return lstResponsavelPublicacao;
    }

    public void setLstResponsavelPublicacao(List<FilaSubmissao> lstResponsavelPublicacao) {
        this.lstResponsavelPublicacao = lstResponsavelPublicacao;
    }

    public List<FilaSubmissao> getLstParticipantePublicacao() {
        return lstParticipantePublicacao;
    }

    public void setLstParticipantePublicacao(List<FilaSubmissao> lstParticipantePublicacao) {
        this.lstParticipantePublicacao = lstParticipantePublicacao;
    }

    public List<FilaSubmissao> getLstFavoritosPublicacao() {
        return lstFavoritosPublicacao;
    }

    public void setLstFavoritosPublicacao(List<FilaSubmissao> lstFavoritosPublicacao) {
        this.lstFavoritosPublicacao = lstFavoritosPublicacao;
    }
}
