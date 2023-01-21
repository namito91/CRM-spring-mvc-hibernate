package com.company.springdemo.dao;
import com.company.springdemo.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO{

    // need to inject the session factory
    @Autowired
    private SessionFactory sessionFactory;

   // @Transactional  // esta funcionalidad la maneja ahora la clase "customerServiceImpl.java"
    @Override
    public List<Customer> getCustomers() {

        // get the current hibernate session
        Session session = sessionFactory.getCurrentSession();

        // create a query ... order by last name
        Query<Customer> query = session.createQuery
                ("from Customer order by lastName", Customer.class);

        // execute the query and get result list
        // return the result
        return query.getResultList();
    }

    @Override
    public void saveCustomer(Customer customer) {
        // get the current hibernate session
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(customer);
    }

    @Override
    public Customer getCustomer(int id) {
        // get the current hibernate session
        Session session = sessionFactory.getCurrentSession();
        
        // retrieve data from the database
        return session.get(Customer.class,id);
    }

    @Override
    public void deleteCustomer(int id) {
        // get the current hibernate session
        Session session = sessionFactory.getCurrentSession();

        Customer customer = session.get(Customer.class,id);

        session.delete(customer);
    }

    @Override
    public List<Customer> searchCustomers(String theSearchName) {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Customer> theQuery = null;

        //
        // only search by name if theSearchName is not empty
        //
        if (theSearchName != null && theSearchName.trim().length() > 0) {
            // search for firstName or lastName ... case insensitive
            theQuery =currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
        }
        else {
            // theSearchName is empty ... so just get all customers
            theQuery =currentSession.createQuery("from Customer", Customer.class);
        }

        // execute query and get result list
        // return the results
        return (List<Customer>) theQuery.getResultList();
    }
}
