package com.me.web.dao;

import com.me.web.pojo.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class UserDao extends DAO{

    public int createUser(User user) throws Exception {
        try {
            begin();
            getSession().save(user);
            commit();
            return 2;
        }catch(HibernateException e){
            rollback();
            throw new Exception("Exception while creating a user: "+e.getMessage());
        }
    }

    public User verifyUser(String userName, String password) throws Exception {
       try{
           begin();
           Query q = getSession().createQuery("from user_table where username = :username");
           q.setString("username", userName);
           User user = (User)q.uniqueResult();
           if(!user.getUsername().isEmpty()&& BCrypt.checkpw(password, user.getPassword())) {
               return user;
           }
           return null;
       }catch(HibernateException e){
           rollback();
           throw new Exception("Could not get user: "+userName,e);
       }
    }
}
