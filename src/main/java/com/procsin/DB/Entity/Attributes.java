package com.procsin.DB.Entity;

import javax.persistence.*;

@Entity
@Table(name = "Attributes", schema = "integration", catalog = "PRS_DEVELOPMENT")
public class Attributes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(unique = true)
    public String keyString;
    public String value;

    public String description;

    public Attributes() {}
}