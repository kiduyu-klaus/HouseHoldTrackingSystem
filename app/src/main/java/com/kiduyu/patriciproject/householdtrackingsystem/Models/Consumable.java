package com.kiduyu.patriciproject.householdtrackingsystem.Models;

public class Consumable {
    String itemname,itemremaining,itemcost,itemmeasure,itemtime;

    public Consumable(String itemname, String itemremaining, String itemcost, String itemmeasure, String itemtime) {
        this.itemname = itemname;
        this.itemremaining = itemremaining;
        this.itemcost = itemcost;
        this.itemmeasure = itemmeasure;
        this.itemtime = itemtime;
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
