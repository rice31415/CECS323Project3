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
    @JoinColumn(name = "PRECEDE_ID")
    private Course precede;

    private char minimumGrade;

    public Prerequisite(Course prereq, Course precede, char minimumGrade) {
        this.prereq = prereq;
        this.precede = precede;
        this.minimumGrade = minimumGrade;
        precede.getPrerequisites().add(this);
    }

    public Prerequisite() {
        
    }

    public Course getPrereq() {
        return prereq;
    }

    public Course getPrecede() {
        return precede;
    }

    public char getMinimumGrade() {
        return minimumGrade;
    }

    public void setMinimumGrade(char minimumGrade) {
        this.minimumGrade = minimumGrade;
    }
}
