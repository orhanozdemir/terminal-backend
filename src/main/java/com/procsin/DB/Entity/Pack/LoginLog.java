package com.procsin.DB.Entity.Pack;

import com.procsin.DB.Entity.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "LoginLog", schema = "test", catalog = "PRS_SEVK")
public class LoginLog {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private User user;
    private Date date;

    public LoginLog() {}

    public LoginLog(User user) {
        this.user = user;
        this.date = new Date();
    }
}