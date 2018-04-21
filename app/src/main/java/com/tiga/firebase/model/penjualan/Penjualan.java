package com.tiga.firebase.model.penjualan;

import java.io.Serializable;
import java.util.List;

public class Penjualan implements Serializable{
    private String AgentId;
    private Long CreateDate;
    private List<TransactionItem> Items;
    private Long KKSNo;
    private String KKSOwner;
    private String PenjualanId;
    private Long TransactionDate;

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

    public List<TransactionItem> getItems() {
        return Items;
    }

    public void setItems(List<TransactionItem> items) {
        Items = items;
    }

    public Long getKKSNo() {
        return KKSNo;
    }

    public void setKKSNo(Long KKSNo) {
        this.KKSNo = KKSNo;
    }

    public String getKKSOwner() {
        return KKSOwner;
    }

    public void setKKSOwner(String KKSOwner) {
        this.KKSOwner = KKSOwner;
    }

    public String getPenjualanId() {
        return PenjualanId;
    }

    public void setPenjualanId(String penjualanId) {
        PenjualanId = penjualanId;
    }

    public Long getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(Long transactionDate) {
        TransactionDate = transactionDate;
    }
}
