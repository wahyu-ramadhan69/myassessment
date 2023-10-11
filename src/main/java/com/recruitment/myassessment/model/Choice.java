package com.recruitment.myassessment.model;

import javax.persistence.*;

@Entity
@Table(name = "Choice")
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_choice")
    private Long id;

    @Column(name = "value")
    private String value;
    
    @Column(name = "isTrue")
    private boolean isTrue;
    
    
    // getters and setters

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public boolean isTrue() {
        return isTrue;
    }
    public void setTrue(boolean isTrue) {
        this.isTrue = isTrue;
    }
    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
    

    
}

