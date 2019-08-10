package com.procsin.DB.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ErrorLog", schema = "sevk", catalog = "PRS_SEVK")
public class ErrorLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String errorCode;
    private String errorMessage;
    private Date date;

    private Long userId;

    public ErrorLog() {}

    public ErrorLog(String errorCode, String errorMessage, Long userId) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.userId = userId;
        this.date = new Date();
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
}
