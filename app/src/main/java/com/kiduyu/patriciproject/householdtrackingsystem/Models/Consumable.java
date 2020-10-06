package com.kiduyu.patriciproject.householdtrackingsystem.Models;

public class Consumable {
    String id,itemname,itemremaining,itemcost,itemmeasure,itemtime;



    public Consumable(String id, String itemname, String itemremaining, String itemcost, String itemmeasure, String itemtime) {
        this.id = id;
        this.itemname = itemname;
        this.itemremaining = itemremaining;
        this.itemcost = itemcost;
        this.itemmeasure = itemmeasure;
        this.itemtime = itemtime;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemremaining() {
        return itemremaining;
    }

    public void setItemremaining(String itemremaining) {
        this.itemremaining = itemremaining;
    }

    public String getItemcost() {
        return itemcost;
    }

    public void setItemcost(String itemcost) {
        this.itemcost = itemcost;
    }

    public String getItemmeasure() {
        return itemmeasure;
    }

    public void setItemmeasure(String itemmeasure) {
        this.itemmeasure = itemmeasure;
    }

    public String getItemtime() {
        return itemtime;
    }

    public void setItemtime(String itemtime) {
        this.itemtime = itemtime;
    }
}
