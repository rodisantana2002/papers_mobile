package com.rhcloud.papers.helpers.generic;

import android.support.v7.app.AppCompatActivity;

import com.rhcloud.papers.R;
import com.rhcloud.papers.model.enumeration.AcoesPublicacao;
import com.rhcloud.papers.model.enumeration.Situacao;
import com.rhcloud.papers.model.transitorio.Acao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rodolfosantana on 12/09/17.
 */

public class hlpMapasValoresEnuns {
    private HashMap<Integer, String> lstMapaSituacao;
    private HashMap<Situacao, List<Acao>> lstMapaSituacaoAcoes;
    private List<Acao> lstIniciado, lstEm_Avaliacao_Orientador, lstAguardandoAjustes, lstLberado_Orientador, lstSubemetido_Publicacao, lstAprovada_Publicacao;

    public hlpMapasValoresEnuns(){
        popularMapaSituacao();
        popularMapaSituacaoAcoes();
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

    public List<Acao> getListaAcoesSituacao(Situacao situacao){
        return lstMapaSituacaoAcoes.get(situacao);
    }

    private void popularMapaSituacaoAcoes(){
        lstMapaSituacaoAcoes = new HashMap<Situacao, List<Acao>>();

        lstIniciado = new ArrayList<Acao>();
        Acao acao = new Acao();
        acao.setId(1);
        acao.setNomeAcao("Encaminhar para Orientador");
        acao.setComentarioAcao("movimenta o artigo para o orientador.");
        acao.setImgAcao(R.drawable.ic_arrow_forward_black_24dp);
        acao.setSituacao(Situacao.EM_AVALIACAO_ORIENTADOR);
        lstIniciado.add(acao);
        acao = new Acao();
        acao.setId(8);
        acao.setNomeAcao("Cancelar");
        acao.setComentarioAcao("Encerra a publicação.");
        acao.setImgAcao(R.drawable.ic_close_black_24dp);
        acao.setSituacao(Situacao.CANCELADO);
        lstIniciado.add(acao);

        //----------
        lstEm_Avaliacao_Orientador = new ArrayList<Acao>();
        acao = new Acao();
        acao.setId(3);
        acao.setNomeAcao("Liberar para Submissão");
        acao.setComentarioAcao("libera o artigo para ser encaminhado para publicação.");
        acao.setImgAcao(R.drawable.ic_thumb_up_black_24dp);
        acao.setSituacao(Situacao.LIBERADO_ORIENTADOR);
        lstEm_Avaliacao_Orientador.add(acao);
        acao = new Acao();
        acao.setId(2);
        acao.setNomeAcao("Devolver para Ajustes");
        acao.setComentarioAcao("retorna o artigo para a realização de ajustes.");
        acao.setImgAcao(R.drawable.ic_thumb_down_black_24dp);
        acao.setSituacao(Situacao.AGUARDANDO_AJUSTES);
        lstEm_Avaliacao_Orientador.add(acao);

        //----------------
        lstAguardandoAjustes= new ArrayList<Acao>();
        acao = new Acao();
        acao.setId(1);
        acao.setNomeAcao("Encaminhar para Orientador");
        acao.setComentarioAcao("movimenta o artigo para o orientador.");
        acao.setImgAcao(R.drawable.ic_arrow_forward_black_24dp);
        acao.setSituacao(Situacao.EM_AVALIACAO_ORIENTADOR);
        lstAguardandoAjustes.add(acao);
        acao = new Acao();
        acao.setId(8);
        acao.setNomeAcao("Cancelar");
        acao.setComentarioAcao("Encerra a publicação.");
        acao.setImgAcao(R.drawable.ic_close_black_24dp);
        acao.setSituacao(Situacao.CANCELADO);
        lstAguardandoAjustes.add(acao);

        //-------------------
        lstLberado_Orientador= new ArrayList<Acao>();
        acao = new Acao();
        acao.setId(4);
        acao.setNomeAcao("Submeter para Publicação");
        acao.setComentarioAcao("registra que o artigo já foi encaminhado para a publicação.");
        acao.setImgAcao(R.drawable.ic_send_black_18dp);
        acao.setSituacao(Situacao.SUBMETIDO_PUBLICACAO);
        lstLberado_Orientador.add(acao);
        acao = new Acao();
        acao.setId(8);
        acao.setNomeAcao("Cancelar");
        acao.setComentarioAcao("Encerra a publicação.");
        acao.setImgAcao(R.drawable.ic_close_black_24dp);
        acao.setSituacao(Situacao.CANCELADO);
        lstLberado_Orientador.add(acao);

        lstSubemetido_Publicacao= new ArrayList<Acao>();
        acao = new Acao();
        acao.setId(2);
        acao.setNomeAcao("Devolver para Ajustes");
        acao.setComentarioAcao("retorna o artigo para a realização de ajustes.");
        acao.setImgAcao(R.drawable.ic_thumb_down_black_24dp);
        acao.setSituacao(Situacao.AGUARDANDO_AJUSTES);
        lstSubemetido_Publicacao.add(acao);
        acao = new Acao();
        acao.setId(5);
        acao.setNomeAcao("Registrar Aprovação Publicação");
        acao.setComentarioAcao("registra que o artigo foi liberado para a publicação.");
        acao.setImgAcao(R.drawable.ic_spellcheck_black_24dp);
        acao.setSituacao(Situacao.APROVADA_PUBLICACAO);
        lstSubemetido_Publicacao.add(acao);
        acao = new Acao();
        acao.setId(7);
        acao.setNomeAcao("Rejeitar Publicação");
        acao.setComentarioAcao("registra que o artigo foi rejeitdo para publicação.");
        acao.setImgAcao(R.drawable.ic_mood_bad_black_24dp);
        acao.setSituacao(Situacao.REJEITADO);
        lstSubemetido_Publicacao.add(acao);

        lstAprovada_Publicacao= new ArrayList<Acao>();
        acao = new Acao();
        acao.setId(6);
        acao.setNomeAcao("Registrar Publicação");
        acao.setComentarioAcao("registra que o artigo foi publicado.");
        acao.setImgAcao(R.drawable.ic_mood_black_24dp);
        acao.setSituacao(Situacao.PUBLICADO);
        lstAprovada_Publicacao.add(acao);

        lstMapaSituacaoAcoes.put(Situacao.INICIADO, lstIniciado);
        lstMapaSituacaoAcoes.put(Situacao.EM_AVALIACAO_ORIENTADOR, lstEm_Avaliacao_Orientador);
        lstMapaSituacaoAcoes.put(Situacao.AGUARDANDO_AJUSTES, lstAguardandoAjustes);
        lstMapaSituacaoAcoes.put(Situacao.LIBERADO_ORIENTADOR, lstLberado_Orientador);
        lstMapaSituacaoAcoes.put(Situacao.SUBMETIDO_PUBLICACAO, lstSubemetido_Publicacao);
        lstMapaSituacaoAcoes.put(Situacao.CANCELADO, new ArrayList<Acao>());
        lstMapaSituacaoAcoes.put(Situacao.REJEITADO, new ArrayList<Acao>());
        lstMapaSituacaoAcoes.put(Situacao.APROVADA_PUBLICACAO, lstAprovada_Publicacao);
        lstMapaSituacaoAcoes.put(Situacao.PUBLICADO, new ArrayList<Acao>());
    }
}
