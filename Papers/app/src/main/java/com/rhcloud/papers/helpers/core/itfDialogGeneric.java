package com.rhcloud.papers.helpers.core;

/**
 * Created by Rodolfo on 21/01/17.
 */
import android.content.DialogInterface;
import com.rhcloud.papers.excecoes.excPassaErro;


public interface itfDialogGeneric {
    public abstract void onButtonAction(boolean value) throws excPassaErro;
}
