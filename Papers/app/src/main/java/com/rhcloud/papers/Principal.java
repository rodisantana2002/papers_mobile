package com.rhcloud.papers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rhcloud.papers.view.viewRecuperarSenha;

public class Principal extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Button btnEsqueciSenha = findViewById(R.id.btnEsqueciMinhaSenha);
        btnEsqueciSenha.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnEsqueciMinhaSenha){
                Intent intent = new Intent(Principal.this, viewRecuperarSenha.class);
                startActivity(intent);
        }
    }
}
