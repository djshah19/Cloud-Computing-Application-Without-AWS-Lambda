package com.me.web.dao;

import com.me.web.pojo.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import java.util.List;

public class TransactionDao extends DAO{

    public int insertTransaction(Transaction transaction) throws Exception{
       try{
           begin();
           getSession().save(transaction);
           commit();
           return 2;
       } catch(HibernateException e){
           rollback();
           throw new Exception("Transaction not saved: "+e.getMessage());
        }

    }

    public Transaction getTransactionById(int id) throws Exception{
        try {
            begin();
            Transaction transaction = (Transaction)getSession().get(Transaction.class, id);
            if(transaction!=null){
                return transaction;
            }else{
                return null;
            }
        }catch(HibernateException e){
            rollback();
            throw new Exception("Transaction not found: "+e.getMessage());
        }

    }

    public int deleteTransaction(int id) throws Exception{
        try{
            begin();
            Transaction tx = getTransactionById(id);
            if(tx!=null){
            getSession().delete(tx);
            commit();
            return 2;
            }
            return 1;
        } catch(HibernateException e){
            rollback();
            throw new Exception("Transaction not saved: "+e.getMessage());
        }

    }

    public int editTransaction(Transaction tx)throws Exception{
        try{
            begin();
            getSession().saveOrUpdate(tx);
            commit();
            return 2;
        }catch (HibernateException e){
            rollback();
            throw new Exception("Exception while updating transaction"+e.getMessage());
        }

    }

    public List<Transaction> getAllTransaction(int id)throws Exception{
        try{
            begin();
            Query q = getSession().createQuery("from transaction where user_id = :id");
            q.setInteger("id", id);
            List<Transaction> list = q.getResultList();
            return list;

        }catch (HibernateException e){
            rollback();
            throw new Exception("Exception while getting transactions"+e.getMessage());
        }
    }
}
