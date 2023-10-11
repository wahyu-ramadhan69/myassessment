package com.recruitment.myassessment.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_question")
    private Long id;

    @Column(name = "text")
    private String text;
    
    @Column(name = "image")
    private String image;

    @Column(name = "type")
    private String type;
    
    @OneToMany(mappedBy = "question")
    private List<Choice> choices;

    @ManyToOne
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
    
    
}
