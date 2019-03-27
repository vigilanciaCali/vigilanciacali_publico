package co.edu.usbcali.vas.model;
// Generated Jun 10, 2018 8:14:55 PM by Hibernate Tools 5.1.0.Final


import java.util.Date;

/**
 * Ticket generated by hbm2java
 */
public class Ticket  implements java.io.Serializable {


     private Long id;
     private TicketType ticketType;
     private Users users;
     private String status;
     private String subject;
     private String message;
     private String note;
     private Date createdAt;
     private Date updatedAt;
     private String createdBy;
     private String updatedBy;

    public Ticket() {
    }

	
    public Ticket(Long id, TicketType ticketType, Users users, String status, Date createdAt, String createdBy) {
        this.id = id;
        this.ticketType = ticketType;
        this.users = users;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
    public Ticket(Long id, TicketType ticketType, Users users, String status, String subject, String message, String note, Date createdAt, Date updatedAt, String createdBy, String updatedBy) {
       this.id = id;
       this.ticketType = ticketType;
       this.users = users;
       this.status = status;
       this.subject = subject;
       this.message = message;
       this.note = note;
       this.createdAt = createdAt;
       this.updatedAt = updatedAt;
       this.createdBy = createdBy;
       this.updatedBy = updatedBy;
    }
   
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    public TicketType getTicketType() {
        return this.ticketType;
    }
    
    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    public String getSubject() {
        return this.subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    public String getNote() {
        return this.note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    public Date getCreatedAt() {
        return this.createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Date getUpdatedAt() {
        return this.updatedAt;
    }
    
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getUpdatedBy() {
        return this.updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }




}

