package com.me.web.webapi_assignment3;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

public class TransactionController {

    @RequestMapping("/transaction/save")
    public String saveTransaction(HttpServletRequest req){
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        return null;
    }
}
