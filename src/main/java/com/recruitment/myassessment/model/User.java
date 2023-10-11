package com.recruitment.myassessment.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_user")
    private Long id;

    @Column(name = "nama")
    private String nama;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "isAdmin", nullable = true, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isAdmin;

    @Column(name = "isUser", nullable = true, columnDefinition = "BOOLEAN DEFAULT true")
    private boolean isUser;

    @Column(name = "token", nullable = true)
    private String token;

    @Column(name = "isActive", columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive;

    @OneToMany(mappedBy = "user")
    private List<Result> results;

    @ManyToMany
    @JoinTable(name = "user_assessment", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "assessment_id"))
    private List<Assessment> assessments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean isUser) {
        this.isUser = isUser;
    }

    // getters and setters

}
