package Repository;

import Model.Bilet;
import Model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class UserRepository implements IUserRepository {


    public UserRepository() throws SQLException {
    }
    static SessionFactory sessionFactory;
    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }
    @Override
    public User findOne(String s){
        this.initialize();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql = "from User where username = :user";
                Query query = session.createQuery(sql);
                query.setParameter("user", s);
                User p = (User) query.getResultList().get(0);

                tx.commit();
                return p;
            } catch (RuntimeException ex) {

                if (tx != null)
                    tx.rollback();
                System.out.println(ex);
                this.close();
            }
        }
        this.close();
        return null;
    }
    @Override
    public Iterable<User> findAll(){
        this.initialize();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql="from User";
                ArrayList<User> results = new ArrayList<>(
                        session.createQuery(sql, User.class).
                                list());
                tx.commit();
                return results;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                this.close();

            }
        }
        this.close();
        return null;
    }

    @Override
    public User save(User entity) {
        this.initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                if (findOne(entity.getId()) != null) {
                    return null;
                }
                session.save(entity);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        this.close();
        return null;
    }


    @Override
    public User delete(String id) {
        this.initialize();
        User old=null;
        old=findOne(id);
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql = "Delete from User where username = :user";
                Query query = session.createQuery(sql);
                query.setParameter("user", id);
                int result = query.executeUpdate();
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                this.close();
            }
        }
        return old;
    }

    @Override
    public User update(User entity){
        this.initialize();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql="UPDATE User SET password=:pass,nume=:nume,prenume=:prenume,email=:email WHERE username =:user";
                Query qry = session.createQuery(sql);
                qry.setParameter("pass", entity.getPassword());
                qry.setParameter("user", entity.getUsername());
                qry.setParameter("nume", entity.getNume());
                qry.setParameter("prenume", entity.getPrenume());
                qry.setParameter("email", entity.getEmail());

                qry.executeUpdate();
                tx.commit();

            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                this.close();
                return entity;
            }
        }
        this.close();
        return null;
    }


    public String login(User user){
        this.initialize();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql = "from User where username = :user";

                Query query = session.createQuery(sql);
                query.setParameter("user", user.getUsername());
                User p = (User) query.getResultList().get(0);

                tx.commit();
                if(p==null)
                    return null;
                if(p.getPassword().equals(user.getPassword()))
                    return p.getType();
                else
                    return null;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                System.out.println(ex);
                this.close();
            }
        }
        this.close();
        return null;
    }
}
