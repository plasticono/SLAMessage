package org.sla.secondapplication.model;

import java.util.Date;

public class Message {

    private String text;
    private Date date;
    String sender;

    public Message(String text, Date date){
        this.text = text;
        this.date = date;
        this.sender = "local";
    }

    public Message(String text, Date date, String sender){
        this.text = text;
        this.date = date;
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
