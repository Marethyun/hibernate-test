package fr.marehyun;

import org.hibernate.cfg.Configuration;

public class HibernateTest {
    public static void main(String... args){
        Configuration config = new Configuration();
        config.setProperty("hibernate.dialect", "org.hibernate.MySQLDialect");
        config.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
    }
}
