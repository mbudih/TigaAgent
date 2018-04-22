package com.tiga.firebase.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by adikwidiasmono on 16/04/18.
 */

public class Invoice implements Serializable {
    private String AgentId;
    private Long CreateDate;
    private String InvoiceId;
    private List<InvoiceItem> Items;
    private String LastStatus;
    private List<InvoiceTracking> Tracking;

    public Invoice() {
    }

    public String getAgentId() {
        return AgentId;
    }

    public void setAgentId(String agentId) {
        AgentId = agentId;
    }

    public Long getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Long createDate) {
        CreateDate = createDate;
    }

    public String getInvoiceId() {
        return InvoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        InvoiceId = invoiceId;
    }

    public List<InvoiceItem> getItems() {
        return Items;
    }

    public void setItems(List<InvoiceItem> items) {
        Items = items;
    }

    public String getLastStatus() {
        return LastStatus;
    }

    public void setLastStatus(String lastStatus) {
        LastStatus = lastStatus;
    }

    public List<InvoiceTracking> getTracking() {
        return Tracking;
    }

    public void setTracking(List<InvoiceTracking> tracking) {
        Tracking = tracking;
    }
}
