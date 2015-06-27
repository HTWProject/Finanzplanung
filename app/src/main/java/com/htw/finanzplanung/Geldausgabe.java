package com.htw.finanzplanung;

import java.sql.Date;

public class Geldausgabe{

    private int dbId;
    private Date datum;
    private String was;
    private Float geldbetrag;

    public Geldausgabe(Integer dbID, Date datum, String was, Float geldbetrag){
        this.dbId = (dbID==null?-1:dbID);
        this.datum = datum;
        this.was = (was==null?"":was);
        this.geldbetrag = (geldbetrag==null?0:geldbetrag);
    }

    public Date getDatum(){
        return datum;
    }
    public String getWas(){
        return was;
    }
    public Float getGeldbetrag(){
        return geldbetrag;
    }


    public int getDbId() {
        return dbId;
    }

    public boolean isDbIdUnknown() {
        return dbId == -1;
    }

    @Override
    public String toString() {
        return "(" + datum.toString() + ", " + was + ", " + geldbetrag + ") ";
    }

}
