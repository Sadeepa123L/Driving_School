package lk.ijse.configuration;

import lk.ijse.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.FileInputStream;
import java.util.Properties;


public class FactoryConfiguration {

    private static FactoryConfiguration factoryConfiguration;

    private static SessionFactory session;

    private FactoryConfiguration() {
        try {
            Configuration configuration = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Course.class)
                    .addAnnotatedClass(Instructor.class)
                    .addAnnotatedClass(Lesson.class)
                    .addAnnotatedClass(Payment.class)
                    .addAnnotatedClass(Student.class);



            session = configuration.buildSessionFactory();
            System.out.println("Hibernate SessionFactory created successfully, DB should be ready!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Hibernate configuration failed", e);
        }
    }

    public static FactoryConfiguration getInstance() {
        return(factoryConfiguration==null)?factoryConfiguration = new FactoryConfiguration():factoryConfiguration;
    }
    public static Session getSession() {
        return session.openSession();
    }
}
