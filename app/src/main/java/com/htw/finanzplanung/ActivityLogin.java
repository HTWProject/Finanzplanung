package com.htw.finanzplanung;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ActivityLogin extends AppCompatActivity{

    // Anlegen der Variabeln
    EditText inputEmail;
    EditText inputPasswort;
    Data_Access dataAccess = new Data_Access(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Zuweisung der XML Objekte an unsere Variabeln
        inputEmail = (EditText) findViewById(R.id.email);
        inputPasswort = (EditText) findViewById(R.id.passwort);
        Button registrationButton = (Button) findViewById(R.id.bt_registration);
        Button loginButton = (Button) findViewById(R.id.bt_login);
        Button passwortVergessenButton = (Button) findViewById(R.id.bt_vergessen);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        // ClickListener implementieren für den Button zum Wechsel der Activity
        registrationButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Neues Intent anlegen
                Intent nextScreen = new Intent(getApplicationContext(), ActivityRegistration.class);

                //Intent mit den Daten füllen
                nextScreen.putExtra("Email", inputEmail.getText().toString());
                nextScreen.putExtra("Passwort", inputPasswort.getText().toString());


                // Intent starten und zur zweiten Activity wechseln
                startActivity(nextScreen);

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Neues Intent anlegen
                Intent nextScreen = new Intent(getApplicationContext(), ActivityHome.class);

                //Intent mit den Daten füllen
                nextScreen.putExtra("Email", inputEmail.getText().toString());
                nextScreen.putExtra("Passwort", inputPasswort.getText().toString());


                // Intent starten und zur zweiten Activity wechseln
                startActivity(nextScreen);

            }
        });
        passwortVergessenButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                alertDialog.setTitle("Passwort Vergessen");
                alertDialog.setMessage(dataAccess.sendPasswortToEmail(inputEmail.getText().toString()));
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // here you can add functions
                    }
                });

                alertDialog.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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