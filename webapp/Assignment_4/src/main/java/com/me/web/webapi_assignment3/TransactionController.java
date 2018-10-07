package com.me.web.webapi_assignment3;

import com.me.web.dao.AttachmentDao;
import com.me.web.dao.TransactionDao;
import com.me.web.dao.UserDao;
import com.me.web.pojo.Attachment;
import com.me.web.pojo.Transaction;
import com.me.web.pojo.User;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;

@RestController
public class TransactionController {

    @RequestMapping(value = "transaction", method = RequestMethod.POST)
    public HashMap<String, Object> saveTransaction(HttpServletRequest req, TransactionDao txDao, UserDao userDao) throws  Exception{
        String headers = req.getHeader(HttpHeaders.AUTHORIZATION);
        User user = null;
        HashMap<String, Object> map = new HashMap<>();
        if(headers != null) {
            String base64Credentials = headers.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            final String[] values = credentials.split(":", 2);
            user = userDao.verifyUser(values[0], values[1]);
            if (user == null || user.getUsername().isEmpty()) {
                map.put("Code",401);
                map.put("Description","Unauthorized");
                return map;
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
                    if(txDao.insertTransaction(tx,null)==2){
                        map.put("Description",tx);
                        map.put("Code",200);
                        return map;
                    }
            }
        }
        map.put("Code",401);map.put("Description","Unauthorized");
        return map;

    }


    @RequestMapping(value = "transaction/{id}/attachments", method = RequestMethod.POST)
    public HashMap<String, Object> saveAttachments(@PathVariable("id") int id, HttpServletRequest req, TransactionDao txDao, AttachmentDao attachmentDao, UserDao userDao, @RequestPart("file") MultipartFile file) throws  Exception{
        String headers = req.getHeader(HttpHeaders.AUTHORIZATION);
        User user = null;
        HashMap<String, Object> map = new HashMap<>();
        if(headers != null) {
            String base64Credentials = headers.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            final String[] values = credentials.split(":", 2);
            user = userDao.verifyUser(values[0], values[1]);
            if (user == null || user.getUsername().isEmpty()) {
                map.put("Code",401);
                map.put("Description","Unauthorized");
                return map;
            }else{
                int txId = id;
                if(txDao.authorizeUser(txId,user) == 2) {
                    Transaction tx = txDao.getTransactionById(txId);
                    Attachment attachment = new Attachment();
                    attachment.setFilePath(file.getOriginalFilename());
                    attachment.setFile(file);
                    attachment.setTransaction(tx);
                    if (attachmentDao.saveAttachment(attachment) == 2) {
                        map.put("Description", attachment);
                        map.put("Code", 200);
                        return map;
                    }
                    else{
                        map.put("Description", "Attachment unsuccessful");
                        map.put("Code", 500);
                        return map;
                    }
                }
                else{
                    map.put("Code", 401);
                    map.put("Description", "Unauthorized");
                    return map;
                }
            }
        }
        map.put("Code",401);map.put("Description","Unauthorized");
        return map;

    }


    @RequestMapping(value="transaction/{id}", method = RequestMethod.DELETE)
    public HashMap<String, Object> deleteTransaction(@PathVariable("id") int id, HttpServletRequest req, TransactionDao txDao, UserDao userDao) throws  Exception{
        String headers = req.getHeader(HttpHeaders.AUTHORIZATION);
        User user = null;
        HashMap<String, Object> map = new HashMap<>();
        if(headers != null) {
            String base64Credentials = headers.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            final String[] values = credentials.split(":", 2);
            user = userDao.verifyUser(values[0], values[1]);
            if (user == null || user.getUsername().isEmpty()) {
                map.put("Code",401);
                map.put("Description","Unauthorized");
                return map;
            }else{
                int txId = id;
                if(txId != 0){
                    if(txDao.authorizeUser(txId,user) == 2) {
                        if (txDao.deleteTransaction(txId) == 2) {
                            map.put("Code", 200);
                            map.put("Description", "Successfully Deleted");
                            return map;
                        } else {
                            map.put("Code", 400);
                            map.put("Description", "Bad Request");
                            return map;
                        }
                    }
                    else{
                        map.put("Code", 401);
                        map.put("Description", "Unauthorized");
                        return map;
                    }
                }else{
                    map.put("Code",204);
                    map.put("Description","No Content");
                    return map;
                }
            }
        }
        map.put("Code",401);
        map.put("Description","Unauthorized");
        return map;
    }

