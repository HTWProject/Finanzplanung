package com.htw.finanzplanung;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.List;


public class ActivityGruppenverwaltung extends AppCompatActivity {

    // Anlegen der Variabeln
    EditText inputGruppenName;
    public Data_Access dataAccess = new Data_Access(this);

    private ListView numberList;
    MyThumbnailAdapter thadapter = null;
    List<Gruppe> meineGruppen;
    //int number_count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gruppenverwaltung);


        // Zuweisung der XML Objekte an unsere Variabeln
        inputGruppenName = (EditText) findViewById(R.id.gruppenname);
        Button createGruppeButton = (Button) findViewById(R.id.bt_createGruppe);
        //gruppenView = (ListView) findViewById(R.id.listViewMeineGruppen);

        meineGruppen = dataAccess.getMeineGruppen();

        //ArrayAdapter<Gruppe> gruppenAdaptArray = new ArrayAdapter<Gruppe>(ActivityGruppenverwaltung.this, R.layout.list_row, meineGruppen);


        //gruppenView.setAdapter(gruppenAdaptArray);



        numberList = (ListView) findViewById(R.id.listViewMeineGruppen);

        //for(int i = number_count; i < 20; i++) {numAl.add(i + " ");}

        thadapter = new MyThumbnailAdapter(ActivityGruppenverwaltung.this, R.layout.list_row, meineGruppen);
        numberList.setAdapter(thadapter);


        // ClickListener implementieren für den Button zum Wechsel der Activity
        createGruppeButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Neues Gruppe anlegen
                if(!inputGruppenName.getText().toString().isEmpty()){
                    dataAccess.addGruppe(inputGruppenName.getText().toString());
                    inputGruppenName.getText().clear();
                    meineGruppen = dataAccess.getMeineGruppen();

                    thadapter.arr.clear();
                    thadapter.arr.addAll(meineGruppen);
                    //thadapter.arr.add(meineGruppen.get(meineGruppen.size()-1));
                    thadapter.notifyDataSetChanged();
                    Toast.makeText(ActivityGruppenverwaltung.this, "Item added", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_gruppenverwaltung, menu);
        return false;
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

    public class MyThumbnailAdapter extends ArrayAdapter<Gruppe> {
        Data_Access dataAccess2;
        List<Gruppe> arr;
        //private TextView text;

        public MyThumbnailAdapter(Context context, int textViewResourceId, List<Gruppe> objects) {

            super(context, textViewResourceId, objects);
            dataAccess2 = new Data_Access(context);
            this.arr = objects;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view = null;
            LayoutInflater inflater = (LayoutInflater) ActivityGruppenverwaltung.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
            view = inflater.inflate(R.layout.list_row, parent, false);
            Button textButton = (Button) view.findViewById(R.id.bt_text);
            Button delButton = (Button) view.findViewById(R.id.bt_del);

            delButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dataAccess2.deleteGruppe(arr.get(position).getDbId());
                    arr.remove(position);
                    thadapter.notifyDataSetChanged();
                    Toast.makeText(ActivityGruppenverwaltung.this, "Gruppe deleted", Toast.LENGTH_SHORT).show();
                }
            });

            textButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    //Neues Intent anlegen
                    Intent nextScreen = new Intent(getApplicationContext(), ActivityGruppenmitglieder.class);

                    //Intent mit den Daten füllen
                    nextScreen.putExtra("GruppenId", arr.get(position).getDbId());
                    //nextScreen.putExtra("Passwort", inputPasswort.getText().toString());


                    // Intent starten und zur zweiten Activity wechseln

                    startActivity(nextScreen);
                }
            });

            textButton.setText(arr.get(position).getName());
            return view;
        }
    }
}


