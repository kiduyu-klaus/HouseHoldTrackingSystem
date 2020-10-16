package com.kiduyu.patriciproject.householdtrackingsystem.Models;

/**
 * Created by Kiduyu klaus
 * on 17/10/2020 00:06 2020
 */
public class History {
    String id, itemname,itemorder,itemagent,itemurgency,status;


    public History(String id, String itemname, String itemorder, String itemagent, String itemurgency, String status) {
        this.id = id;
        this.itemname = itemname;
        this.itemorder = itemorder;
        this.itemagent = itemagent;
        this.itemurgency = itemurgency;
        this.status = status;
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

    public String getItemorder() {
        return itemorder;
    }

    public void setItemorder(String itemorder) {
        this.itemorder = itemorder;
    }

    public String getItemagent() {
        return itemagent;
    }

    public void setItemagent(String itemagent) {
        this.itemagent = itemagent;
    }

    public String getItemurgency() {
        return itemurgency;
    }

    public void setItemurgency(String itemurgency) {
        this.itemurgency = itemurgency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