    @RequestMapping(value="transaction/{id}", method = RequestMethod.PUT)
    public HashMap<String, Object> updateTransaction(@PathVariable("id") int id, HttpServletRequest req, TransactionDao txDao, UserDao userDao) throws  Exception{
        String headers = req.getHeader(HttpHeaders.AUTHORIZATION);
        User user = null;
        HashMap<String, Object> map = new HashMap<>();
        if(headers != null) {
            String base64Credentials = headers.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            final String[] values = credentials.split(":", 2);
            user = userDao.verifyUser(values[0], values[1]);
            if (user == null || user.getUsername().isEmpty()) {
                map.put("Code",401);
                map.put("Description","Unauthorized");
                return map;
            }else{
                int txId = id;
                if(txDao.authorizeUser(txId,user) == 2) {
                    Transaction tx = txDao.getTransactionById(txId);
                    if (tx == null) {
                        map.put("Code", 400);
                        map.put("Description", "Bad Request");
                        return map;
                    }
                    String description = req.getParameter("description") == null ? tx.getDescription() : req.getParameter("description");
                    String merchant = req.getParameter("merchant") == null ? tx.getMerchant() : req.getParameter("merchant");
                    String amount = req.getParameter("amount") == null ? String.valueOf(tx.getAmount()) : req.getParameter("amount");
                    String date = req.getParameter("date") == null ? tx.getDate() : req.getParameter("date");
                    String category = req.getParameter("category") == null ? tx.getCategory() : req.getParameter("category");

                    tx.setId(txId);
                    tx.setDescription(description);
                    tx.setMerchant(merchant);
                    tx.setAmount(Double.parseDouble(amount));
                    tx.setDate(date);
                    tx.setCategory(category);
                    tx.setUser(user);

                    if (txDao.editTransaction(tx) == 2) {
                        map.put("Code", 201);
                        map.put("Description", "Created");
                        map.put("Transaction", tx);
                        return map;
                    } else {
                        map.put("Code", 400);
                        map.put("Description", "Bad Request");
                        return map;
                    }
                }
                else{
                    map.put("Code", 401);
                    map.put("Description", "Unauthorized");
                    return map;
                }
            }
        }
        map.put("Code",401);
        map.put("Description","Unauthorized");
        return map;
    }

    @RequestMapping(value="transaction", method = RequestMethod.GET)
    public HashMap<String, Object> getAllTransaction(HttpServletRequest req, TransactionDao txDao, UserDao userDao) throws  Exception{
        String headers = req.getHeader(HttpHeaders.AUTHORIZATION);
        HashMap<String, Object> map = new HashMap<>();
        User user = null;
        if(headers != null) {
            String base64Credentials = headers.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            final String[] values = credentials.split(":", 2);
            user = userDao.verifyUser(values[0], values[1]);
            if (user == null || user.getUsername().isEmpty()) {
                map.put("Code",401);
                map.put("Description", "Unauthorized");
                return map;
            }else{
                List<Transaction> list = txDao.getAllTransaction(user.getId());
                if(list.isEmpty()){
                    map.put("Code",200);
                    map.put("Description", "No transaction found");
                    //return "{message:'No transaction found'}";
                    return map;
                }else{
                    map.put("Code",200);
                    map.put("Description","OK");
                    map.put("Transaction", list);
                    return map;
                }
            }
        }
        map.put("Code",401);
        map.put("Description", "Unauthorized");
        return map;
}}
