package com.procsin.DB.Entity.Pack.Return;

import com.procsin.DB.Entity.UserManagement.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Gift", schema = "panel", catalog = "PRS_SEVK")
public class Gift {

    @Id
    @GeneratedValue
    public Long id;

    public String name;
    public String description;

    public Boolean didComplete;

    public Date createdAt;
    @ManyToOne
    public User createdBy;

    public Gift() {}
}
