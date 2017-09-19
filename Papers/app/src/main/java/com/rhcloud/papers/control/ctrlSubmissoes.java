package com.rhcloud.papers.control;

import com.rhcloud.papers.bs.concrets.bsDocumento;
import com.rhcloud.papers.bs.concrets.bsHistorico;
import com.rhcloud.papers.bs.concrets.bsSubmissoes;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.model.entity.FilaSubmissao;
import com.rhcloud.papers.model.entity.HistoricoFilaSubmissao;

import java.util.List;

/**
 * Created by rodolfosantana on 09/09/17.
 */

public class ctrlSubmissoes {
    private FilaSubmissao filaSubmissao;
    private bsSubmissoes bsSubmissoes;
    private bsHistorico bsHistorico;

    public ctrlSubmissoes(FilaSubmissao filaSubmissao){
        this.filaSubmissao = filaSubmissao;
        bsSubmissoes = new bsSubmissoes();
        bsHistorico = new bsHistorico();
    }

    public String criar() throws excPassaErro {
        return bsSubmissoes.create(filaSubmissao);
    }

    public String remover() throws excPassaErro {
        return bsSubmissoes.delete(filaSubmissao.getId());
    }

    public String atualizar() throws excPassaErro {
        return bsSubmissoes.update(filaSubmissao);
    }

    public FilaSubmissao obterByID(Integer id) throws excPassaErro {
        return bsSubmissoes.findByID(id);
    }

    public List<FilaSubmissao> obterAll() throws excPassaErro {
        return bsSubmissoes.findAll();
    }

    public List<FilaSubmissao> obterAllByDocumento(Integer id) throws excPassaErro {
        return bsSubmissoes.findAllByDocumento(id);
    }

    public List<FilaSubmissao> obterAllByDocumentoBySituacao(Integer id) throws excPassaErro {
        return bsSubmissoes.findAllByDocumentoBySituacao(id);
    }

    public String atualizarSituacao() throws excPassaErro {
        return bsSubmissoes.atualizarSituacao(filaSubmissao);
    }

}
