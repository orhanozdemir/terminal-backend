package com.procsin.DB.Entity.UserManagement;

import javax.persistence.*;

@Entity
@Table(name = "Device", schema = "sevk", catalog = "PRS_SEVK")
public class Device {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private String deviceName;
    @Column(unique = true)
    private String deviceId;
    private String platform;
    private Boolean isActive;

    public Device() {}

    public Device(String deviceName, String deviceId, String platform, Boolean isActive) {
        this.deviceName = deviceName;
        this.deviceId = deviceId;
        this.platform = platform;
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public Boolean getActive() {
        return isActive;
    }
    public void setActive(Boolean active) {
        isActive = active;
    }
}
