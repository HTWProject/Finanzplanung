package com.htw.finanzplanung;

public class Mitglied{
    private Integer dbId;
    private String name;
    //private Float betrag;

    public Mitglied(Integer dbID, String name){
        this.dbId = (dbID==null?-1:dbID);
        this.name = (name==null?"":name);
        //this.betrag = (betrag==null?0f:betrag);

    }
    public Integer getDbId() {
        return dbId;
    }

    public boolean isDbIdUnknown() {
        return dbId == -1;
    }

    public String getName(){
        return name;
    }

    //public Float getBetrag(){
        //return betrag;
    //}

    @Override
    public String toString() {
        return "(" + dbId + ", " + name + ") ";
    }

}
