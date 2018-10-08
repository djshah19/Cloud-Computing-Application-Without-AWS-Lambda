package com.me.web.pojo;


import com.me.web.dao.AttachmentDao;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", unique = true, nullable = false)
    private int id;

    @Column(name="description")
    private String description;

    @Column(name="merchant")
    private String merchant;

    @Column(name="amount")
    private double amount;

    @Column(name="date")
    private String date;

    @Column(name="category")
    private String category;

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY, optional = false)
    private User user;

    @Transient
    private ArrayList<Attachment> attachmentList;

    public Transaction(){

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Attachment> getAttachmentList(int id) throws Exception{
        attachmentList = new ArrayList<>();
        AttachmentDao attachmentDao = new AttachmentDao();
        //attachmentList = attachmentDao.getAttachmentByTransaction(id);
        return attachmentList;
    }
}
