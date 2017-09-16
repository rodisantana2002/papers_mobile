package com.rhcloud.papers.control;

import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.model.entity.Documento;
import com.rhcloud.papers.model.entity.DocumentosPessoas;
import com.rhcloud.papers.model.entity.DocumentosPessoasFavoritos;
import com.rhcloud.papers.model.entity.FilaSubmissao;
import com.rhcloud.papers.model.entity.HistoricoFilaSubmissao;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.model.transitorio.AutorPerfil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodolfosantana on 09/09/17.
 */

public class ctrlAutorPerfil {
    private ctrlDocumento ctrlDocumento;
    private ctrlDocumentoPessoas ctrlDocumentoPessoas;
    private ctrlDocumentoPessoasFavoritos ctrlDocumentoPessoasFavoritos;
    private ctrlSubmissoes ctrlSubmissoes;
    private AutorPerfil autorPerfil;
    private Usuario usuario;

    public ctrlAutorPerfil(Usuario  usuario) throws excPassaErro {
        autorPerfil = new AutorPerfil();
        ctrlDocumento = new ctrlDocumento(new Documento());
        ctrlSubmissoes = new ctrlSubmissoes(new FilaSubmissao());
        ctrlDocumentoPessoas = new ctrlDocumentoPessoas(new DocumentosPessoas());
        ctrlDocumentoPessoasFavoritos = new ctrlDocumentoPessoasFavoritos(new DocumentosPessoasFavoritos());
        this.usuario = usuario;
        autorPerfil.setUsuario(usuario);
        popularDocumentosResponsavel();
        popularDocumentosParticipante();
        popularDocumentosFavoritos();
    }

    public AutorPerfil getAutorPerfil() throws excPassaErro {
        return autorPerfil;
    }

    public AutorPerfil getAutorPublicacoes() throws excPassaErro {
        popularPublicacoesResponsavel();
        popularPublicacoesParticipante();
        popularPublicacoesFavoritos();
        return autorPerfil;
    }

    public void popularDocumentosResponsavel() throws excPassaErro {
        autorPerfil.setLstDocumentosResponsavel(ctrlDocumento.obterAllByAutor(usuario.getId()));
    }

    public void popularDocumentosParticipante() throws excPassaErro {
        List<Documento> lstDocumentos = new ArrayList<Documento>();
        for (DocumentosPessoas docPessoa : ctrlDocumentoPessoas.obterAllByAutor(usuario.getId())){
            lstDocumentos.add(docPessoa.getDocumento());
        }
        autorPerfil.setLstDocumentosParticipante(lstDocumentos);
    }

    public void popularDocumentosFavoritos() throws excPassaErro {
        List<Documento> lstDocumentos = new ArrayList<Documento>();
        for (DocumentosPessoasFavoritos favorito: ctrlDocumentoPessoasFavoritos.obterAllByAutor(usuario.getId())){
            lstDocumentos.add(favorito.getDocumento());
        }
        autorPerfil.setLstDocumentosFavoritos(lstDocumentos);
    }

    ///Carregas as publicações conforme os tipos de artigo
    public void popularPublicacoesResponsavel() throws excPassaErro {
        List<FilaSubmissao> lstFilaSubmissaos = new ArrayList<FilaSubmissao>();
        for (Documento documento: autorPerfil.getLstDocumentosResponsavel()){
            lstFilaSubmissaos.addAll(ctrlSubmissoes.obterAllByDocumento(documento.getId()));
        }
        autorPerfil.setLstResponsavelPublicacao(lstFilaSubmissaos);
    }

    public void popularPublicacoesParticipante() throws excPassaErro {
        List<FilaSubmissao> lstFilaSubmissaos = new ArrayList<FilaSubmissao>();
        for (Documento documento : autorPerfil.getLstDocumentosParticipante()){
            lstFilaSubmissaos.addAll(ctrlSubmissoes.obterAllByDocumento(documento.getId()));
        }
        autorPerfil.setLstParticipantePublicacao(lstFilaSubmissaos);
    }

    public void popularPublicacoesFavoritos() throws excPassaErro {
        List<FilaSubmissao> lstFilaSubmissaos = new ArrayList<FilaSubmissao>();
        for (Documento favorito : autorPerfil.getLstDocumentosFavoritos()){
            lstFilaSubmissaos.addAll(ctrlSubmissoes.obterAllByDocumento(favorito.getId()));
        }
        autorPerfil.setLstFavoritosPublicacao(lstFilaSubmissaos);
    }
}
