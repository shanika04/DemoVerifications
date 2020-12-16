package sqlInjection.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

public class HQL {

  public void hqlTest1(String id) {
    SessionFactory sessionFactory = getSessionFactory();
    Session session = sessionFactory.openSession();
    session.getTransaction().begin();
    String hql = "UPDATE Employee set salary = 100 WHERE id = " + "?";
    Query query = session.createQuery(hql);
    query.setString(1, id);
    session.getTransaction().commit();
    session.close();
  }

  private static SessionFactory sessionFactory;

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      Configuration configuration = new Configuration().configure();
      ServiceRegistry serviceRegistry =
              new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

      sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    return sessionFactory;
  }
}