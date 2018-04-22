package com.tiga.firebase.model;

import java.io.Serializable;

/**
 * Created by adikwidiasmono on 16/04/18.
 */

public class InvoiceItem implements Serializable {
    private String ImageURL;
    private String Product;
    private Long Quantity;

    public InvoiceItem() {
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public Long getQuantity() {
        return Quantity;
    }

    public void setQuantity(Long quantity) {
        Quantity = quantity;
    }
}
