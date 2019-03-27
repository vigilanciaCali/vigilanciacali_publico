package co.edu.usbcali.vas.model;
// Generated Jun 10, 2018 8:14:55 PM by Hibernate Tools 5.1.0.Final


import java.util.Date;

/**
 * SystemVideoLog generated by hbm2java
 */
public class SystemVideoLog  implements java.io.Serializable {


     private Long id;
     private Integer userId;
     private String userName;
     private Date actionDate;
     private String actionName;
     private String note;
     private String ip;

    public SystemVideoLog() {
    }

	
    public SystemVideoLog(Long id, Integer userId, Date actionDate, String actionName) {
        this.id = id;
        this.userId = userId;
        this.actionDate = actionDate;
        this.actionName = actionName;
    }
    public SystemVideoLog(Long id, Integer userId, String userName, Date actionDate, String actionName, String note, String ip) {
       this.id = id;
       this.userId = userId;
       this.userName = userName;
       this.actionDate = actionDate;
       this.actionName = actionName;
       this.note = note;
       this.ip = ip;
    }
   
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Date getActionDate() {
        return this.actionDate;
    }
    
    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }
    public String getActionName() {
        return this.actionName;
    }
    
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
    public String getNote() {
        return this.note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    public String getIp() {
        return this.ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }




}

