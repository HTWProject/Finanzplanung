package com.htw.finanzplanung;


public class Geldausgabe{

    private int dbId;
    private String datum; //datum TEXT as strings ("YYYY-MM-DD").
    private String was;
    private Float geldbetrag;

    public Geldausgabe(Integer dbID, String datum, String was, Float geldbetrag){
        this.dbId = (dbID==null?-1:dbID);
        this.datum = (datum==null?"":datum);
        this.was = (was==null?"":was);
        this.geldbetrag = (geldbetrag==null?0:geldbetrag);
    }

    public String getDatum(){
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
        return "(" + datum + ", " + was + ", " + geldbetrag + ") ";
    }

}
