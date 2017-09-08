package com.rhcloud.papers.control;

import com.rhcloud.papers.bs.concrets.bsDocumentoPessoas;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.model.entity.DocumentosPessoas;

import java.util.List;

/**
 * Created by rodolfosantana on 08/09/17.
 */

public class ctrlDocumentoPessoas {
    private DocumentosPessoas documentosPessoas;
    private bsDocumentoPessoas bsDocumentoPessoas;

    public ctrlDocumentoPessoas(DocumentosPessoas documentosPessoas){
        this.documentosPessoas = documentosPessoas;
        bsDocumentoPessoas = new bsDocumentoPessoas();
    }

    public String criar() throws excPassaErro {
        return bsDocumentoPessoas.create(documentosPessoas);
    }

    public String remover() throws excPassaErro {
        return bsDocumentoPessoas.delete(documentosPessoas.getId());
    }

    public String atualizar() throws excPassaErro {
        return bsDocumentoPessoas.update(documentosPessoas);
    }

    public DocumentosPessoas obterByID(Integer id) throws excPassaErro {
        return bsDocumentoPessoas.findByID(id);
    }

    public List<DocumentosPessoas> obterAll() throws excPassaErro {
        return bsDocumentoPessoas.findAll();
    }

    public List<DocumentosPessoas> obterAllByAutor(Integer id) throws excPassaErro {
        return bsDocumentoPessoas.findAllByAutor(id);
    }

    public List<DocumentosPessoas> obterAllByDocumento(Integer id) throws excPassaErro {
        return bsDocumentoPessoas.findAllByDocumento(id);
    }
}
