package com.htw.finanzplanung;

public class Gruppe{
    private Integer dbId;
    private String name;

    public Gruppe(Integer dbID, String name){
        this.dbId = (dbID==null?-1:dbID);
        this.name = (name==null?"":name);

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

    @Override
    public String toString() {
        return "(" + dbId + ", " + name + ") ";
    }

}
