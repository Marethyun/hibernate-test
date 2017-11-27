package fr.marethyun;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class HibernateTest {

    private static SessionFactory factory;

    public static void main(String... args){

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        Configuration config = new Configuration();

        config.setProperty("hibernate.show_sql", "false");

        config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        config.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost/hibernate");

        config.setProperty("hibernate.connection.username", "root");
        config.setProperty("hibernate.connection.password", "root");

        config.addResource("fr/marethyun/Book.hbm.xml");

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

                System.out.println(book.getName() + " -> " + book.getColor() + " printed");
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
