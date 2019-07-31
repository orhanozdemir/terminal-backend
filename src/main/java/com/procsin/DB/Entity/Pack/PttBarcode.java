package com.procsin.DB.Entity.Pack;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PttBarcode", schema = "test", catalog = "PRS_SEVK")
public class PttBarcode {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String pttBarcode;

    private Boolean isUsed;

    private Date creationDate;
    private Date consumptionDate;

    public PttBarcode() {}

    public PttBarcode(String pttBarcode, Boolean isUsed, Date creationDate, Date consumptionDate) {
        this.pttBarcode = pttBarcode;
        this.isUsed = isUsed;
        this.creationDate = creationDate;
        this.consumptionDate = consumptionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPttBarcode() {
        return pttBarcode;
    }

    public void setPttBarcode(String pttBarcode) {
        this.pttBarcode = pttBarcode;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getConsumptionDate() {
        return consumptionDate;
    }

    public void setConsumptionDate(Date consumptionDate) {
        this.consumptionDate = consumptionDate;
    }
}
