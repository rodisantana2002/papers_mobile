package com.rhcloud.papers.control;

import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.model.entity.Documento;
import com.rhcloud.papers.model.entity.DocumentosPessoas;
import com.rhcloud.papers.model.entity.DocumentosPessoasFavoritos;
import com.rhcloud.papers.model.entity.FilaSubmissao;
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
        ctrlDocumentoPessoas = new ctrlDocumentoPessoas(new DocumentosPessoas());
        ctrlDocumentoPessoasFavoritos = new ctrlDocumentoPessoasFavoritos(new DocumentosPessoasFavoritos());
        this.usuario = usuario;
        this.usuario.getPessoa().setFoto(null);
        autorPerfil.setUsuario(usuario);
        popularDocumentosResponsavel();
        popularDocumentosParticipante();
        popularDocumentosFavoritos();
    }

    public AutorPerfil getAutorPerfil() throws excPassaErro {
        return autorPerfil;
    }

    private void popularDocumentosResponsavel() throws excPassaErro {
        autorPerfil.setLstDocumentosResponsavel(ctrlDocumento.obterAllByAutor(usuario.getId()));
    }

    private void popularDocumentosParticipante() throws excPassaErro {
        List<Documento> lstDocumentos = new ArrayList<Documento>();
        for (DocumentosPessoas docPessoa : ctrlDocumentoPessoas.obterAllByAutor(usuario.getId())){
            docPessoa.getPessoa().setFoto(null);
            lstDocumentos.add(docPessoa.getDocumento());
        }
        autorPerfil.setLstDocumentosParticipante(lstDocumentos);
    }

    private void popularDocumentosFavoritos() throws excPassaErro {
        List<Documento> lstDocumentos = new ArrayList<Documento>();
        for (DocumentosPessoasFavoritos favorito: ctrlDocumentoPessoasFavoritos.obterAllByAutor(usuario.getId())){
            favorito.getPessoa().setFoto(null);
            lstDocumentos.add(favorito.getDocumento());
        }
        autorPerfil.setLstDocumentosFavoritos(lstDocumentos);
    }

    private void popularPublicacoesResponsavel() throws excPassaErro {
        List<FilaSubmissao> lstFilaSubmissaos = new ArrayList<FilaSubmissao>();
        for (Documento documento: autorPerfil.getLstDocumentosResponsavel()){
            documento.getPessoa().setFoto(null);
            lstFilaSubmissaos.addAll(ctrlSubmissoes.obterAllByDocumento(documento.getId()));
        }
        autorPerfil.setLstResponsavelPublicacao(lstFilaSubmissaos);
    }

    private void popularPublicacoesParticipante() throws excPassaErro {
        List<FilaSubmissao> lstFilaSubmissaos = new ArrayList<FilaSubmissao>();
        for (Documento documento : autorPerfil.getLstDocumentosParticipante()){
            documento.getPessoa().setFoto(null);
            lstFilaSubmissaos.addAll(ctrlSubmissoes.obterAllByDocumento(documento.getId()));
        }
        autorPerfil.setLstResponsavelPublicacao(lstFilaSubmissaos);
    }
}
