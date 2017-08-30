package com.rhcloud.papers.model.transitorio;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by rodolfosantana on 30/08/17.
 */

public class Acao implements Serializable{
    private int id;
    private String nomeAcao;
    private String comentarioAcao;
    private Drawable imgAcao;
    private Class classeAcao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeAcao() {
        return nomeAcao;
    }

    public void setNomeAcao(String nomeAcao) {
        this.nomeAcao = nomeAcao;
    }

    public String getComentarioAcao() {
        return comentarioAcao;
    }

    public void setComentarioAcao(String comentarioAcao) {
        this.comentarioAcao = comentarioAcao;
    }

    public Class getClasseAcao() {
        return classeAcao;
    }

    public void setClasseAcao(Class classeAcao) {
        this.classeAcao = classeAcao;
    }

    public Drawable getImgAcao() {
        return imgAcao;
    }

    public void setImgAcao(Drawable imgAcao) {
        this.imgAcao = imgAcao;
    }
}
