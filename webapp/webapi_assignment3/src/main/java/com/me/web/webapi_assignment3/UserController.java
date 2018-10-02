package com.me.web.webapi_assignment3;

import com.me.web.dao.UserDao;
import com.me.web.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @RequestMapping(value = "user/save", method = RequestMethod.POST)
    public String saveTransaction(HttpServletRequest req){
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userDao.insertUser(user);
        return null;
    }
}
