package com.htw.finanzplanung;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ActivityGruppenmitglieder extends AppCompatActivity {


    // Anlegen der Variabeln
    EditText inputMitglied;
    public Data_Access dataAccess = new Data_Access(this);

    private ListView numberList;
    MyThumbnailAdapter thadapter = null;
    ArrayList<Mitglied> meineMitglieder;
    //Intent i = ;
    // Receiving the Data
    //Integer gruppenID = (Integer) getIntent().getIntExtra( "GruppenId", 0);
    //Integer gruppenID = Integer.valueOf(getIntent().getStringExtra("GruppenId"));
    Integer gruppenID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gruppenmitglieder);
        //String gruppenID = getIntent().getStringExtra("GruppenId");
        gruppenID = getIntent().getIntExtra( "GruppenId", 0);
        Log.d("ResponseGruppe: ", "> " + gruppenID);

        // Zuweisung der XML Objekte an unsere Variabeln
        inputMitglied = (EditText) findViewById(R.id.mitgliederEmail);
        Button addMitgliedButton = (Button) findViewById(R.id.bt_addMitglied);


        meineMitglieder = dataAccess.getGruppenMitglieder(gruppenID);


        numberList = (ListView) findViewById(R.id.listViewMitglieder);


        thadapter = new MyThumbnailAdapter(getApplicationContext(), R.layout.list_row, meineMitglieder);
        numberList.setAdapter(thadapter);


        // ClickListener implementieren für den Button zum Wechsel der Activity
        addMitgliedButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Mitglied zur Gruppe hinzufügen
                if(!inputMitglied.getText().toString().isEmpty()){
                    dataAccess.addGruppenMitglied(gruppenID,inputMitglied.getText().toString());
                    inputMitglied.getText().clear();
                    meineMitglieder = dataAccess.getGruppenMitglieder(gruppenID);
                    thadapter.arr.add(meineMitglieder.get(meineMitglieder.size()));
                    thadapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Mitglied added", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_gruppenmitglieder, menu);
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


    public class MyThumbnailAdapter extends ArrayAdapter<Mitglied> {
        Data_Access dataAccess2;
        ArrayList<Mitglied> arr;
        Context context;

        public MyThumbnailAdapter(Context context, int textViewResourceId, ArrayList<Mitglied> objects) {

            super(context, textViewResourceId, objects);
            dataAccess2 = new Data_Access(context);
            this.arr = objects;
            this.context = context;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view = null;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_row, parent, false);
            Button textButton = (Button) view.findViewById(R.id.bt_text);
            Button delButton = (Button) view.findViewById(R.id.bt_del);

            delButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dataAccess2.deleteMitglied(gruppenID, arr.get(position).getDbId());
                    arr.remove(position);
                    thadapter.notifyDataSetChanged();
                    Toast.makeText(context, "Mitglied deleted", Toast.LENGTH_SHORT).show();
                }
            });

            textButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    //Neues Intent anlegen
                    //Intent nextScreen = new Intent(getApplicationContext(), ActivityGruppenmitglieder.class);

                    //Intent mit den Daten füllen
                    //nextScreen.putExtra("GruppenId", arr.get(position).getDbId());
                    //nextScreen.putExtra("Passwort", inputPasswort.getText().toString());


                    // Intent starten und zur zweiten Activity wechseln

                    //startActivity(nextScreen);
                }
            });

            textButton.setText(arr.get(position).getName());
            return view;
        }
    }

}
