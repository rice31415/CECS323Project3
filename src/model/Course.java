package model;

import java.util.*;

import jakarta.persistence.*;

@Entity(name = "COURSES")
@Table(uniqueConstraints={
    @UniqueConstraint(columnNames = {"DEPARTMENT_ID", "number"})
}) 
public class Course {
    @Id
    @Column(name = "COURSE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseID;

    @Column(length = 8)
    private String number;
    
    @Column(length = 64)
    private String title;
    
    private byte units; //unit shouldn't surpass 127

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

    @OneToMany(mappedBy = "course")
    private Set<Prerequisite> prerequisites;

    public Course() {

    }

    public Course(int courseID, String number, String title, byte units) {
        this.courseID = courseID;
        this.setNumber(number);
        this.setTitle(title);
        this.setUnits(units);
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte getUnits() {
        return units;
    }

    public void setUnits(byte units) {
        this.units = units;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void addDepartment(Department department) {
        setDepartment(department);
        department.getCourses().add(this);
    }

    public Set<Prerequisite> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(Set<Prerequisite> prerequisites) {
        this.prerequisites = prerequisites;
    }
}
