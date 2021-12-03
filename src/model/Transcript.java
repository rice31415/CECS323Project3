package model;

import java.util.*;

import jakarta.persistence.*;

@Entity(name = "TRANSCRIPTS")
public class Transcript {

    @Id
    @ManyToOne
    @JoinColumn(name = "SECTION_ID")
    private Section section;

    @Id
    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private Student student;

    @Column(length = 2)
    private String gradeEarned;

    public Transcript() {
        
    }

    public Transcript(String gradeEarned) {
        this.setGradeEarned(gradeEarned);
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getGradeEarned() {
        return gradeEarned;
    }

    public void setGradeEarned(String gradeEarned) {
        this.gradeEarned = gradeEarned;
    }
}
