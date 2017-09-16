package com.rhcloud.papers.control;

import com.rhcloud.papers.bs.concrets.bsPessoaFoto;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.model.entity.PessoaFoto;

/**
 * Created by rodolfosantana on 16/09/17.
 */

public class ctrlPessoaFoto {
    private PessoaFoto pessoaFoto;
    private bsPessoaFoto bsPessoaFoto;

    public ctrlPessoaFoto(PessoaFoto pessoaFoto){
        this.pessoaFoto = pessoaFoto;
        bsPessoaFoto = new bsPessoaFoto();
    }

    public String criar() throws excPassaErro {
        return bsPessoaFoto.create(pessoaFoto);
    }

    public String remover() throws excPassaErro {
        return bsPessoaFoto.delete(pessoaFoto.getId());
    }

    public String atualizar() throws excPassaErro {
        return bsPessoaFoto.update(pessoaFoto);
    }

    public PessoaFoto obterByID(Integer id) throws excPassaErro {
        return bsPessoaFoto.findByID(id);
    }

    public PessoaFoto obterByAutorId(Integer id) throws excPassaErro {
        return bsPessoaFoto.finByAutorId(id);
    }
}
