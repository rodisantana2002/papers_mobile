package com.rhcloud.papers.model.enumeration;

/**
 * Created by rodolfosantana on 19/09/17.
 */

public enum Status {
    LIDA,
    ARQUIVADA,
    PENDENTE;

    public String getDescricao(){
        switch (this){
            case LIDA : return "Lida";
            case PENDENTE : return "Não Lida";
            case ARQUIVADA : return "Arquivada";
            default: return "";
        }
    }
}
