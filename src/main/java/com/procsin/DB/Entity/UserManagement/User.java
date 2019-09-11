package com.procsin.DB.Entity.UserManagement;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Account", schema = "sevk", catalog = "PRS_SEVK")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "account_role", schema = "sevk", catalog = "PRS_SEVK", joinColumns = {
            @JoinColumn(name = "account_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<Role> roles;

    private String name;
    private String surname;

    private Boolean isActive;

    private String platform;
    private String pushToken;

    public User() {}

    public User(String username, String password, Set<Role> roles, String name, String surname, Boolean isActive, String platform, String pushToken) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.name = name;
        this.surname = surname;
        this.isActive = isActive;
        this.platform = platform;
        this.pushToken = pushToken;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }
}
