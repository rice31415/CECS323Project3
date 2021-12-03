package model;

import java.util.*;

import jakarta.persistence.*;

@Entity(name = "STUDENTS")
public class Student {
    @Id
    @Column(name = "STUDENT_ID", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY) //ask about this

    private int studentId;

    @Column(length = 128)
    private String name;

    @ManyToMany(mappedBy = "students")
    private Set<Section> sections;

    @OneToMany(mappedBy = "student")
    private Set<Transcript> transcripts;

    public Student() {

    }

    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.setName(name);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    public void addSection(Section section) {
        sections.add(section);
        section.getStudents().add(this);
    }

    public Set<Transcript> getTranscripts() {
        return transcripts;
    }

    public void setTranscripts(Set<Transcript> transcripts) {
        this.transcripts = transcripts;
    }

    public double getGpa() {
        double gpa = 0;
        int units = 0;
        for (Transcript t: transcripts) {
            String grade = t.getGradeEarned();
            if (grade.equals("A")) {
                gpa += 4;
            }
            else if (grade.equals("B")) {
                gpa += 3;
            }
            else if (grade.equals("C")) {
                gpa += 2;
            }
            else if (grade.equals("D")) {
                gpa += 1;
            }
            units += t.getSection().getCourse().getUnits();
        }
        gpa /= units;
        return gpa;
    }
}
