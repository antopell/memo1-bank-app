package com.aninfo.model;

import javax.persistence.*;


@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "type")
    private String type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_cbu", referencedColumnName = "cbu")
    private Account account;

    public Transaction() {
    }
    public Transaction(Account account, Double amount, String type) {
        this.account = account;
        this.amount = amount;
        this.type = type;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
