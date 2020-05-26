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

public class SpectacolRepository implements ISpectacolRepository {


    public SpectacolRepository() throws SQLException {
    }
     SessionFactory sessionFactory;
    void initialize() {
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

    void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }


    @Override
    public Spectacol findOne(String s){
        this.initialize();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql = "from Spectacol where id = :id";
                Query query = session.createQuery(sql);
                query.setParameter("id", s);
                Spectacol p = (Spectacol) query.getResultList().get(0);

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
    public Iterable<Spectacol> findAll(){
        this.initialize();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql="from Spectacol";
                ArrayList<Spectacol> results = new ArrayList<>(
                        session.createQuery(sql, Spectacol.class).
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
    public Spectacol save(Spectacol entity) {
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
    public Spectacol delete(String id) {
        this.initialize();
        Spectacol old=null;
        old=findOne(id);
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql = "Delete from Spectacol where id = :id";
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
    public Spectacol update(Spectacol entity){
        this.initialize();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql="UPDATE Spectacol SET descriere=:descriere,pretBilet=:pretbilet WHERE id =:id";
                Query qry = session.createQuery(sql);

                //qry.setParameter("titlu", entity.getTitlu());
                qry.setParameter("descriere", entity.getDescriere());
                //qry.setParameter("dataSiora", entity.getDataSiora());
                qry.setParameter("pretbilet", entity.getPretBilet());
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
    @Override
    public Iterable<Spectacol> findAll_disponibile(){
        this.initialize();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql="from Spectacol where dataSiora < datetime('now')";
                ArrayList<Spectacol> results = new ArrayList<>(
                        session.createQuery(sql, Spectacol.class).
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
}

