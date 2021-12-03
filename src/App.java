import java.util.List;
import java.util.Scanner;

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
    public static void main(String[] args) throws Exception {
        basicDemos();
    }
}
