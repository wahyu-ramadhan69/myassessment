package com.recruitment.myassessment.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Assessment")
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_assessment")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "password")
    private String password;

    @Column(name = "closeDate")
    private Date closeDate;

    @ManyToMany
    @JoinTable(name = "user_assessment", joinColumns = @JoinColumn(name = "assessment_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Usr> participants;

    @OneToMany(mappedBy = "assessment")
    private List<Result> results;

    @OneToMany(mappedBy = "assessment")
    private List<Question> questions;

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public List<Usr> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Usr> participants) {
        this.participants = participants;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}
