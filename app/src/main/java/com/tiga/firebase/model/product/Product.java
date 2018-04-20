package com.tiga.firebase.model.product;

public class Product {
    private String name;
    private boolean is_subsidi;
    private double price;
    private String img_url;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_subsidi() {
        return is_subsidi;
    }

    public void setIs_subsidi(boolean is_subsidi) {
        this.is_subsidi = is_subsidi;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
