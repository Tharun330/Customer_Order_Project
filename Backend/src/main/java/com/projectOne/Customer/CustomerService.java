package com.projectOne.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projectOne.ExceptionHandling.NotFoundException;
import com.projectOne.Logger.LoggerService;
import com.projectOne.Order.Order;
import com.projectOne.Order.OrderRepository;

//import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private LoggerService loggerService;

    public Customer addCustomer(Customer customer) {
        loggerService.info("Adding new customer: " + customer);
        return repository.save(customer);
    }

    public List<Customer> getAllCustomer() {

        loggerService.info("Fetching all customers");
        List<Customer> customers = repository.findAll();
        if (customers.isEmpty()) {
            throw new NotFoundException("Failed to fetch details..");
        }
        return customers;
    }

    public Customer getCustomerById(long id) {
        Customer res = repository.findById(id).orElse(null);
        loggerService.info("Fetching customer with ID: " + id);
        loggerService.debug("Customer details: " + res);
        if (res == null) {
            throw new NotFoundException("No Account Found with this Id");
        }
        return res;
    }

    public void updateCustomer(Customer customer) {
        loggerService.info("Updating Customer: " + customer);
        repository.save(customer);
    }

    public void deleteCustomerById(long id) {
        loggerService.info("Deleting Customer with ID: " + id);
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("No Customer Found with this Id"));

        repository.deleteById(id);
        loggerService.info("Customer with ID " + customer.getId() + " deleted successfully.");
    }

    public Iterable<Order> getCustomerOrders(long id) {

        Customer customer = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("No Customer Found with this Id"));

        // List<Order> customerOrders = new ArrayList<>();
        // orders.forEach((order) -> {
        // if (order.getCustomer().getId() == customer.getId()) {
        // customerOrders.add(order);
        // }
        // });
        loggerService.info("Fetching customer's orders...");
        List<Order> customerOrders = orderRepository.findByCustomer_Id(customer.getId());
        if (customerOrders.isEmpty()) {
            throw new NotFoundException("No Orders Found for this Customer");
        }

        return customerOrders;
    }

}
