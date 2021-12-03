package model;

import java.util.*;

import jakarta.persistence.*;

@Entity(name = "PREREQUISITES")
public class Prerequisite {

    @Id
    @ManyToOne
    @JoinColumn(name = "PREREQ_ID")
    private Course prereq;

    @Id
    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;

    private char minimumGrade;

    public Prerequisite(char minimumGrade) {
        this.minimumGrade = minimumGrade;
    }

    public Prerequisite() {
        
    }

    public char getMinimumGrade() {
        return minimumGrade;
    }

    public void setMinimumGrade(char minimumGrade) {
        this.minimumGrade = minimumGrade;
    }
}
