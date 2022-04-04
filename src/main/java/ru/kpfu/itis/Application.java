package ru.kpfu.itis;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.kpfu.itis.models.entities.User;

import java.sql.Timestamp;

public class Application {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure("old/hibernate.cfg.xml");

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.openSession();
//
//        User user = User.builder()
//                .username("srtrdtfj")
//                .email("dfghgj@yandex.ru")
//                .passwordHash("kutgjyfhdg")
//                .createdAt(Timestamp.valueOf("2000-12-11 12:00:00"))
//                .build();
//
//        session.save(user);
//        User user2 = session.createQuery("from User where id = 1", User.class).uniqueResult();
//        System.out.println(user2);
//        session.delete(user2);

        session.close();
        sessionFactory.close();
    }
}