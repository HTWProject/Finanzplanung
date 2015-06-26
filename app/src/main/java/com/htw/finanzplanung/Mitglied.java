package com.htw.finanzplanung;

public class Mitglied{
    private Integer id;
    private String name;

    public Mitglied(Integer id, String name){
        this.id = id;
        this.name = name;

    }

    public Integer getID(){
        return id;
    }
    public String getWas(){
        return name;
    }

}
