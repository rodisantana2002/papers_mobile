package com.rhcloud.papers.control;

import com.rhcloud.papers.bs.concrets.bsAutentication;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.model.entity.Usuario;

/**
 * Created by Rodolfo on 28/12/16.
 */

public class ctrlLogin {
    private Usuario usuario;
    private bsAutentication autentication;

    public ctrlLogin(Usuario usuario){
        autentication = new bsAutentication();
        this.usuario = usuario;
    }

    public Usuario efetuarLogin() throws excPassaErro {
        return autentication.efetuarLogin(usuario);
    }

    public void efetuarLogout(){

    }
}

