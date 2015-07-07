package com.htw.finanzplanung;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
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
import java.util.List;


public class ActivityFinanzen extends Activity {

    // Anlegen der Variabeln
    private TextView text_date;
    private TextView was;
    private TextView geldbetrag;
    private TextView geldgesamtbetrag;
    private TextView text_date_von;
    private TextView text_date_bis;
    private int year;
    private int month;
    private int day;
    static final int DATE_DIALOG_ID = 100;
    static final int DATE_DIALOG_ID_von = 101;
    static final int DATE_DIALOG_ID_bis = 102;

    EditText inputGeldausgabe;
    Data_Access dataAccess = new Data_Access(this);

    private ListView numberList;
    ArrayList<Geldausgabe> meineGeldausgabe;
    Integer gruppenID;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_finanzen);
        gruppenID = getIntent().getIntExtra( "GruppenId", 0);
        Log.d("ResponseGruppe: ", "> " + gruppenID);

        text_date = (TextView) findViewById(R.id.txt_Datum);
        text_date_von = (TextView) findViewById(R.id.txt_DatumVon);
        text_date_bis = (TextView) findViewById(R.id.txt_DatumBis);

        was = (TextView) findViewById(R.id.txt_Was);
        geldbetrag = (TextView) findViewById(R.id.txt_Betrag);
        geldgesamtbetrag = (TextView) findViewById(R.id.txt_gesamtBetrag);

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

        updateGesamtBeträge();


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
                dataAccess.addGeldausgabe(text_date.getText().toString(), was.getText().toString(), Float.valueOf(geldbetrag.getText().toString()), gruppenID);
                updateGesamtBeträge();

            }
        });
        //Toast.makeText(getApplicationContext(), dp.getDayOfMonth() + " " + dp.getMonth() + " " + dp.getYear(), Toast.LENGTH_SHORT).show();

/*
        addBetragButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), dp.getDayOfMonth() + " " + dp.getMonth() + " " + dp.getYear(),  Toast.LENGTH_SHORT).show();

            }
        });
*/


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListViewMitgliederAusgaben);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);







        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });



    }
    public void updateGesamtBeträge(){
            geldgesamtbetrag.setText(dataAccess.getGruppenGesamtbetrag(text_date_von.getText().toString(),text_date_bis.getText().toString(),gruppenID)+" €");
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



    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
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

            updateGesamtBeträge();
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

            updateGesamtBeträge();
        }
    };


}
