package com.htw.finanzplanung;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class ActivitySettings extends Activity {

    // Anlegen der Variabeln
    EditText inputServer;
    EditText inputName;
    EditText inputNewPasswort;
    EditText inputOldPasswort;
    CheckBox inputCheckbox;
    Button settingsSaveButton;

    Data_Access dataAccess = new Data_Access(this);

    public void addListenerOnChkIos() {

        inputCheckbox = (CheckBox) findViewById(R.id.cb_mobilSync);
        settingsSaveButton = (Button) findViewById(R.id.bt_save);
        inputName = (EditText) findViewById(R.id.user_name);
        inputNewPasswort = (EditText) findViewById(R.id.newPasswort);
        inputOldPasswort = (EditText) findViewById(R.id.Passwort);
        inputServer = (EditText) findViewById(R.id.Server);
        inputCheckbox.setChecked(dataAccess.getMobileSyncStatus());

        inputName.setText(dataAccess.getName());
        inputServer.setText(dataAccess.getServer());

        inputCheckbox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                //if (((CheckBox) v).isChecked()) {
                dataAccess.setMobileSync(((CheckBox) v).isChecked(), inputOldPasswort.getText().toString());
                inputCheckbox.setChecked(dataAccess.getMobileSyncStatus());
                /*
                Toast.makeText(getApplicationContext(),
                       "Bro, try Android :)" + dataAccess.getMobileSyncStatus() + ((CheckBox) v).isChecked(), Toast.LENGTH_LONG).show();
                }
                 else if(!((CheckBox) v).isChecked()){
                    dataAccess.setMobileSync(false, inputOldPasswort.getText().toString());
                    inputCheckbox.setChecked(dataAccess.getMobileSyncStatus());
                }
                */

            }
        });
        settingsSaveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?

                    Toast.makeText(getApplicationContext(),
                            "Save", Toast.LENGTH_LONG).show();


            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        addListenerOnChkIos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_settings, menu);
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
