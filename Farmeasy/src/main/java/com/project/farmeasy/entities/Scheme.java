package com.project.farmeasy.entities;

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
    private String schemeDescription;
    private String interestRate;
    private String schemePdf;

}
