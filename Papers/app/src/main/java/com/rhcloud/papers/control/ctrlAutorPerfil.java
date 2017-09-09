package com.rhcloud.papers.control;

import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.model.entity.Documento;
import com.rhcloud.papers.model.entity.DocumentosPessoas;
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
    private ctrlSubmissoes ctrlSubmissoes;
    private AutorPerfil autorPerfil;
    private Usuario usuario;

    public ctrlAutorPerfil(Usuario  usuario) throws excPassaErro {
        autorPerfil = new AutorPerfil();
        ctrlDocumento = new ctrlDocumento(new Documento());
        ctrlDocumentoPessoas = new ctrlDocumentoPessoas(new DocumentosPessoas());
        autorPerfil.setUsuario(usuario);
        this.usuario = usuario;
        popularDocumentosResponsavel();
        popularDocumentosParticipante();
    }

    public AutorPerfil getAutorPerfil() throws excPassaErro {
        return autorPerfil;
    }

    private void popularDocumentosResponsavel() throws excPassaErro {
        autorPerfil.setLstResponsavelDocumentos(ctrlDocumento.obterAllByAutor(usuario.getId()));
    }

    private void popularDocumentosParticipante() throws excPassaErro {
        List<Documento> lstDocumentos = new ArrayList<Documento>();
        for (DocumentosPessoas docPessoa : ctrlDocumentoPessoas.obterAllByAutor(usuario.getId())){
            lstDocumentos.add(docPessoa.getDocumento());
        }
        autorPerfil.setLstParticipanteDocumentos(lstDocumentos);
    }

    private void popularPublicacoesResponsavel() throws excPassaErro {
        List<FilaSubmissao> lstFilaSubmissaos = new ArrayList<FilaSubmissao>();
        for (Documento documento: autorPerfil.getLstResponsavelDocumentos()){
            lstFilaSubmissaos.addAll(ctrlSubmissoes.obterAllByDocumento(documento.getId()));
        }
        autorPerfil.setLstResponsavelPublicacao(lstFilaSubmissaos);
    }

    private void popularPublicacoesParticipante() throws excPassaErro {
        List<FilaSubmissao> lstFilaSubmissaos = new ArrayList<FilaSubmissao>();
        for (Documento documento : autorPerfil.getLstParticipanteDocumentos()){
            lstFilaSubmissaos.addAll(ctrlSubmissoes.obterAllByDocumento(documento.getId()));
        }
        autorPerfil.setLstResponsavelPublicacao(lstFilaSubmissaos);
    }
}
