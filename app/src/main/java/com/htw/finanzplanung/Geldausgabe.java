package com.htw.finanzplanung;

import java.sql.Date;

public class Geldausgabe{
    private Date datum;
    private String was;
    private Float geldbetrag;

    public Geldausgabe(Date datum, String was, Float geldbetrag){

        this.datum = datum;
        this.was = was;
        this.geldbetrag = geldbetrag;
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

}
