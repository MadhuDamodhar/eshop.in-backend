package com.ecom.Model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "ChatBotHistory")
@Data
public class ChatBotModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Lob
    @Column(columnDefinition = "TEXT") 
    private String message;
    @Lob
    @Column(columnDefinition = "TEXT") 
    private String response;

    @Column
    private Boolean issueResolved = false;  

    @Column
    private String userName;
    
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId") 
    @JsonIgnoreProperties("roles") 
    @JsonIgnore
    private User user;
}
