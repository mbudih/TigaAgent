package com.tiga.firebase.model.product;

import java.io.Serializable;

public class ProductBuy implements Serializable{
    Product product;
    int qty;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal(){
        return product.getPrice()*getQty();
    }
}
