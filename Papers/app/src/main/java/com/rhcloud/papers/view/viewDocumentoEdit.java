package com.rhcloud.papers.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.rhcloud.papers.R;
import com.rhcloud.papers.model.entity.TipoDocumento;
import com.rhcloud.papers.view.adapters.adpTipoDocumento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodolfosantana on 06/09/17.
 */

public class viewDocumentoEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_documento_edit);
        //prepararComponentes(getIntent().getExtras());

        Spinner txtTipo = (Spinner) findViewById(R.id.txtTipo);
        List<TipoDocumento> tipoDocumentoList = new ArrayList<TipoDocumento>();
        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setId(1);
        tipoDocumento.setDescricao("teste");
        tipoDocumentoList.add(tipoDocumento);

        adpTipoDocumento arrayAdapter = new adpTipoDocumento(this,tipoDocumentoList);
        txtTipo.setAdapter(arrayAdapter);
    }
}
