package com.htw.finanzplanung;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ActivityFinanzen extends AppCompatActivity {

    // Anlegen der Variabeln
    private EditText text_date;
    private EditText was;
    private EditText geldbetrag;
    private EditText geldgesamtbetrag;
    private EditText text_date_von;
    private EditText text_date_bis;
    private int year;
    private int month;
    private int day;
    static final int DATE_DIALOG_ID = 100;
    static final int DATE_DIALOG_ID_von = 101;
    static final int DATE_DIALOG_ID_bis = 102;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_finanzen);
        gruppenID = getIntent().getIntExtra( "GruppenId", 0);
        Log.d("ResponseGruppe: ", "> " + gruppenID);

        text_date = (EditText) findViewById(R.id.txt_Datum);
        text_date_von = (EditText) findViewById(R.id.txt_DatumVon);
        text_date_bis = (EditText) findViewById(R.id.txt_DatumBis);
        disableEditText(text_date);
        disableEditText(text_date_von);
        disableEditText(text_date_bis);

        was = (EditText) findViewById(R.id.txt_Was);
        geldbetrag = (EditText) findViewById(R.id.txt_Betrag);
        geldgesamtbetrag = (EditText) findViewById(R.id.txt_gesamtBetrag);

        final Calendar calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        text_date.setText(new StringBuilder()
                        // Month is 0 based, so you have to add 1
                        .append(new DecimalFormat("0000").format(year)).append("-")
                        .append(new DecimalFormat("00").format(month + 1)).append("-")
                        .append(new DecimalFormat("00").format(day)).append("")
        );

        text_date_von.setText(new StringBuilder()
                        // Month is 0 based, so you have to add 1
                        .append(new DecimalFormat("0000").format(year)).append("-")
                        .append(new DecimalFormat("00").format(month)).append("-")
                        .append(new DecimalFormat("00").format(day)).append("")
        );

        text_date_bis.setText(new StringBuilder()
                        // Month is 0 based, so you have to add 1
                        .append(new DecimalFormat("0000").format(year)).append("-")
                        .append(new DecimalFormat("00").format(month + 1)).append("-")
                        .append(new DecimalFormat("00").format(day)).append("")
        );



        Button addBetragButton = (Button) findViewById(R.id.bt_addBetrag);


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListViewMitgliederAusgaben);

        // preparing list data

        //prepareListData();
        listDataChild = new HashMap<Mitglied, List<Geldausgabe>>();
        listDataHeader = new ArrayList<Mitglied>();

        updateListe();


        text_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);

            }
        });
        text_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDialog(DATE_DIALOG_ID);
                }
            }
        });


        text_date_von.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID_von);

            }
        });
        text_date_von.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDialog(DATE_DIALOG_ID_von);
                }
            }
        });


        text_date_bis.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID_bis);

            }
        });
        text_date_bis.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDialog(DATE_DIALOG_ID_bis);
                }
            }
        });






        addBetragButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!geldbetrag.getText().toString().isEmpty()){
                    dataAccess.addGeldausgabe(text_date.getText().toString(), was.getText().toString(), Float.parseFloat(geldbetrag.getText().toString()), gruppenID);
                    updateListe();
                }else{
                    Toast.makeText(getApplicationContext(), "Please Insert a Value", Toast.LENGTH_SHORT).show();
                }


            }
        });






        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition).getName()
                                + " : "
                                + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).toString(), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition).getName() + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition).getName() + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });



    }

    Data_Access dataAccess = new Data_Access(this);

    Integer gruppenID;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<Mitglied> listDataHeader;

    HashMap<Mitglied, List<Geldausgabe>> listDataChild;
    public void updateListe(){
        geldgesamtbetrag.setText(dataAccess.getGruppenGesamtbetrag(text_date_von.getText().toString(),text_date_bis.getText().toString(),gruppenID)+" €");
        listDataHeader = dataAccess.getGruppenMitglieder(gruppenID);
       // Log.d("Response1: ", "> " + listDataHeader.get(0).toString());
        //Log.d("Response1: ", "> " + listDataHeader.size());

        for(int x = 0; x < listDataHeader.size();x++) {
           // Log.d("Response2: ", "> " + x);
           // Log.d("Response2id: ", "> " + listDataHeader.get(x).getDbId());
            listDataChild.put(listDataHeader.get(x), dataAccess.getUserGeldausgaben(text_date_von.getText().toString(), text_date_bis.getText().toString(), gruppenID, listDataHeader.get(x).getDbId()));
           // Log.d("Response2: ", "> " +listDataHeader.get(x).toString() + listDataChild.get(listDataHeader.get(x)).get(x).toString() );
        }

       // Log.d("Response3: ", "> " + listDataChild.size());

/*
        Iterator it = listDataChild.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            //System.out.println(pair.getKey() + " = " + pair.getValue());
           // Log.d("ResponseHash1: ", "> " + "key" + pair.getKey().toString() + " value" + pair.getValue().toString());
            it.remove(); // avoids a ConcurrentModificationException
        }
*/
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, text_date_von.getText().toString(),text_date_bis.getText().toString(),gruppenID);
        //Log.d("Response4: ", "> " + "OK");
        // setting list adapter
        expListView.setAdapter(listAdapter);
       // Log.d("Response5: ", "> " + "Nice");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_finanzen, menu);
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


    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,day);
            case DATE_DIALOG_ID_von:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListenerVon, year, month,day);
            case DATE_DIALOG_ID_bis:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListenerBis, year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into Text View
            text_date.setText(new StringBuilder().append(new DecimalFormat("0000").format(year)).append("-").append(new DecimalFormat("00").format(month + 1)).append("-").append(new DecimalFormat("00").format(day)).append(""));


        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListenerVon = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into Text View
            text_date_von.setText(new StringBuilder().append(new DecimalFormat("0000").format(year)).append("-").append(new DecimalFormat("00").format(month + 1)).append("-").append(new DecimalFormat("00").format(day)).append(""));

            updateListe();
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListenerBis = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into Text View
            text_date_bis.setText(new StringBuilder().append(new DecimalFormat("0000").format(year)).append("-").append(new DecimalFormat("00").format(month + 1)).append("-").append(new DecimalFormat("00").format(day)).append(""));

            updateListe();
        }
    };


    private void disableEditText(EditText editText) {
        //editText.setFocusable(false);
        //editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        //editText.setBackgroundColor(Color.TRANSPARENT);
    }

}
