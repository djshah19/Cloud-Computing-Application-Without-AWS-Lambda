package com.me.web.webapi_assignment3;

import com.me.web.dao.TransactionDao;
import com.me.web.dao.UserDao;
import com.me.web.pojo.Transaction;
import com.me.web.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebapiAssignment3ApplicationTests {

    @Test
    public void contextLoads() {
    }

    /*  Transaction Save test*/
    @Test
    public void saveTransactionTest(){
        try {
            TransactionDao transactionDAO = new TransactionDao();
            Transaction tx = new Transaction();
            tx.setAmount(23.4);
            tx.setCategory("Apparels");
            tx.setDate("05/04/2017");
            tx.setMerchant("Macy's");
            UserDao uDao = new UserDao();
            User ur = uDao.verifyUser("root","root");
            tx.setUser(ur);
            int val = transactionDAO.insertTransaction(tx,null);
            assertEquals(2, val);
        }
        catch(Exception ex){
            System.out.println("Exception Message:"+ex.getMessage());
        }
    }
}
