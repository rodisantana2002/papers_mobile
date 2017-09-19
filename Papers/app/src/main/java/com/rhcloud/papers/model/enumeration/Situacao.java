/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rhcloud.papers.model.enumeration;

/**
 *
 * @author Rodolfo
 */
public enum Situacao {
    INICIADO,
    EM_AVALIACAO_ORIENTADOR,
    AGUARDANDO_AJUSTES,
    LIBERADO_ORIENTADOR,
    SUBMETIDO_PUBLICACAO,
    CANCELADO,
    REJEITADO,
    APROVADA_PUBLICACAO,
    PUBLICADO;

    public String getDescricao(){
        switch (this){
            case INICIADO: return "Em Redação";
            case EM_AVALIACAO_ORIENTADOR: return "Em Avaliação com Orientador";
            case AGUARDANDO_AJUSTES: return "Aguardando Ajustes";
            case LIBERADO_ORIENTADOR: return "Liberado Orientador";
            case SUBMETIDO_PUBLICACAO: return "Submetido para Publicação";
            case CANCELADO: return "Cancelado";
            case REJEITADO: return "Rejeitado";
            case APROVADA_PUBLICACAO: return "Aprovada Publicação";
            case PUBLICADO: return "Publicado";
            default: return "";
        }
    }
}
