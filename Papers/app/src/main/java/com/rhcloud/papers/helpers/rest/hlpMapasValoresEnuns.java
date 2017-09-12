package com.rhcloud.papers.helpers.rest;

import com.rhcloud.papers.model.enumeration.Situacao;

import java.util.HashMap;

/**
 * Created by rodolfosantana on 12/09/17.
 */

public class hlpMapasValoresEnuns {
    private HashMap<Integer, String> lstMapaSituacao;

    public hlpMapasValoresEnuns(){
        popularMapaSituacao();
    }

    public String getDescricaoSituacao(Situacao situacao){
        return lstMapaSituacao.get(situacao.ordinal());
    }

    private void popularMapaSituacao(){
        lstMapaSituacao = new HashMap<Integer, String>();
        lstMapaSituacao.put(Situacao.INICIADO.ordinal(), "EM REDAÇÃO");
        lstMapaSituacao.put(Situacao.EM_AVALIACAO_ORIENTADOR.ordinal(), "EM AVALIAÇÃO COM O ORIENTADOR");
        lstMapaSituacao.put(Situacao.AGUARDANDO_AJUSTES.ordinal(), "AGUARDANDO AJUSTES");
        lstMapaSituacao.put(Situacao.LIBERADO_ORIENTADOR.ordinal(), "LIBERADO PELO ORIENTADOR");
        lstMapaSituacao.put(Situacao.SUBMETIDO_PUBLICACAO.ordinal(), "SUBMETIDO PARA PUBLICAÇÃO");
        lstMapaSituacao.put(Situacao.CANCELADO.ordinal(), "CANCELADO");
        lstMapaSituacao.put(Situacao.REJEITADO.ordinal(), "REJEITADO");
        lstMapaSituacao.put(Situacao.APROVADA_PUBLICACAO.ordinal(), "APROVADA A PUBLICAÇÃO");
        lstMapaSituacao.put(Situacao.PUBLICADO.ordinal(), "PUBLICADO");
    }
}
