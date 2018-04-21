package com.tiga.firebase.model;

import java.io.Serializable;

public class KKSData implements Serializable{

    private String address;
    private String name;
    private String no_id;
    private String no_kks;

    public String getNo_kks() {
        return no_kks;
    }

    public void setNo_kks(String no_kks) {
        this.no_kks = no_kks;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo_id() {
        return no_id;
    }

    public void setNo_id(String no_id) {
        this.no_id = no_id;
    }
}
