import java.time.*;
import java.util.*;

import jakarta.persistence.*;
import model.*;
public class App {
    // These demos show finding, creating, and updating individual objects.
    private static void basicDemos() {
        // In true Java fashion, we can't just create an EntityManager; we have to first
        // create a Factory that can then create the Manager. Don't ask me why.

        // The parameter "demoDb" matches the "name" of our data source, set in
        // src/META-INF/persistence.xml.
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();
        
        // The EntityManager object lets us find, create, update, and delete individual
        // instances of our entity classes.
        
    }

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
        

        Transcript aa1 = new Transcript("A", a, student1);
        Transcript ba1 = new Transcript("A", b, student1);
        Transcript ca1 = new Transcript("A", c, student1);
        Transcript ac2 = new Transcript("C", a, student2);
        Transcript bc2 = new Transcript("C", b, student2);
        Transcript cc2 = new Transcript("C", c, student2);
        Transcript ac3 = new Transcript("C", a, student3);
        Transcript bb3 = new Transcript("B", b, student3);
        Transcript cd3 = new Transcript("D", c, student3);

        //student1.addSection(d);
        d.addStudent(student1);

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
        // The important bit is "MUSEUMS m"; this tells the query to iterate over the
        // MUSEUMS table one row at a time, temporarily calling each row "m". We can then
        // refer to "m" like it's an object, in order to select a row or filter based 
        // on its columns.

        // createQuery returns a Query object, which can be executed with getSingleResult
        // if it always returns <= 1 object.

        // If we want to SAFELY involve user input, we use a TypedQuery.
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter a student name: ");
        String name = input.nextLine();

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

        // System.out.println();
        // System.out.println("Example #4: Using JPQL to select all museums");
        // // This is simple. Just omit the WHERE, and use .getResultList().
        // var students = em.createQuery("select s from STUDENTS s", Student.class).getResultList();

        // for (Student s : students) {
        //     System.out.println(s);
        // }
    }


    public static void main(String[] args) throws Exception {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();
        instantiateModel(em);
        studentLookup(em);
    }
}
