package com.webapi.webapi_assignment2;

import com.webapi.dao.BasicDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebapiAssignment2ApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void register(){
        BasicDAO ba = new BasicDAO();

        int val = ba.register("suyog","suyog");
        assertEquals(1, val);
    }
}
