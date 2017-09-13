package com.rhcloud.papers.control;

import com.rhcloud.papers.bs.concrets.bsHistorico;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.model.entity.HistoricoFilaSubmissao;

/**
 * Created by rodolfosantana on 12/09/17.
 */

public class ctrlHistorico {
    private HistoricoFilaSubmissao historicoFilaSubmissao;
    private bsHistorico bsHistorico;

    public ctrlHistorico(HistoricoFilaSubmissao historicoFilaSubmissao){
        this.historicoFilaSubmissao = historicoFilaSubmissao;
        bsHistorico = new bsHistorico();
    }

    public String criar() throws excPassaErro {
        return bsHistorico.create(historicoFilaSubmissao);
    }
}
