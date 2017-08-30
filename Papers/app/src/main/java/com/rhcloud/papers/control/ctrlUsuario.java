package com.rhcloud.papers.control;

import com.rhcloud.papers.bs.concrets.bsUsuario;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.model.entity.Usuario;

/**
 * Created by rodolfosantana on 29/08/17.
 */

public class ctrlUsuario {
    private Usuario usuario;
    private bsUsuario bsUsuario;

    public ctrlUsuario(Usuario usuario){
        this.usuario = usuario;
        bsUsuario = new bsUsuario();
    }

    public String criarUsuario() throws excPassaErro {
        return bsUsuario.create(usuario);
    }

    public String removerUsuario() throws excPassaErro {
        return bsUsuario.delete(usuario.getId());
    }

    public String alterarSenha() throws excPassaErro {
        return bsUsuario.alterarSenha(usuario);
    }

}