package com.rhcloud.papers.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.control.ctrlPessoa;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.core.itfDialogGeneric;
import com.rhcloud.papers.helpers.generic.hlpDialog;
import com.rhcloud.papers.model.entity.Pessoa;
import com.rhcloud.papers.model.entity.Usuario;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class viewAlterarFoto extends AppCompatActivity implements View.OnClickListener{
    private Button btnSelecionarImg, btnEnviar;
    private ImageButton btnVoltar;
    private ImageView imgFoto;
    private ProgressDialog progressDialog;
    private procDados procDados;
    private Usuario usuario;
    private static int RESULT_LOAD_IMAGE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alterar_foto);

        prepararComponentes(getIntent().getExtras());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(selectedImage);
                Bitmap imgF = BitmapFactory.decodeStream(imageStream);
                imgFoto.setImageBitmap(imgF);
                if(imgF!=null){
                    btnEnviar.setVisibility(View.VISIBLE);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void prepararComponentes(Bundle bundle) {
        btnEnviar = (Button) findViewById(R.id.btnEnviarFoto);
        btnSelecionarImg = (Button) findViewById(R.id.btnSelecionarFoto);
        btnVoltar = (ImageButton)  findViewById(R.id.btnVoltarAlterarFoto);

        btnEnviar.setOnClickListener(this);
        btnSelecionarImg.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);

        imgFoto = (ImageView) findViewById(R.id.imgFotoUsuario);
        usuario = (Usuario) bundle.getSerializable("usuario");
        btnEnviar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == btnEnviar.getId()) {
            if (validarDados()) {
                atualizarObjeto();
                procDados = new procDados(usuario);
                procDados.execute();
            }
        }

        if (view.getId() == btnVoltar.getId()){
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            intent = new Intent(viewAlterarFoto.this, viewPerfil.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        if(view.getId() ==btnSelecionarImg.getId()){
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, RESULT_LOAD_IMAGE);
        }
    }


    private byte[] getFoto(){
        Bitmap bitmap = ((BitmapDrawable) imgFoto.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void atualizarObjeto() {
         usuario.getPessoa().setFoto(getFoto());
    }

    private boolean validarDados() {
        if (getFoto()==null) {
           hlpDialog.getAlertDialog(this, "Atenção", "Uma Foto válida deve ser selecionada", "Ok", new itfDialogGeneric() {

                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    btnSelecionarImg.requestFocus();
                }
            });
            return false;
        }
        btnEnviar.setVisibility(View.VISIBLE);
        return true;
    }

    private class procDados extends AsyncTask<Void, Void, String> {
        private Usuario usuario;

        public procDados(Usuario usuario){
            this.usuario = usuario;
        }

        @Override
        protected String doInBackground(Void... voids) {
            ctrlPessoa ctrlPessoa = new ctrlPessoa(usuario.getPessoa());
            String msg = "";
            try {
                return ctrlPessoa.atualizar();
            } catch (com.rhcloud.papers.excecoes.excPassaErro excPassaErro) {
                msg = excPassaErro.getMessage();
            }
            return msg;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(viewAlterarFoto.this, "Aguarde", "Enviando solicitação...");
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (result.trim().equals("Autor registrado com sucesso")) {
                result = "Foto atualizada com sucesso";
            }
            final String finalResult = result;
            hlpDialog.getAlertDialog(viewAlterarFoto.this, "Atenção", result, "Ok", new itfDialogGeneric() {
                @Override
                public void onButtonAction(boolean value) throws excPassaErro {
                    if (finalResult.trim().equals("Foto atualizada com sucesso")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("usuario", usuario);
                        Intent intent = new Intent(viewAlterarFoto.this, viewPerfil.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        btnSelecionarImg.requestFocus();
                    }
                }
            });
        }
    }

}

