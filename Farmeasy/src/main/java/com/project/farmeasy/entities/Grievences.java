package com.project.farmeasy.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "grievences")
@Data
public class Grievences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    private Farmer farmer;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    private String grievencesType;
    private String grievencesDescription;
    private String grievencesStatus;
    private String grievencesReview;

}
