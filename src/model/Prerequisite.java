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

    public Prerequisite(Course prereq, Course course, char minimumGrade) {
        this.prereq = prereq;
        this.course = course;
        this.minimumGrade = minimumGrade;
        course.getPrerequisites().add(this);
    }

    public Prerequisite() {
        
    }

    public Course getPrereq() {
        return prereq;
    }

    public Course getCourse() {
        return course;
    }

    public char getMinimumGrade() {
        return minimumGrade;
    }

    public void setMinimumGrade(char minimumGrade) {
        this.minimumGrade = minimumGrade;
    }
}
