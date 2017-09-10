package com.rhcloud.papers.control;

import com.rhcloud.papers.bs.concrets.bsDocumentoPessoas;
import com.rhcloud.papers.bs.concrets.bsDocumentosPessoasFavoritos;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.model.entity.DocumentosPessoasFavoritos;

import java.util.List;

/**
 * Created by rodolfosantana on 09/09/17.
 */

public class ctrlDocumentoPessoasFavoritos {
    private DocumentosPessoasFavoritos documentosPessoasFavoritos;
    private bsDocumentosPessoasFavoritos bsDocumentosPessoasFavoritos;

    public ctrlDocumentoPessoasFavoritos(DocumentosPessoasFavoritos documentosPessoasFavoritos){
        this.documentosPessoasFavoritos = documentosPessoasFavoritos;
        bsDocumentosPessoasFavoritos = new bsDocumentosPessoasFavoritos();
    }

    public String criar() throws excPassaErro {
        return bsDocumentosPessoasFavoritos.create(documentosPessoasFavoritos);
    }

    public String remover() throws excPassaErro {
        return bsDocumentosPessoasFavoritos.delete(documentosPessoasFavoritos.getPessoa().getId(), documentosPessoasFavoritos.getDocumento().getId());
    }

    public String atualizar() throws excPassaErro {
        return bsDocumentosPessoasFavoritos.update(documentosPessoasFavoritos);
    }

    public DocumentosPessoasFavoritos obterByID(Integer id) throws excPassaErro {
        return bsDocumentosPessoasFavoritos.findByID(id);
    }

    public List<DocumentosPessoasFavoritos> obterAll() throws excPassaErro {
        return bsDocumentosPessoasFavoritos.findAll();
    }

    public List<DocumentosPessoasFavoritos> obterAllByAutor(Integer id) throws excPassaErro {
        return bsDocumentosPessoasFavoritos.findAllByAutor(id);
    }
}
