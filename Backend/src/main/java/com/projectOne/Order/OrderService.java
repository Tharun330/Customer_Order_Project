package com.projectOne.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projectOne.Customer.Customer;
import com.projectOne.Customer.CustomerRepository;
import com.projectOne.ExceptionHandling.NotFoundException;
import com.projectOne.Item.Item;
import com.projectOne.Item.ItemRepository;
import com.projectOne.Logger.LoggerService;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private LoggerService loggerService;

    @Transactional
    public Order createOrder(Order order, long customer_Id) {
        loggerService.info("Fetching custoemr's details...");
        Customer customer = customerRepository.findById(customer_Id)
                .orElseThrow(() -> new NotFoundException("Customer not found!" + customer_Id));
        loggerService.info("Customer Details: " + customer);
        order.setCustomer(customer);

        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
                item.setOrder(order);

                if (item.getItem() == null || item.getItem().getItemId() == null) {
                    throw new IllegalArgumentException("Each order item must contain an item with an id");
                }

                Integer itemId = item.getItem().getItemId();
                Item currentItem = itemRepository.findById(itemId).orElseThrow(
                        () -> new NotFoundException("Item not found with id " + itemId));
                System.out.println("ITEM OBJECT = " + currentItem);

                item.setItem(currentItem);
                item.setOrder(order);
            }
        }
        customer.getOrders().add(order);
        loggerService.info("Order Confirm: " + order);

        Order savedOrder = orderRepository.save(order);
        return savedOrder;
    }

    public Order getOrderById(long id) {
        loggerService.info("Fetching order with ID: " + id);
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found with id " + id));
    }

    @Transactional
    public Order updateOrder(Long id, Order changedOrder) {

        loggerService.info("Fetching Order in DB... ");
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id " + id));

        existingOrder.setOrderDate(changedOrder.getOrderDate());
        existingOrder.setOrderTime(changedOrder.getOrderTime());
        existingOrder.setStatus(changedOrder.getStatus());

        loggerService.info("Existing order: " + existingOrder);
        existingOrder.getOrderItems().clear();

        if (changedOrder.getOrderItems() != null) {

            for (OrderItem oi : changedOrder.getOrderItems()) {

                if (oi.getItem() == null || oi.getItem().getItemId() == null) {
                    throw new IllegalArgumentException("Each order item must contain an item with an id");
                }

                Integer itemId = oi.getItem().getItemId();
                Item managedItem = itemRepository.findById(itemId).orElseThrow(
                        () -> new NotFoundException("Item not found with id " + itemId));

                OrderItem newOrderItem = new OrderItem();
                newOrderItem.setItem(managedItem);
                newOrderItem.setQuantity(oi.getQuantity());
                newOrderItem.setUnitPrice(oi.getUnitPrice());
                existingOrder.addOrderItem(newOrderItem);

            }
        }
        loggerService.info("Update order: " + existingOrder);
        orderRepository.save(existingOrder);
        return existingOrder;

    }

    @Transactional
    public Order updateOrderStatus(Long id, OrderStatusUpdate newStatus) {

        loggerService.info("Updating order status to:: " + newStatus.getStatus());
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id " + id));

        existingOrder.setStatus(newStatus.getStatus());

        return existingOrder;
    }

    @Transactional
    public void deleteOrder(Long id) {

        loggerService.info("Deleting order with ID: " + id);
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id " + id));

        if (existingOrder.getCustomer() != null) {
            existingOrder.getCustomer().getOrders().remove(existingOrder);
        }
        orderRepository.delete(existingOrder);
    }

    public Iterable<Order> getAllOrders() {
        loggerService.info("Fetching all orders...");
        return orderRepository.findAll();
    }

}
