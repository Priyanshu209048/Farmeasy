package com.project.farmeasy.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Table(name = "FARMER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Farmer {

    @Id
    @Column(name = "USER_ID")
    private String id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "CONTACT")
    private String contact;

    @Column(name = "EMAIL", unique = true)
    @Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

}
