package Repository;

import Model.Bilet;
import Model.Spectacol;
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

public class BiletRepository implements IBiletRepository {


    public BiletRepository() throws SQLException {
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
    public Iterable<Bilet> findBySpectacol(String spectacol){
        this.initialize();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql="from Bilet where spectacol =:spectacol";
                //String sql = "from Bilet where id = :id";
                Query query = session.createQuery(sql, Bilet.class);
                query.setParameter("spectacol", spectacol);
                ArrayList<Bilet> results = new ArrayList<>(
                        ((org.hibernate.query.Query) query).list());
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
    public Bilet findByNR_Spectacol(int rand, int nr, String spectacol) {
        this.initialize();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql = "from Bilet where numar=:nr and rand=:rand and spectacol=:spectacol ";
                Query query = session.createQuery(sql);

                query.setParameter("nr", nr);
                query.setParameter("rand", rand);
                query.setParameter("spectacol",spectacol);
                Bilet p = (Bilet) query.getResultList().get(0);

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
    public Bilet findOne(String s){
        this.initialize();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql = "from Bilet where id = :id";
                Query query = session.createQuery(sql);
                query.setParameter("id", s);
                Bilet p = (Bilet) query.getResultList().get(0);

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
    public Iterable<Bilet> findAll(){
        this.initialize();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql="from Bilet";
                ArrayList<Bilet> results = new ArrayList<>(
                        session.createQuery(sql, Bilet.class).
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
    public Bilet save(Bilet entity) {
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
    public Bilet delete(String id) {
        this.initialize();
        Bilet old=null;
        old=findOne(id);
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql = "Delete from Bilet where id = :id";
                Query query = session.createQuery(sql);
                query.setParameter("id", id);
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
    public Bilet update(Bilet entity){
        this.initialize();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql="UPDATE Bilet SET spectacol=:spectacol,spectator=:spectator, rand=:rand,numar=:numar WHERE id =:id";
                Query qry = session.createQuery(sql);
                qry.setParameter("rand", entity.getRand());
                qry.setParameter("numar", entity.getNumar());
                qry.setParameter("spectacol", entity.getSpectacol());
                qry.setParameter("spectator", entity.getSpectator());
                qry.setParameter("id", entity.getId());
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

}

