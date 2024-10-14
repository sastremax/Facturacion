package edu.coderhouse.jpa.service;

import edu.coderhouse.jpa.entity.Client;
import edu.coderhouse.jpa.entity.Invoice;
import edu.coderhouse.jpa.entity.Product;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DaoFactory {
    private final SessionFactory sessionFactory;

    @Autowired
    public DaoFactory(EntityManagerFactory factory) {
        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    public void create(Object obj) {
        Session session = sessionFactory.getCurrentSession();
        try {
        session.beginTransaction();
        session.save(obj);
        session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void update(Object obj) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        try {
        session.beginTransaction();
        session.merge(obj);
        session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public Client getClienteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Client client = session.get(Client.class, id);
            session.getTransaction().commit();
            return client;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public Invoice getInvoiceById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Invoice invoice = session.get(Invoice.class, id);
        session.getTransaction().commit();
        session.close();
        return invoice;
    }

    public List<Product> getAllProducts() {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            List<Product> products = session.createQuery("from Product", Product.class).list();
            session.getTransaction().commit();
            return products;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

}
