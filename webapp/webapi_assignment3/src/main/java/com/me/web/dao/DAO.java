package com.me.web.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DAO {

    Configuration cfg = new Configuration();
    SessionFactory sf = cfg.configure().buildSessionFactory();
    Session session = sf.openSession();


}
