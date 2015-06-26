package com.htw.finanzplanung;


import java.util.ArrayList;
import java.sql.Date;


public class Data_Access {


    //GruppenVerwalten

    public ArrayList<Gruppe> getMeineGruppen(Integer user_id) {
        ArrayList<Gruppe> Gruppen = new ArrayList<>();
        //    for(){
        //        Gruppe.add(new Gruppe());
        //    };
        return Gruppen;
    }

    public int deleteGruppe(Integer user_id, Integer gruppen_id) {
        return 0;
    }

    public int addGruppe(Integer user_id,String gruppenname){
        return 0;
    }

    public Integer getGruppenMasterID(Integer gruppen_id){
        return 0;
    }


    //Finanzen
    public int addGeldausgabe(Date datum,String was, Float Betrag, Integer user_id, Integer gruppen_id){
        return 0;
    }

    public Float getGruppenGesamtbetrag(Date startdatum, Date enddatum, Integer gruppen_id){
        return null;
    }

    public Float getUserGesamtbetrag(Date startdatum, Date enddatum, Integer gruppen_id, Integer user_id){
        return null;
    }

    public ArrayList<Geldausgabe> getUserGeldausgaben(Integer gruppen_id, Integer user_id){
        ArrayList<Geldausgabe> Ausgaben = new ArrayList<>();
    //    for(){
    //        Ausgaben.add(new Geldausgabe());
    //    };

        return(Ausgaben);
    }

    //Gruppenmitglieder
    public ArrayList<Mitglied> getGruppenMitglieder(){
        ArrayList<Mitglied> Mitglieder = new ArrayList<>();
        //    for(){
        //        Mitglieder.add(new Gruppe());
        //    };
        return Mitglieder;
    }

    public int deleteMitglied(Integer user_id, Integer gruppen_id, Integer mitglied_id){
        return 0;
    }

    public int addGruppenMitglied(Integer user_id, Integer gruppen_id, Integer mitglied_id){
        return 0;
    }

    //Home
    public ArrayList<Gruppe> getGruppen(Integer user_id){
        ArrayList<Gruppe> Gruppen = new ArrayList<>();
        //    for(){
        //        Gruppe.add(new Gruppe());
        //    };
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
        return 0;
    }

}
