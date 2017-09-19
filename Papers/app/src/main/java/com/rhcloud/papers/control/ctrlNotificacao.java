package com.rhcloud.papers.control;

import com.rhcloud.papers.bs.concrets.bsNotificacao;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.model.entity.DocumentosPessoas;
import com.rhcloud.papers.model.entity.Notificacao;

import java.util.List;

/**
 * Created by rodolfosantana on 19/09/17.
 */

public class ctrlNotificacao {
    private Notificacao notificacao;
    private bsNotificacao bsNotificacao;
    private ctrlDocumentoPessoas ctrlDocumentoPessoas;

    public ctrlNotificacao(Notificacao notificacao){
        this.notificacao = notificacao;
        bsNotificacao = new bsNotificacao();
        ctrlDocumentoPessoas = new ctrlDocumentoPessoas(new DocumentosPessoas());
    }

    public String criar() throws excPassaErro {
        //vai em busca das pessoas que devem ser notificadas;
        String msg = "";
        for (DocumentosPessoas documentosPessoas : ctrlDocumentoPessoas.obterAllByDocumento(notificacao.getDocumento().getId())){
            Notificacao not = notificacao;
            not.setPessoa(documentosPessoas.getPessoa());
            msg = bsNotificacao.create(not);
        }
        return msg;
    }

    public String atualizar() throws excPassaErro {
        return bsNotificacao.update(notificacao);
    }

    public List<Notificacao> obterAllByAutor(String id) throws excPassaErro {
        return bsNotificacao.obterAllByAutor(id);
    }
}
