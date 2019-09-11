package com.procsin.DB.Entity.UserManagement;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "DeviceOwners", schema = "sevk", catalog = "PRS_SEVK")
public class DeviceOwners {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private String ownerName;

    @ManyToOne
    private Device device;

    private Date startDate;
    private Date endDate;

    private Boolean isActive;

    public DeviceOwners() {}

    public DeviceOwners(String ownerName, Device device, Date startDate, Date endDate, Boolean isActive) {
        this.ownerName = ownerName;
        this.device = device;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public Device getDevice() {
        return device;
    }
    public void setDevice(Device device) {
        this.device = device;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public Boolean getActive() {
        return isActive;
    }
    public void setActive(Boolean active) {
        isActive = active;
    }
}
