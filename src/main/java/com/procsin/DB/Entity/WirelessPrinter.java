package com.procsin.DB.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WirelessPrinter", schema = "devices", catalog = "PRS_DEVELOPMENT")
public class WirelessPrinter {

    @Id
    public String serialNumber;

    public String tcpIp;
    public int port;

    boolean isActive;

    public WirelessPrinter() {}
}
