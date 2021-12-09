package model;

import java.util.*;

import jakarta.persistence.*;

@Entity(name = "SECTIONS")
@Table(uniqueConstraints={
    @UniqueConstraint(columnNames = {"SEMESTER_ID", "COURSE_ID", "sectionNumber"})
}) 
public class Section {
    @Id
    @Column(name = "SECTION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sectionId;
    
    //Section number and capacity can go above 127 but never go into 4 digits from what I've seen
    private short sectionNumber;
    private short maxCapacity; 

    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "TIME_SLOT_ID")
    private TimeSlot timeSlot;

    @ManyToOne
    @JoinColumn(name = "SEMESTER_ID")
    private Semester semester;

    @JoinTable(
        name = "enrollments", 
        joinColumns = @JoinColumn(name = "SECTION_ID"), 
        inverseJoinColumns = @JoinColumn(name = "STUDENT_ID")
    )
    @ManyToMany
    private Set<Student> enrollments;

    public Section() {
        
    }

    public Section(Course course, short sectionNumber, Semester semester, TimeSlot timeSlot, short maxCapacity) {
        this.course = course;
        this.semester = semester;
        this.timeSlot = timeSlot;
        this.maxCapacity = maxCapacity;
        this.sectionNumber = sectionNumber;
        enrollments = new HashSet<Student>();
    }

    public int getSectionId() {
        return sectionId;
    }

    public short getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(short maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public short getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(short sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public void addSemester(Semester semester) {
        setSemester(semester);
        semester.getSections().add(this);
    }

    public Set<Student> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<Student> enrollments) {
        this.enrollments = enrollments;
    }

    public void addStudent(Student student) {
        enrollments.add(student);
        student.getSections().add(this);
    }
}
