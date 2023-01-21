package com.company.springdemo.service;
import com.company.springdemo.entity.Customer;

import javax.crypto.CipherSpi;
import java.util.List;

public interface CustomerService {
    public List<Customer> getCustomers();
    public void saveCustomer(Customer customer);
    public Customer getCustomer(int id);
    public void deleteCustomer(int id);
    public List<Customer> searchCustomers(String theSearchName);
}
