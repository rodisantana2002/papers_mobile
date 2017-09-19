package com.rhcloud.papers.control;

import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.model.entity.Documento;
import com.rhcloud.papers.model.entity.DocumentosPessoas;
import com.rhcloud.papers.model.entity.DocumentosPessoasFavoritos;
import com.rhcloud.papers.model.entity.FilaSubmissao;
import com.rhcloud.papers.model.entity.HistoricoFilaSubmissao;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.model.enumeration.Situacao;
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
    private Situacao situacao;

    public ctrlAutorPerfil(Usuario  usuario) throws excPassaErro {
        autorPerfil = new AutorPerfil();
        ctrlDocumento = new ctrlDocumento(new Documento());
        ctrlSubmissoes = new ctrlSubmissoes(new FilaSubmissao());
        ctrlDocumentoPessoas = new ctrlDocumentoPessoas(new DocumentosPessoas());
        ctrlDocumentoPessoasFavoritos = new ctrlDocumentoPessoasFavoritos(new DocumentosPessoasFavoritos());
        this.usuario = usuario;
        this.situacao = null;
        autorPerfil.setUsuario(usuario);
        popularDocumentosResponsavel();
        popularDocumentosParticipante();
        popularDocumentosFavoritos();
    }


    public AutorPerfil getAutorPerfil() throws excPassaErro {
        return autorPerfil;
    }

    public AutorPerfil getAutorPublicacoes(boolean situacao) throws excPassaErro {
        popularPublicacoesResponsavel(situacao);
        popularPublicacoesParticipante(situacao);
        popularPublicacoesFavoritos(situacao);
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
    public void popularPublicacoesResponsavel(boolean situacao) throws excPassaErro {
        List<FilaSubmissao> lstFilaSubmissaos = new ArrayList<FilaSubmissao>();
        for (Documento documento: autorPerfil.getLstDocumentosResponsavel()){
            if (situacao){
                lstFilaSubmissaos.addAll(ctrlSubmissoes.obterAllByDocumento(documento.getId()));
            }
            else{
                lstFilaSubmissaos.addAll(ctrlSubmissoes.obterAllByDocumentoBySituacao(documento.getId()));
            }
        }
        autorPerfil.setLstResponsavelPublicacao(lstFilaSubmissaos);
    }

    public void popularPublicacoesParticipante(boolean situacao) throws excPassaErro {
        List<FilaSubmissao> lstFilaSubmissaos = new ArrayList<FilaSubmissao>();
        for (Documento documento : autorPerfil.getLstDocumentosParticipante()){
            if (situacao){
                lstFilaSubmissaos.addAll(ctrlSubmissoes.obterAllByDocumento(documento.getId()));
            }
            else{
                lstFilaSubmissaos.addAll(ctrlSubmissoes.obterAllByDocumentoBySituacao(documento.getId()));
            }
        }
        autorPerfil.setLstParticipantePublicacao(lstFilaSubmissaos);
    }

    public void popularPublicacoesFavoritos(boolean situacao) throws excPassaErro {
        List<FilaSubmissao> lstFilaSubmissaos = new ArrayList<FilaSubmissao>();
        for (Documento favorito : autorPerfil.getLstDocumentosFavoritos()){
            if(situacao){
                lstFilaSubmissaos.addAll(ctrlSubmissoes.obterAllByDocumento(favorito.getId()));
            }
            else{
                lstFilaSubmissaos.addAll(ctrlSubmissoes.obterAllByDocumentoBySituacao(favorito.getId()));
            }
        }
        autorPerfil.setLstFavoritosPublicacao(lstFilaSubmissaos);
    }
}
