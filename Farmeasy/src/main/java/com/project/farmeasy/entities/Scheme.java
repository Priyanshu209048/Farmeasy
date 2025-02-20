package com.project.farmeasy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "scheme")
@Data
public class Scheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String schemeName;
    private String schemeCode;
    private String schemeDescription;
    private String benefits;
    private String eligibility;
    private String documents;
    private String roi;
    private String tenure;
    private String schemeType;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

}
