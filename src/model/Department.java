package model;

import java.util.*;

import jakarta.persistence.*;

@Entity(name = "DEPARTMENTS")
public class Department {
    @Id
    @Column(name = "DEPARTMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentID;

    @Column(length = 128)
    private String name;
    
    @Column(length = 8)
    private String abbreviation;

    @OneToMany(mappedBy = "department")
    @JoinColumn(name = "DEPARTMENT_ID")
    private List<Course> courses;

    public Department() {

    }

    public Department(int departmentID, String name, String abbreviation) {
        this.setDepartmentID(departmentID);
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
        course.setDepartment(this);
    }
}
