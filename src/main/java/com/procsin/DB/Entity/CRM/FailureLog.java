package com.procsin.DB.Entity.CRM;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "FailureLog", schema = "crm", catalog = "PRS_SEVK")
public class FailureLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public enum FailureType{
        DAMAGED, MISSING, OTHER
    }

    public String productCode;
    public String orderCode;

    public String description;

    public Date date;

    public String failureType;

    public FailureLog() {}
}
