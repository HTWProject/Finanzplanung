package com.htw.finanzplanung;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ActivityRegistration extends AppCompatActivity {

    // Anlegen der Variabeln
    EditText inputName;
    EditText inputEmail;
    EditText inputPasswort;
    EditText inputPasswortValidierung;
    Data_Access dataAccess = new Data_Access(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Zuweisung der XML Objekte an unsere Variabeln
        inputName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPasswort = (EditText) findViewById(R.id.passwort);
        inputPasswortValidierung = (EditText) findViewById(R.id.PasswortValidation);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        Button okButton = (Button) findViewById(R.id.bt_OK);

        okButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                alertDialog.setTitle("Registration");
                alertDialog.setMessage(dataAccess.registration(inputName.getText().toString(), inputEmail.getText().toString(), inputPasswort.getText().toString(), inputPasswortValidierung.getText().toString()));
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
        getMenuInflater().inflate(R.menu.menu_activity_registration, menu);
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
