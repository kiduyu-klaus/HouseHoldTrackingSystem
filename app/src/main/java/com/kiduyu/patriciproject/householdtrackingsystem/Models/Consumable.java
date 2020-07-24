package com.kiduyu.patriciproject.householdtrackingsystem.Models;

public class Consumable {
    String name,total_purchased,when,remaining;

    public Consumable(){

    }

    public Consumable(String name, String total_purchased, String when, String remaining) {
        this.name = name;
        this.total_purchased = total_purchased;
        this.when = when;
        this.remaining = remaining;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal_purchased() {
        return total_purchased;
    }

    public void setTotal_purchased(String total_purchased) {
        this.total_purchased = total_purchased;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }
}
