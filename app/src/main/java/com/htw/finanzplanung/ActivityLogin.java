package com.htw.finanzplanung;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
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
    //Context testcontext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Zuweisung der XML Objekte an unsere Variabeln
        inputEmail = (EditText) findViewById(R.id.email);
        inputPasswort = (EditText) findViewById(R.id.passwort);
        Button registrationButton = (Button) findViewById(R.id.bt_registration);
        Button loginButton = (Button) findViewById(R.id.bt_login);
        Button passwortVergessenButton = (Button) findViewById(R.id.bt_vergessen);
        //Button resetDbButton = (Button) findViewById(R.id.bt_resetDB);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        if(dataAccess.getLoginState()!=null){
            startActivity(new Intent(getApplicationContext(), ActivityHome.class));
        }

        // ClickListener implementieren für den Button zum Wechsel der Activity
        registrationButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                //Neues Intent anlegen
                Intent nextScreen = new Intent(getApplicationContext(), ActivityRegistration.class);

                //Intent mit den Daten füllen
                //nextScreen.putExtra("Email", inputEmail.getText().toString());
                //nextScreen.putExtra("Passwort", inputPasswort.getText().toString());


                // Intent starten und zur zweiten Activity wechseln

                startActivity(nextScreen);

                //setContentView(R.layout.activity_registration);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                String loginstatus = dataAccess.Login(inputEmail.getText().toString(), inputPasswort.getText().toString());

                if(dataAccess.getLoginState()!=null){
                    startActivity(new Intent(getApplicationContext(), ActivityHome.class));
                }else{
                    alertDialog.setTitle("Login");
                    alertDialog.setMessage(loginstatus);
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // here you can add functions
                        }
                    });

                    alertDialog.show();
                }


            }
        });
/*
        resetDbButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                dataAccess.databaseDelete(getApplicationContext());


                    alertDialog.setTitle("Reset");
                    alertDialog.setMessage("Erfolgreich alles zurückgesetzt");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // here you can add functions
                        }
                    });

                    alertDialog.show();



            }
        });
*/
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
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }
        if (id == R.id.resetDB) {
            dataAccess.databaseDelete(getApplicationContext());


            alertDialog.setTitle("Reset");
            alertDialog.setMessage("Erfolgreich alles zurückgesetzt");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // here you can add functions
                }
            });

            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}