package model;

import java.util.*;

import jakarta.persistence.*;

@Entity(name = "STUDENTS")
public class Student {
    @Id
    @Column(name = "STUDENT_ID", unique = true)
    private int studentId;

    @Column(length = 128)
    private String name;

    @ManyToMany(mappedBy = "enrollments")
    private Set<Section> sections;

    @OneToMany(mappedBy = "student")
    private Set<Transcript> transcripts;

    public enum RegistrationResult { SUCCESS, ALREADY_PASSED, ENROLLED_IN_SECTION, NO_PREREQUISITES, ENROLLED_IN_ANOTHER, TIME_CONFLICT };

    public Student() {

    }

    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.setName(name);
        transcripts = new TreeSet<Transcript>();
        sections = new HashSet<Section>();
    }

    public int getStudentId() {
        return studentId;
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

    public void addSection(Section s) {
        this.sections.add(s);
        s.getEnrollments().add(this);
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
            int u = t.getSection().getCourse().getUnits();
            if (grade.equals("A")) {
                gpa += 4*u;
                units += u;
            }
            else if (grade.equals("B")) {
                gpa += 3*u;
                units += u;
            }
            else if (grade.equals("C")) {
                gpa += 2*u;
                units += u;
            }
            else if (grade.equals("D")) {
                gpa += 1*u;
                units += u;
            }
            else if (grade.equals("F")) {
                gpa += 0*u;
                units += u;
            }
        }
        if (units == 0) {
            return 0;
        }
        gpa /= units;
        return gpa;
    }

    public RegistrationResult registerForSection(Section s) {
        int prereq = 0;
        Course c = s.getCourse();
        for (Transcript t: transcripts) {
            if (t.getSection().getCourse() == c) {
                if (t.getGradeEarned().compareTo("C") <= 0) {
                    return RegistrationResult.ALREADY_PASSED;
                }
            }

            for (Prerequisite p: c.getPrerequisites()) {
                if (t.getSection().getCourse() == p.getPrereq()) {
                    if (t.getGradeEarned().compareTo(Character.toString(p.getMinimumGrade())) <= 0) {
                        prereq++;
                    }
                }
            }
        }

        if (prereq < c.getPrerequisites().size()) {
            return RegistrationResult.NO_PREREQUISITES;
        }

        for (Section sec: sections) {
            if (sec == s) {
                return RegistrationResult.ENROLLED_IN_SECTION;
            }
            else if (sec.getCourse() == s.getCourse() && s.getSemester() == s.getSemester()) {
                return RegistrationResult.ENROLLED_IN_ANOTHER;
            }
            else if (s.getTimeSlot().checkTimeConflict(sec.getTimeSlot())) {
                return RegistrationResult.TIME_CONFLICT;
            }
        }

        return RegistrationResult.SUCCESS;
    }
}
