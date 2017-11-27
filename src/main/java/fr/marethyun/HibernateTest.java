package fr.marethyun;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.logging.Level;

public class HibernateTest {

    private static SessionFactory factory;

    public static void main(String... args){

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        Configuration config = new Configuration();
        // Disable SQL logging
        config.setProperty("hibernate.show_sql", "false");
        // Create table using mappers, if they don't exists
        config.setProperty("hibernate.hbm2ddl.auto", "update");

        // Configuring MySQL dialect
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        config.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost/hibernate");

        // Database account credentials
        config.setProperty("hibernate.connection.username", "root");
        config.setProperty("hibernate.connection.password", "root");

        config.addResource("mappers/Book.hbm.xml");

        try {
            factory = config.buildSessionFactory();
        } catch (Exception e){
            e.printStackTrace();
        }

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            List books = session.createQuery("FROM Book").list();

            for (Object mapped : books) {
                Book book = (Book) mapped;

                System.out.println(book.getName() + " -> " + book.getColor());
            }

            tx.commit();
        } catch (HibernateException e){
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        factory.close();
    }
}
