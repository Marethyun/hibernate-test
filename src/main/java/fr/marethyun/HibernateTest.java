package fr.marethyun;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;

public class HibernateTest {

    private static SessionFactory factory;
    private static long time;

    public static void main(String... args){
        time = System.currentTimeMillis();

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        Configuration config = new Configuration();
        // Disable SQL logging
        //config.setProperty("hibernate.show_sql", "false");
        // Create table using mappers, if they don't exists
        config.setProperty("hibernate.hbm2ddl.auto", "update");
        config.setProperty("c3p0.max_size", "300");
        config.setProperty("hibernate.c3p0.max_statements", "10");
        //config.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.EhCacheProvider");

        // Configuring MySQL dialect
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
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

        Transaction tx = null;
        System.out.println(Double.toString((System.currentTimeMillis() - time) / 1000.0) + "s");
        try {

            Session session = factory.openSession();

            for (int i = 0; i < 100; i++) {

                tx = session.beginTransaction();

                System.out.print(session.createQuery("FROM Book").list().get(0) + " ");
                System.out.println("");

                tx.commit();
            }
            session.close();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        factory.close();

        System.out.println(Double.toString((System.currentTimeMillis() - time) / 1000.0) + "s");
    }
}
