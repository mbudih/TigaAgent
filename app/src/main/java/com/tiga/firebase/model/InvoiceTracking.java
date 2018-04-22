package com.tiga.firebase.model;

import java.io.Serializable;

/**
 * Created by adikwidiasmono on 16/04/18.
 */

public class InvoiceTracking implements Serializable {
    private Long CreateDate;
    private String Description;
    private String Status;
    private String Title;

    public InvoiceTracking() {
    }

    public Long getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Long createDate) {
        CreateDate = createDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
