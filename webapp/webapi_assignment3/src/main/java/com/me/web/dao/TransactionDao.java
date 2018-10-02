package com.me.web.dao;

import com.me.web.pojo.Transaction;

public class TransactionDao extends DAO{

    public Transaction insertTransaction(Transaction transaction){
        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(transaction);
        tx.commit();
        session.close();
        return null;
    }
}
