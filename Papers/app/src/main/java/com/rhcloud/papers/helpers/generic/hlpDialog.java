package com.rhcloud.papers.helpers.generic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;

/**
 * Created by Rodolfo on 19/01/17.
 */

public class hlpDialog {

    public static void getConfirmDialog(final Context mContext,
                                        final String title, final String msg,
                                        final String positiveBtnCaption, final String negativeBtnCaption,
                                        final boolean isCancelable, final itfDialogGeneric target) {

        ((Activity) mContext).runOnUiThread(new Runnable() {

            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                int imageResource = android.R.drawable.ic_dialog_alert;
                Drawable image = mContext.getResources().getDrawable(imageResource);

                builder.setTitle(title)
                        .setMessage(msg)
                        .setIcon(image)
                        .setCancelable(false)
                        .setPositiveButton(positiveBtnCaption,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        try {
                                            target.onButtonAction(true);
                                        } catch (excPassaErro excPassaErro) {
                                            excPassaErro.printStackTrace();
                                        }
                                    }
                                })
                        .setNegativeButton(negativeBtnCaption,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        try {
                                            target.onButtonAction(false);
                                        } catch (excPassaErro excPassaErro) {
                                            excPassaErro.printStackTrace();
                                        }
                                    }
                                });

                AlertDialog alert = builder.create();
                alert.setCancelable(isCancelable);
                alert.show();
                if (isCancelable) {
                    alert.setOnCancelListener(new DialogInterface.OnCancelListener() {

                        @Override
                        public void onCancel(DialogInterface arg0) {
                            try {
                                target.onButtonAction(false);
                            } catch (excPassaErro excPassaErro) {
                                excPassaErro.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    public static void getAlertDialog(final Context mContext,
                                      final String title, final String msg,
                                      final String confirmBtnCaption, final itfDialogGeneric target) {

        ((Activity) mContext).runOnUiThread(new Runnable() {

            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                int imageResource = android.R.drawable.ic_dialog_alert;
                Drawable image = mContext.getResources().getDrawable(imageResource, null);

                builder.setTitle(title)
                        .setMessage(msg)
                        .setIcon(image)
                        .setPositiveButton(confirmBtnCaption,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        try {
                                            target.onButtonAction(true);
                                        } catch (excPassaErro excPassaErro) {
                                            excPassaErro.printStackTrace();
                                        }
                                    }
                                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}
