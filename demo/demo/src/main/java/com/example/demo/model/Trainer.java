package com.example.demo.model;

import jakarta.persistence.*;


@Entity
@Table(name = "trainer_profiles")

public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String specialization;

    @Column(columnDefinition = "TEXT")
    private String bio;

    public Trainer(Long id, String username, String email, String firstname, String lastname, String specialization, String bio) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.specialization = specialization;
        this.bio = bio;
    }

    public Trainer() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getBio() {
        return bio;
    }
}