package com.projectOne.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projectOne.Order.Order;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        List<Customer> response = service.getAllCustomer();
        return response;
    }

    @GetMapping("/customers/{id}")
    public Customer getSingleCustomer(@PathVariable long id) {
        Customer response = service.getCustomerById(id);
        return response;
    }

    @GetMapping("/customers/orders/{id}")
    public ResponseEntity<Iterable<Order>> getCustomerOrders(@PathVariable long id) {
        Iterable<Order> orders = service.getCustomerOrders(id);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/addcustomers")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer newCustomer = new Customer(customer.getId(), customer.getName(), customer.getEmail(),
                customer.getPassword());
        Customer savedCustomer = service.addCustomer(newCustomer);
        return ResponseEntity.ok(savedCustomer);
    }

    @PatchMapping("/customers/update/{id}")
    public String updateCustomerDetails(@RequestBody Customer customer) {
        long custId = customer.getId();
        Customer currentCustomer = service.getCustomerById(custId);
        currentCustomer.setName(customer.getName());
        currentCustomer.setEmail(customer.getEmail());
        currentCustomer.setPassword(customer.getPassword());
        service.updateCustomer(currentCustomer);
        return "Updated Successfully";
    }

    @DeleteMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable long id) {
        service.deleteCustomerById(id);
        return "Account Deleted";

    }

}