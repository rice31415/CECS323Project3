import java.time.*;
import java.util.*;

import jakarta.persistence.*;
import model.*;
import model.Student.RegistrationResult;
public class App {
    private static void instantiateModel(EntityManager em) {
        em.getTransaction().begin();
            
        Semester spring21 = new Semester("Spring 2021", LocalDate.of(2021,1,19));
        Semester fall21 = new Semester("Fall 2021", LocalDate.of(2021,8,17));
        Semester spring22 = new Semester("Spring 2022", LocalDate.of(2022,1,20));

        Department cecs = new Department("Computer Engineering and Computer Science", "CECS");
        Department ital = new Department("Italian Studies", "ITAL");

        Course cecs174 = new Course(cecs, "174", "Introduction to Programming and Problem Solving", (byte)3);
        Course cecs274 = new Course(cecs, "274", "Data Structures", (byte)3);
        Course cecs277 = new Course(cecs, "277", "Object Oriented Application Development", (byte)3);
        Course cecs282 = new Course(cecs, "282", "Advanced C++", (byte)3);
        Course ital101a = new Course(ital, "101A", "Fundamentals of Italian", (byte)4);
        Course ital101b = new Course(ital, "101B", "Fundamentals of Italian", (byte)4);

        em.persist(spring21);
        em.persist(fall21);
        em.persist(spring22);

        em.persist(cecs);
        em.persist(ital);

        em.persist(cecs174);
        em.persist(cecs274);
        em.persist(cecs277);
        em.persist(cecs282);
        em.persist(ital101a);
        em.persist(ital101b);
        em.getTransaction().commit();
        em.getTransaction().begin();
        
        Prerequisite cecs274p1 = new Prerequisite(cecs174, cecs274, 'C');
        Prerequisite cecs277p1 = new Prerequisite(cecs174, cecs277, 'C');
        Prerequisite cecs282p1 = new Prerequisite(cecs274, cecs282, 'C');
        Prerequisite cecs282p2 = new Prerequisite(cecs277, cecs282, 'C');
        Prerequisite ital101bp1 = new Prerequisite(ital101a, ital101b, 'D');

        TimeSlot slot1 = new TimeSlot((byte)40, LocalTime.of(12, 30), LocalTime.of(13, 45));
        TimeSlot slot2 = new TimeSlot((byte)20, LocalTime.of(14, 00), LocalTime.of(15, 15));
        TimeSlot slot3 = new TimeSlot((byte)42, LocalTime.of(12, 00), LocalTime.of(12, 50));
        TimeSlot slot4 = new TimeSlot((byte)2, LocalTime.of(8, 00), LocalTime.of(10, 45));

        Section a = new Section(cecs174, (short)1, spring21, slot1, (short)105);
        Section b = new Section(cecs274, (short)1, fall21, slot2, (short)140);
        Section c = new Section(cecs277, (short)3, fall21, slot4, (short)35);
        Section d = new Section(cecs282, (short)5, spring22, slot2, (short)35);
        Section e = new Section(cecs277, (short)1, spring22, slot1, (short)35);
        Section f = new Section(cecs282, (short)7, spring22, slot1, (short)35);
        Section g = new Section(ital101a, (short)1, spring22, slot3, (short)25);

        Student student1 = new Student(123456789, "Naomi Nagata");
        Student student2 = new Student(987654321, "James Holden");
        Student student3 = new Student(555555555, "Amos Burton");

        d.addStudent(student1);

        em.persist(cecs274p1);
        em.persist(cecs277p1);
        em.persist(cecs282p1);  
        em.persist(cecs282p2);  
        em.persist(ital101bp1);

        em.persist(slot1);
        em.persist(slot2);
        em.persist(slot3);
        em.persist(slot4);

        em.persist(a);
        em.persist(b);
        em.persist(c);
        em.persist(d);
        em.persist(e);
        em.persist(f);
        em.persist(g);

        em.persist(student1);
        em.persist(student2);
        em.persist(student3);
        em.getTransaction().commit();
        em.getTransaction().begin();

        Transcript aa1 = new Transcript("A", a, student1);
        Transcript ba1 = new Transcript("A", b, student1);
        Transcript ca1 = new Transcript("A", c, student1);
        Transcript ac2 = new Transcript("C", a, student2);
        Transcript bc2 = new Transcript("C", b, student2);
        Transcript cc2 = new Transcript("C", c, student2);
        Transcript ac3 = new Transcript("C", a, student3);
        Transcript bb3 = new Transcript("B", b, student3);
        Transcript cd3 = new Transcript("D", c, student3);

        em.persist(aa1);
        em.persist(ba1);
        em.persist(ca1);
        em.persist(ac2);
        em.persist(bc2);
        em.persist(cc2);
        em.persist(ac3);
        em.persist(bb3);
        em.persist(cd3);
        
        // Committing the transaction will push/"flush" the changes to the database.
        em.getTransaction().commit();
    }

