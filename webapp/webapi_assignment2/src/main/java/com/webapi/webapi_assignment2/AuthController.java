package com.webapi.webapi_assignment2;

//import com.webapi.dao.BasicDAO;
//import org.springframework.beans.factory.annotation.Autowired;
import com.webapi.dao.BasicDAO;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCrypt;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Base64;

@RestController
public class AuthController {

    //@Autowired
    //BasicDAO basicDAO;

    @RequestMapping(value="user/register",method = RequestMethod.POST)
    public String register(HttpServletRequest req){
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        BasicDAO basicDAO = new BasicDAO();
        String salt = BCrypt.gensalt();
        password = BCrypt.hashpw(password, salt);
        if(basicDAO.register(username,password) == 2){
            return "{message:'User successfully registered'}";
        }
        return "{message:'User already exist'}";
    }

    @RequestMapping(value="/time",method = RequestMethod.GET)
    public String time(HttpServletRequest req){
        String headers = req.getHeader(HttpHeaders.AUTHORIZATION);
        BasicDAO basicDAO = new BasicDAO();
        String base64Credentials = headers.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);

        if(basicDAO.verifyUser(values[0],values[1])){
            return "{timestamp:'"+new Timestamp(System.currentTimeMillis()).toString()+"'}";
        }
        else{
            return "{message:'Username or password is incorrect'}";
        }
    }
}
