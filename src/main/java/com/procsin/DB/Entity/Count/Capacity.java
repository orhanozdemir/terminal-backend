package com.procsin.DB.Entity.Count;

import com.procsin.DB.Entity.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Capacity", schema = "sevk", catalog = "PRS_SEVK")
public class Capacity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinTable(name="capacity_shelf", schema = "sevk", catalog = "PRS_SEVK", joinColumns=@JoinColumn(name="capacity_id"), inverseJoinColumns=@JoinColumn(name="shelf_id"))
    private Shelf shelf;

    @ManyToOne
    @JoinTable(name="capacity_enumeration", schema = "sevk", catalog = "PRS_SEVK", joinColumns=@JoinColumn(name="capacity_id"), inverseJoinColumns=@JoinColumn(name="enumeration_id"))
    private Enumeration enumeration;

    private int count;

    @ManyToOne
    @JoinTable(name="capacity_createdBy", schema = "sevk", catalog = "PRS_SEVK", joinColumns=@JoinColumn(name="capacity_id"), inverseJoinColumns=@JoinColumn(name="account_id"))
    private User createdBy;

    private Date createdAt;

    public Capacity() {
    }

    public Capacity(Shelf shelf, Enumeration enumeration, int count, User createdBy, Date createdAt) {
        this.shelf = shelf;
        this.enumeration = enumeration;
        this.count = count;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    public Enumeration getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(Enumeration enumeration) {
        this.enumeration = enumeration;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
