package com.me.web.dao;

import com.me.web.pojo.Attachment;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.ArrayList;
import java.util.List;

public class AttachmentDao extends DAO{

    private String url = "/Downloads/";

    public int saveAttachment(Attachment attachment){
        try{
            begin();
            getSession().save(attachment);
            commit();
            if(storeAttachment(attachment) == 2)
                return 2;
            else{
                return 1;
            }
        }
        catch(Exception ex){
            System.out.println("Exception Message:"+ex.getMessage());
        }
        return 1;
    }

    public int storeAttachment(Attachment file){
        try {
            MultipartFile fileInMemory = file.getFile();
            java.io.File destFile = new java.io.File(url, fileInMemory.getOriginalFilename());
            fileInMemory.transferTo(destFile);
            return 2;
        }
        catch(Exception ex){
            System.out.println("Exception Message:" + ex.getMessage());
        }
        return 0;
    }

    /*
    public ArrayList<Attachment> getAttachmentByTransaction(int id) throws Exception{
        List<Attachment> attachmentList = new ArrayList<>();
        try{
            begin();
            Query q = getSession().createQuery("from Attachment where transaction_id = :id");
            q.setInteger("id", id);
            attachmentList = q.getResultList();
            commit();
        }catch (HibernateException e){
            rollback();
            throw new Exception("Exception while getting transactions"+e.getMessage());
        }
        return (ArrayList<Attachment>) attachmentList;
    }
    */
}
