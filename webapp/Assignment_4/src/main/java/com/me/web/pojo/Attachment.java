package com.me.web.pojo;

import org.hibernate.annotations.CollectionId;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", unique = true, nullable = false)
    private int id;



    @ManyToOne()
    private Transaction transaction;

//    @Column(name="file_path")
//    private String filePath;
//
//    @Transient
//    private MultipartFile file;

    public Attachment(){}

    public int getId(){ return id;}


    public Transaction getTransaction(){ return transaction;}

//    public String getFilePath(){return filePath;}
//
//    public MultipartFile getFile() {
//        return file;
//    }

    public void setId(int id){ this.id = id;}


    public void setTransaction(Transaction transaction){ this.transaction = transaction;}

//    public void setFilePath(String filePath){this.filePath = filePath;}
//
//    public void setFile(MultipartFile file) {
//        this.file = file;
//    }

}
