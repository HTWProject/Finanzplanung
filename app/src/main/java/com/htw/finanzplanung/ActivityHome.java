package com.htw.finanzplanung;

import android.app.Activity;
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

import java.util.ArrayList;


public class ActivityHome extends Activity {
    // Anlegen der Variabeln
    Data_Access dataAccess = new Data_Access(this);

    private ListView numberList;
    MyThumbnailAdapter thadapter = null;
    ArrayList<Gruppe> meineGruppen;
/*
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(), "onRestart", Toast.LENGTH_SHORT).show();
    }
*/

    int backButtonCount = 0;

    public void onBackPressed()
    {

        if(backButtonCount >= 1)
        {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            android.os.Process.killProcess(android.os.Process.myPid());
            //ActivityHome.this.finish();
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
    /*
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    */
    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(getApplicationContext(), "onResume", Toast.LENGTH_SHORT).show();
        meineGruppen = dataAccess.getGruppen();


        numberList = (ListView) findViewById(R.id.listViewGruppen);


        thadapter = new MyThumbnailAdapter(getApplicationContext(), R.layout.list_row, meineGruppen);
        numberList.setAdapter(thadapter);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //Button settingsButton = (Button) findViewById(R.id.bt_settings);
        Button gruppenverwaltungButton = (Button) findViewById(R.id.bt_gruppen);
        //Button logoutButton = (Button) findViewById(R.id.bt_logout);


/*
        // ClickListener implementieren für den Button zum Wechsel der Activity
        settingsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {


            }
        });
*/
        meineGruppen = dataAccess.getGruppen();


        numberList = (ListView) findViewById(R.id.listViewGruppen);


        thadapter = new MyThumbnailAdapter(getApplicationContext(), R.layout.list_row, meineGruppen);
        numberList.setAdapter(thadapter);

/*
        // ClickListener implementieren für den Button zum Wechsel der Activity
        logoutButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                dataAccess.Logout();
                //Neues Intent anlegen
                Intent nextScreen = new Intent(getApplicationContext(), ActivityLogin.class);
                // Intent starten und zur zweiten Activity wechseln
                startActivity(nextScreen);

            }
        });

*/
        // ClickListener implementieren für den Button zum Wechsel der Activity
        gruppenverwaltungButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Neues Intent anlegen
                Intent nextScreen = new Intent(getApplicationContext(), ActivityGruppenverwaltung.class);
                // Intent starten und zur zweiten Activity wechseln
                startActivity(nextScreen);

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_home, menu);
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
            //Neues Intent anlegen
            Intent nextScreen = new Intent(getApplicationContext(), ActivitySettings.class);
            // Intent starten und zur zweiten Activity wechseln
            startActivity(nextScreen);
            return true;
        }
        if (id == R.id.logout) {
            dataAccess.Logout();
            //Neues Intent anlegen
            //Intent nextScreen = new Intent(getApplicationContext(), ActivityLogin.class);
            // Intent starten und zur zweiten Activity wechseln

            //startActivity(nextScreen);
            ActivityHome.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class MyThumbnailAdapter extends ArrayAdapter<Gruppe> {
        Data_Access dataAccess2;
        ArrayList<Gruppe> arr;
        Context context;

        public MyThumbnailAdapter(Context context, int textViewResourceId, ArrayList<Gruppe> objects) {

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
                    dataAccess2.verlasseGruppe(arr.get(position).getDbId());
                    arr.remove(position);
                    thadapter.notifyDataSetChanged();
                    Toast.makeText(context, "Mitglied deleted", Toast.LENGTH_SHORT).show();
                }
            });

            textButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    //Neues Intent anlegen
                    Intent nextScreen = new Intent(getApplicationContext(), ActivityFinanzen.class);

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
