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
    private List<Documento> lstDocumentosRespomsavel;
    private List<Documento> lstDocumentosFavoritos;
    private List<FilaSubmissao> lstResponsavelPublicacao;
    private List<FilaSubmissao> lstParticipantePublicacao;

    public AutorPerfil(){
        lstDocumentosRespomsavel = new ArrayList<Documento>();
        lstDocumentosParticipante = new ArrayList<Documento>();
        lstDocumentosFavoritos = new ArrayList<Documento>();
        lstResponsavelPublicacao = new ArrayList<FilaSubmissao>();
        lstParticipantePublicacao = new ArrayList<FilaSubmissao>();
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

    public List<Documento> getLstDocumentosRespomsavel() {
        return lstDocumentosRespomsavel;
    }

    public void setLstDocumentosRespomsavel(List<Documento> lstDocumentosRespomsavel) {
        this.lstDocumentosRespomsavel = lstDocumentosRespomsavel;
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
}
