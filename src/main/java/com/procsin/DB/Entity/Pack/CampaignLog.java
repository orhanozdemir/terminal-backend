package com.procsin.DB.Entity.Pack;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CampaignLog", schema = "sevk", catalog = "PRS_SEVK")
public class CampaignLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Campaign campaign;
    private Date date;
    private String orderCode;

    public CampaignLog() {}

    public CampaignLog(Campaign campaign, Date date, String orderCode) {
        this.campaign = campaign;
        this.date = date;
        this.orderCode = orderCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
