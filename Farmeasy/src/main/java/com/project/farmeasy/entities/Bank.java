package com.project.farmeasy.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bank")
@Data
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String bankName;
    private String bankAddress;
    private String bankCity;
    private String bankState;
    private String bankZip;
    private String bankEmail;
    private String bankPhone;


}
