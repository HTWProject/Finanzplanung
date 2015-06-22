package com.htw.finanzplanung;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ActivityGruppenverwaltung extends AppCompatActivity {

    // Anlegen der Variabeln
    EditText inputGruppenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gruppenverwaltung);


        // Zuweisung der XML Objekte an unsere Variabeln
        inputGruppenName = (EditText) findViewById(R.id.gruppenname);
        Button createGruppeButton = (Button) findViewById(R.id.bt_createGruppe);

        // ClickListener implementieren für den Button zum Wechsel der Activity
        createGruppeButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Neues Gruppe anlegen


            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_gruppenverwaltung, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
