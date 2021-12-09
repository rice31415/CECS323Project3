package model;

import java.util.*;

import jakarta.persistence.*;

@Entity(name = "TRANSCRIPTS")
public class Transcript implements Comparable<Transcript>{

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

    public Transcript(String gradeEarned, Section section, Student student) {
        this.setGradeEarned(gradeEarned);
        this.setSection(section);
        this.setStudent(student);
        student.getTranscripts().add(this);
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

    public String toString() {
        Course c = section.getCourse();
        return c.getDepartment().getAbbreviation() + " " + c.getNumber() + ", " + section.getSemester().getTitle() + ". Grade Earned: " + gradeEarned;
    }

    public int compareTo(Transcript t) {
        int a = section.getSemester().getStartDate().compareTo(t.getSection().getSemester().getStartDate());
        int b = section.getCourse().getDepartment().getAbbreviation().compareTo(t.getSection().getCourse().getDepartment().getAbbreviation());
        int c = section.getCourse().getNumber().compareTo(t.getSection().getCourse().getNumber());
        if (a != 0) {
            return a;
        }
        else if (b != 0) {
            return b;
        }
        else {
            return c;
        }
    }
}
