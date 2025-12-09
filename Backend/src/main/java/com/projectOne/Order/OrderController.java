package com.projectOne.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders/{customer_Id}/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order, @PathVariable long customer_Id) {

        Order createdOrder = orderService.createOrder(order, customer_Id);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/orders/{id}")
    public Order getOrderById(@PathVariable long id) {
        return orderService.getOrderById(id);

    }

    @GetMapping("/orders")
    public ResponseEntity<Iterable<Order>> getAllOrders() {
        Iterable<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("/orders/update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order changedOrder) {
        Order updatedOrder = orderService.updateOrder(id, changedOrder);
        return ResponseEntity.ok(updatedOrder);
    }

    @PatchMapping("/orders/updatestatus/{id}")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestBody OrderStatusUpdate newStatus) {

        Order updateOrder = orderService.updateOrderStatus(id, newStatus);
        return ResponseEntity.ok(updateOrder);

    }

    @DeleteMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "Order Deleted Successfully";

    }

}
