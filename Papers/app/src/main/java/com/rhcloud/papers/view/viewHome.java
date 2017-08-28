package com.rhcloud.papers.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rhcloud.papers.R;
import com.rhcloud.papers.helpers.generic.hlpConstants;

public class viewHome extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_documentos:
                    mTextMessage.setText(R.string.title_dashboard);
                    popularTela();
                    return true;
                case R.id.navigation_autores:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_configuracoes:
                    mTextMessage.setText(R.string.title_configuracoes);
                    return true;
            }
            return false;
        }

        private void popularTela() {
            SharedPreferences sharedPreferences = getSharedPreferences(hlpConstants.MYPREFERENCES, Context.MODE_PRIVATE);
            Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            moveTaskToBack(true);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
