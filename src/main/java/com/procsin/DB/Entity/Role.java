package com.procsin.DB.Entity;

import javax.persistence.*;

@Entity
@Table(name = "Role", schema = "test", catalog = "PRS_SEVK")
public class Role {

    @Id @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
