package com.procsin.DB.Entity.Pack;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PttBarcode", schema = "sevk", catalog = "PRS_SEVK")
public class PttBarcode {

    @Id @GeneratedValue
    public long id;

    @Column(unique = true)
    public String barcode;

    public boolean isUsed;
    public Date usedDate;

    public PttBarcode() {}
}
