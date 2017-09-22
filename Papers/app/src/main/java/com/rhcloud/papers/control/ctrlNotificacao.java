package com.rhcloud.papers.control;

import com.rhcloud.papers.bs.concrets.bsNotificacao;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.model.entity.DocumentosPessoas;
import com.rhcloud.papers.model.entity.Notificacao;
import com.rhcloud.papers.model.entity.Usuario;
import com.rhcloud.papers.model.enumeration.Status;

import java.util.ArrayList;
import java.util.HashMap;
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

    public String criar(Usuario usuario) throws excPassaErro {
        //vai em busca das pessoas que devem ser notificadas;
        //procura pela lista de partiipanates;
        String msg = "";
        for (DocumentosPessoas documentosPessoas : ctrlDocumentoPessoas.obterAllByDocumento(notificacao.getDocumento().getId())){
            ///naõ envia para o proprio participante quando ele esta gerando historico
            if (!usuario.getPessoa().getId().equals(documentosPessoas.getPessoa().getId())){
                Notificacao not = notificacao;
                not.setPessoa(documentosPessoas.getPessoa());
                msg = bsNotificacao.create(not);
            }
        }
        //valida se o usuario locado é o responsavel senão envia notificação para autor
        if (!usuario.getPessoa().getId().equals(notificacao.getDocumento().getPessoa().getId())){
            Notificacao not = notificacao;
            not.setPessoa(notificacao.getDocumento().getPessoa());
            msg = bsNotificacao.create(not);
        }
        return msg;
    }

    public String atualizar() throws excPassaErro {
        return bsNotificacao.update(notificacao);
    }

    public HashMap<Status, List<Notificacao>> obterAllByAutor(String id) throws excPassaErro {
        HashMap<Status, List<Notificacao>> listHashMap = new HashMap<Status, List<Notificacao>>();
        List<Notificacao> notificacaoListPendentes, notificacaoListLidas, notificacaoListArquivadas;
        notificacaoListPendentes = new ArrayList<Notificacao>();
        notificacaoListLidas = new ArrayList<Notificacao>();
        notificacaoListArquivadas = new ArrayList<Notificacao>();

        for (Notificacao noti : bsNotificacao.obterAllByAutor(id)){
            if (noti.getStatus().equals(Status.PENDENTE)){
                notificacaoListPendentes.add(noti);
            }
            else if(noti.getStatus().equals(Status.LIDA)){
                notificacaoListLidas.add(noti);
            }
            else if (noti.getStatus().equals(Status.ARQUIVADA)){
                notificacaoListArquivadas.add(noti);
            }
        }
        listHashMap.put(Status.PENDENTE, notificacaoListPendentes);
        listHashMap.put(Status.LIDA, notificacaoListLidas);
        listHashMap.put(Status.ARQUIVADA, notificacaoListArquivadas);
        return listHashMap;
    }

    public Integer obterTotalPendentesByAutor(String id) throws excPassaErro {
        return bsNotificacao.obterPendentesByAutor(id).size();
    }
}
