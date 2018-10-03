package com.me.web.webapi_assignment3;

import com.me.web.dao.TransactionDao;
import com.me.web.dao.UserDao;
import com.me.web.pojo.Transaction;
import com.me.web.pojo.User;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
@RestController
public class TransactionController {

    @RequestMapping(value = "/transaction/save", method = RequestMethod.POST)
    public String saveTransaction(HttpServletRequest req, TransactionDao txDao, UserDao userDao) throws  Exception{
        String headers = req.getHeader(HttpHeaders.AUTHORIZATION);
        User user = null;
        if(headers != null) {
            String base64Credentials = headers.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            final String[] values = credentials.split(":", 2);
            user = userDao.verifyUser(values[0], values[1]);
            if (user.getUsername().isEmpty()) {
                return "{message:'Username or password is incorrect'}";
            }else{
                String description = req.getParameter("description");
                String merchant = req.getParameter("merchant");
                String amount = req.getParameter("amount");
                String date = req.getParameter("date");
                String category = req.getParameter("category");
                Transaction tx = new Transaction();
                    tx.setDescription(description);
                    tx.setMerchant(merchant);
                    tx.setAmount(Double.parseDouble(amount));
                    tx.setDate(date);
                    tx.setCategory(category);
                    tx.setUser(user);
                    if(txDao.insertTransaction(tx)==2){
                        return "{message:'Transaction saved successfully'}";
                    }
            }
        }

            return "{message:'Authorization header is required'}";

    }


    @RequestMapping(value="/transaction/delete", method = RequestMethod.DELETE)
    public String deleteTransaction(HttpServletRequest req, TransactionDao txDao, UserDao userDao) throws  Exception{
        String headers = req.getHeader(HttpHeaders.AUTHORIZATION);
        User user = null;
        if(headers != null) {
            String base64Credentials = headers.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            final String[] values = credentials.split(":", 2);
            user = userDao.verifyUser(values[0], values[1]);
            if (user.getUsername().isEmpty()) {
                return "{message:'Username or password is incorrect'}";
            }else{
                int txId = Integer.parseInt(req.getParameter("transactionid"));

                if(txDao.deleteTransaction(txId)==2){
                    return "{message:'Transaction deleted successfully'}";
                }else{
                    return "{message:'Transaction not found'}";
                }
            }
        }

        return "{message:'Authorization header is required'}";
    }

    @RequestMapping(value="/transaction/update", method = RequestMethod.PUT)
    public String updateTransaction(HttpServletRequest req, TransactionDao txDao, UserDao userDao) throws  Exception{
        String headers = req.getHeader(HttpHeaders.AUTHORIZATION);
        User user = null;
        if(headers != null) {
            String base64Credentials = headers.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            final String[] values = credentials.split(":", 2);
            user = userDao.verifyUser(values[0], values[1]);
            if (user.getUsername().isEmpty()) {
                return "{message:'Username or password is incorrect'}";
            }else{
                int txId = Integer.parseInt(req.getParameter("transactionid"));
                Transaction tx = txDao.getTransactionById(txId);
                if(tx==null){
                    return "{message:'Transaction id does not exits'}";
                }
                String description = req.getParameter("description");
                String merchant = req.getParameter("merchant");
                String amount = req.getParameter("amount");
                String date = req.getParameter("date");
                String category = req.getParameter("category");

                tx.setId(txId);
                tx.setDescription(description);
                tx.setMerchant(merchant);
                tx.setAmount(Double.parseDouble(amount));
                tx.setDate(date);
                tx.setCategory(category);
                tx.setUser(user);

                if(txDao.editTransaction(tx)==2){
                    return "{message:'Transaction updated successfully'}";
                }else{
                    return "{message:'Transaction not updated'}";
                }
            }
        }

        return "{message:'Authorization header is required'}";
    }

    @RequestMapping(value="/transaction/getAll", method = RequestMethod.GET)
    public String getAllTransaction(HttpServletRequest req, TransactionDao txDao, UserDao userDao) throws  Exception{
        String headers = req.getHeader(HttpHeaders.AUTHORIZATION);
        User user = null;
        if(headers != null) {
            String base64Credentials = headers.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            final String[] values = credentials.split(":", 2);
            user = userDao.verifyUser(values[0], values[1]);
            if (user.getUsername().isEmpty()) {
                return "{message:'Username or password is incorrect'}";
            }else{
                List<Transaction> list = txDao.getAllTransaction(user.getId());

                if(list.isEmpty()){
                    return "{message:'No transaction found'}";
                }else{
                    return null;
                }
            }
        }
        return "{message:'Authorization header is required'}";
}}
