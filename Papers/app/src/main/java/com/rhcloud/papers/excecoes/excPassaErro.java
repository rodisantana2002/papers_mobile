package com.rhcloud.papers.excecoes;

/**
 *
 * @author Rodolfo
 */
public class excPassaErro extends Exception {
    //apenas criar construtor para receber mensagem e encaminhar para Super.
    public excPassaErro(String message){
        super(message);
    }    
}