    private static void studentLookup(EntityManager em) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a student name: ");
        String name = scan.nextLine();

        // A TypedQuery is strongly typed; a normal Query would not be.
        var namedStudent = em.createQuery("SELECT s FROM STUDENTS s WHERE "
            + "s.name = ?1", Student.class);
        namedStudent.setParameter(1, name);
        try {
            Student s = namedStudent.getSingleResult();
            for (Transcript t: s.getTranscripts()) {
                System.out.println(t.toString());
            }
            System.out.println("GPA: " + s.getGpa());
        }
        catch (NoResultException ex) {
            System.out.println("Student with name '" + name + "' not found.");
        }
    }

    private static void registration(EntityManager em) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a semester or choose from the menu options: ");
        
        var semesters = em.createQuery("select s from SEMESTERS s", Semester.class).getResultList();

        Integer i = 1;
        for (Semester s : semesters) {
            System.out.println(i + ": " + s.getTitle());
            i++;
        }

        Semester sem = new Semester();
        while (sem.getTitle() == null) {
            String input = scan.nextLine();
            i = 1;
            for (Semester s : semesters) {
                if (i.toString().equals(input)) {
                    sem = s;
                }
                else if (s.getTitle().equals(input)) {
                    sem = s;
                }
                i++;
            }
            if (sem == null) {
                System.out.println("Invalid input, please try again: ");
            }
        }
        
        System.out.println("Please enter a student name: ");
        String name = scan.nextLine();
        var namedStudent = em.createQuery("SELECT s FROM STUDENTS s WHERE "
            + "s.name = ?1", Student.class);
        namedStudent.setParameter(1, name);
        Student student = new Student();
        try {
            student = namedStudent.getSingleResult();
        }
        catch (NoResultException ex) {
            System.out.println("Student with name '" + name + "' not found.");
        }

        System.out.println("Please enter a course section (in the format CECS 277-05): ");
        String secStr = scan.nextLine();
        String [] secArr = secStr.split(" |\\-");
        while (secArr.length != 3) {
            System.out.println("Invalid input format. Please try again: ");
            secStr = scan.nextLine();
            secArr = secStr.split(" |\\-");
        }

        var sec = em.createQuery("SELECT s FROM SECTIONS s JOIN s.course c JOIN s.semester sem JOIN c.department d WHERE "
            + "s.sectionNumber = ?3 AND d.abbreviation = ?1 AND c.number = ?2 AND sem.title = ?4", Section.class);

        Section section;
        try {
            sec.setParameter(1, secArr[0]);
            sec.setParameter(2, secArr[1]);
            sec.setParameter(3, Integer.parseInt(secArr[2]));
            sec.setParameter(4, sem.getTitle());
            section = sec.getSingleResult();
            System.out.println(student.registerForSection(section));
            if (student.registerForSection(section) == RegistrationResult.SUCCESS) {
                em.getTransaction().begin();
                student.addSection(section);
                em.getTransaction().commit();
            }
        }
        catch (NoResultException ex) {
            System.out.println("That section does not exist");
        }
    }


    public static void main(String[] args) throws Exception {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();
        String input = "4";

        while (!input.equals("0")) {
            System.out.println("Please choose an option: ");
            System.out.println("1: Instantiate Model");
            System.out.println("2: Student Lookup");
            System.out.println("3: Section Registration");
            System.out.println("0: Exit");
            Scanner scan = new Scanner(System.in);
            input = scan.nextLine();

            if (input.equals("1")) {
                instantiateModel(em);
                //need to switch to drop-and-create to run a second time
            }
            else if (input.equals("2")) {
                studentLookup(em);
            }
            else if (input.equals("3")) {
                registration(em);
            }
            else if (input.equals("0")) {
                System.out.println("Exiting...");
            }
            else {
                System.out.println("Invalid Input");
            }
        }
    }
}
