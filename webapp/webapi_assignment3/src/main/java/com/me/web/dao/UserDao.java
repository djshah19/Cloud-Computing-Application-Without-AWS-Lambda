package com.me.web.dao;

import com.me.web.pojo.User;

public class UserDao extends DAO{

    public User insertUser(User user){
        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        session.close();
        return null;
    }
}
