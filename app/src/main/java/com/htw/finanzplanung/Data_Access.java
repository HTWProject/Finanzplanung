package com.htw.finanzplanung;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

//datum TEXT as strings ("YYYY-MM-DD").

public class Data_Access extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Finanzplanung.sqlite";


    public Data_Access(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = OFF;");
        db.execSQL("CREATE TABLE IF NOT EXISTS user " +
                        "(" +
                            "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "  name TEXT," +
                            "  email TEXT UNIQUE," +
                            "  passwort TEXT NOT NULL" +
                            "  activationcode INTEGER DEFAULT 0" +
                        ")"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS gruppe " +
                        "(" +
                        "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "  name TEXT," +
                        "  user_id INTEGER REFERENCES user(_id) ON UPDATE CASCADE ON DELETE CASCADE," +
                        ")"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS settings " +
                        "(" +
                        "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "  server TEXT," +
                        "  mobile_sync NUMERIC," +
                        "  user_id INTEGER REFERENCES user(_id) ON UPDATE CASCADE ON DELETE CASCADE," +
                        ")"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS ausgabe " +
                        "(" +
                        "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "  datum TEXT" +
                        "  was TEXT," +
                        "  bertrag REAL," +
                        "  user_id INTEGER REFERENCES user(_id) ON UPDATE CASCADE ON DELETE CASCADE," +
                        "  gruppe_id INTEGER REFERENCES gruppe(_id) ON UPDATE CASCADE ON DELETE CASCADE" +
                        ")"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS user_ist_mitglied_in_gruppe " +
                        "(" +
                        "  user_id INTEGER REFERENCES user(_id) ON UPDATE CASCADE ON DELETE CASCADE," +
                        "  gruppe_id INTEGER REFERENCES gruppe(_id) ON UPDATE CASCADE ON DELETE CASCADE," +
                        "  PRIMARY KEY(user_id,gruppe_id)" +
                        ")"
        );
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("PRAGMA foreign_keys = OFF;");
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS ausgabe");
        db.execSQL("DROP TABLE IF EXISTS gruppe");
        db.execSQL("DROP TABLE IF EXISTS settings");
        db.execSQL("DROP TABLE IF EXISTS user_ist_mitglied_in_gruppe");
        db.execSQL("PRAGMA foreign_keys = ON;");
        onCreate(db);
    }

    //GruppenVerwalten

    public ArrayList<Gruppe> getMeineGruppen(Integer user_id) {
        ArrayList<Gruppe> Gruppen = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor c = db.rawQuery("SELECT _id, name FROM gruppe  WHERE user_id = " + user_id + " ", null);


        if(c.moveToFirst()){
            do{
                Gruppen.add(new Gruppe(c.getInt(0), c.getString(1)));
            }while(c.moveToNext());
        }
        c.close();
        db.close();

        return Gruppen;
    }

    public int deleteGruppe(Integer user_id, Integer gruppen_id) {
        return 0;
    }

    public int addGruppe(Integer user_id,String gruppenname){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO gruppe (name, user_id) VALUES ('" + gruppenname + "' , " + user_id + ");");
        db.close();
        return 0;
    }

    public Integer getGruppenMasterID(Integer gruppen_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer user_id = -1;

        Cursor c = db.rawQuery("SELECT user_id FROM gruppe  WHERE _id = " + gruppen_id + " ", null);


        if(c.moveToFirst()){
            do{
                user_id = c.getInt(0);
            }while(c.moveToNext());
        }
        c.close();
        db.close();

        return user_id;
    }


    //Finanzen
    public int addGeldausgabe(String datum,String was, Float betrag, Integer user_id, Integer gruppen_id){
        SQLiteDatabase db = this.getWritableDatabase();

        //datum TEXT as strings ("YYYY-MM-DD").
        db.execSQL("INSERT INTO ausgabe (was, ausgabe, betrag, user_id, gruppe_id) " +
                "VALUES (" +
                " '" + datum + "', " +
                " '" + was + "', " +
                "  " + betrag + " , " +
                "  " + user_id + " , " +
                "  " + gruppen_id + "   " +
                ");");
        db.close();
        return 0;
    }

    public Float getGruppenGesamtbetrag(String startdatum, String enddatum, Integer gruppen_id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(
                "SELECT sum(geldbetrag) AS Summe " +
                "FROM ausgabe " +
                "WHERE gruppe_id = " + gruppen_id +" " +
                        "AND ausgabe.datum BETWEEN '" + startdatum + "' " +
                        "AND '" + enddatum + "' ", null
        );
        Float gesamtgeldbetrag = 0f;
        if(c.moveToFirst()){
            gesamtgeldbetrag = c.getFloat(0);
        }
        c.close();
        db.close();

        return gesamtgeldbetrag;
    }

    public Float getUserGesamtbetrag(String startdatum, String enddatum, Integer gruppen_id, Integer user_id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(
                "SELECT sum(ausgabe.geldbetrag) AS Summe " +
                "FROM ausgabe " +
                "INNER JOIN user_ist_mitglied_in_gruppe " +
                        "ON ausgabe.gruppe_id = user_ist_mitglied_in_gruppe.gruppe_id  " +
                "WHERE user_ist_mitglied_in_gruppe.gruppe_id = " + gruppen_id + " " +
                        "AND user_ist_mitglied_in_gruppe.user_id = " + user_id + " " +
                        "AND ausgabe.datum BETWEEN '" + startdatum + "' AND '" + enddatum + "' ", null
        );
        Float gesamtgeldbetrag = 0f;
        if(c.moveToFirst()){
            gesamtgeldbetrag = c.getFloat(0);
        }
        c.close();
        db.close();

        return gesamtgeldbetrag;
    }

    public ArrayList<Geldausgabe> getUserGeldausgaben(Integer gruppen_id, Integer user_id){
        ArrayList<Geldausgabe> Ausgaben = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(
                "SELECT ausgabe.* " +
                "FROM ausgabe " +
                "INNER JOIN user_ist_mitglied_in_gruppe " +
                        "ON ausgabe.gruppe_id = user_ist_mitglied_in_gruppe.gruppe_id  " +
                "WHERE user_ist_mitglied_in_gruppe.gruppe_id = " + gruppen_id + " " +
                        "AND user_ist_mitglied_in_gruppe.user_id = " + user_id + " ", null
        );

        if(c.moveToFirst()){
            do{
                Ausgaben.add(new Geldausgabe(c.getInt(0), c.getString(1),c.getString(2),c.getFloat(3)));
            }while(c.moveToNext());
        }
        c.close();
        db.close();

        return Ausgaben;
    }

    //Gruppenmitglieder
    public ArrayList<Mitglied> getGruppenMitglieder(Integer gruppe_id){
        ArrayList<Mitglied> Mitglieder = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(
                "SELECT user._id, user.name " +
                "FROM user " +
                "INNER JOIN user_ist_mitglied_in_gruppe " +
                        "ON user._id = user_ist_mitglied_in_gruppe.user_id  " +
                "WHERE user_ist_mitglied_in_gruppe.gruppe_id = " + gruppe_id + " ", null
        );

        if(c.moveToFirst()){
            do{
                Mitglieder.add(new Mitglied(c.getInt(0), c.getString(1)));
            }while(c.moveToNext());
        }
        c.close();
        db.close();
        return Mitglieder;
    }

    public int deleteMitglied(Integer user_id, Integer gruppen_id, Integer mitglied_id){
        return 0;
    }

    public int addGruppenMitglied(Integer user_id, Integer gruppen_id, Integer mitglied_id){
        SQLiteDatabase db = this.getWritableDatabase();

        //datum TEXT as strings ("YYYY-MM-DD").
        db.execSQL("INSERT INTO user_ist_mitglied_in_gruppe ( user_id, gruppe_id) " +
                "VALUES (" +
                "  " +user_id       +" , " +
                "  " +gruppen_id    +"   " +
                ");");
        db.close();
        return 0;
    }

    //Home
    public ArrayList<Gruppe> getGruppen(Integer user_id){
        ArrayList<Gruppe> Gruppen = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor c = db.rawQuery(
                "SELECT gruppe._id, gruppe.name " +
                "FROM gruppe " +
                "INNER JOIN user_ist_mitglied_in_gruppe " +
                        "ON gruppe._id = user_ist_mitglied_in_gruppe.gruppe_id   " +
                "WHERE user_ist_mitglied_in_gruppe.user_id = " + user_id + " ", null
        );


        if(c.moveToFirst()){
            do{
                Gruppen.add(new Gruppe(c.getInt(0), c.getString(1)));
            }while(c.moveToNext());
        }
        c.close();
        db.close();

        return Gruppen;
    }

    public int verlasseGruppe(Integer user_id, Integer gruppen_id){
        if(user_id.equals(getGruppenMasterID(gruppen_id))){

            return 1;
        }else {
            return 0;
        }

    }

    //Startseite
    public Mitglied Login(String email,String passwort){
        return null;
    }

    public int sendPasswortToEmail(String email){
        return 0;
    }

    //Registration
    public int addUser(String name, String email, String passwort){

        SQLiteDatabase db = this.getWritableDatabase();

        //datum TEXT as strings ("YYYY-MM-DD").
        db.execSQL("INSERT INTO user (name, email, passwort) " +
                "VALUES (" +
                " '" +name            +"', " +
                " '" +email           +"', " +
                " '" +passwort        +"' , " +
                ");");
        db.close();
        return 0;
    }

    //Settings
    public int setNewPasswort(String newpasswort, String oldpasswort, String user_id){
        return 0;

    }
    public int setNewName(String newName, String passwort, String user_id){
        return 0;

    }
    public int setNewServer(String newServer, String passwort, String user_id){
        return 0;

    }
    public Boolean setMobileSync(Boolean MobileSync, String passwort, String user_id){
        return true;

    }
    public Boolean getMobileSyncStatus(String user_id){
        return true;

    }


}
