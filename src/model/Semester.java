package model;

import java.util.*;
import java.time.*;

import jakarta.persistence.*;

@Entity(name = "SEMESTERS")
public class Semester {
    @Id
    @Column(name = "SEMESTER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int semesterId;

    @Column(length = 16)
    private String title;

    private LocalDate startDate;

    @OneToMany(mappedBy = "semester")
    @JoinColumn(name = "SEMESTER_ID")
    private List<Section> sections;

    public Semester() {
        
    }

    public Semester(String title, LocalDate startDate) {
        this.setTitle(title);
        this.setStartDate(startDate);
    }

    public int getSemesterId() {
        return semesterId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public void addSection(Section s) {
        sections.add(s);
        s.setSemester(this);
    }
}
