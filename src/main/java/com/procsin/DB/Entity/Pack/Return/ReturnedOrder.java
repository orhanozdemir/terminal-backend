package com.procsin.DB.Entity.Pack.Return;

import com.procsin.DB.Entity.UserManagement.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ReturnedOrder", schema = "sevk", catalog = "PRS_SEVK")
public class ReturnedOrder {

    public enum ReturnedOrderStatus {
        MUSTERI_HIZMETLERI_BEKLENIYOR,
        YENIDEN_CIKIS_BEKLENIYOR,
        YENIDEN_CIKIS_SAGLANDI,
        KISMI_GONDERIM_BEKLENIYOR,
        KISMI_GONDERIM_SAGLANDI,
        GIDER_PUSULASI_BEKLENIYOR,
        GIDER_PUSULASI_KESILDI
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String orderCode;
    public String customerFullName;
    public ReturnedOrderStatus status;
    public String description;
    public Date createdAt;
    public Date lastUpdatedAt;

    @ManyToOne
    public User createdBy;
    @ManyToOne
    public User lastUpdatedBy;

    public String trackingCode;

    public ReturnedOrder() {}
}
