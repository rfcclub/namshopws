package com.test.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.test.demo.data.Order;
import com.test.demo.repositories.OrderRepository;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;

/**
 * GraphQL service for order table.
 * 
 * @author Nam
 *
 */
@Service
public class OrderService {
	private final OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	/**
	 * Query order using id
	 * Usage: Query order has id = 1 and return id, totalPrice.
	 * { 	order(id:1) {
	 * 		id
	 * 		totalPrice
	 * 	}
	 * }
	 *  
	 * @param id id of order
	 * @return found order or null.
	 */
	@GraphQLQuery(name = "order", description="Query order using id")
	public Optional<Order> getOrderById(@GraphQLArgument(name = "id") Long id) {
		return orderRepository.findById(id);
	}

	/**
	 * Save order object to database.
	 * Usage: Save new order and return id and totalPrice of new order
	 * 		mutation { 
	 *   		saveProduct(order: { ....<<order object>>... }) {
	 *           	id
	 *           	totalPrice
	 *      }   
	 * @param order order which need to save
	 * @return persisted order.
	 */
	@GraphQLMutation(name = "saveOrder", description="Save order object to database.")
	public Order saveOrder(@GraphQLArgument(name = "order", description="Argument under JSON format") Order order) {
		return orderRepository.save(order);
	}

	/**
	 * Delete order base on id
	 * Usage: Delete order with id = 1.
	 * 		mutation { 
	 *   		deleteOrder(id: 1) 
	 *      } 
	 * @param id id of order we want to delete
	 */
	@GraphQLMutation(name = "deleteOrder", description="Delete order base on id")
	public void deleteOrder(@GraphQLArgument(name = "id") Long id) {
		orderRepository.deleteById(id);
	}

	/**
	 * Get all orders.
	 * Usage: Get all orders and show details.
	 *  {
	 *  	orders {
	 *  		...<<extra details>>
	 *  	}
	 *  }
	 * @return all orders.
	 */
	@GraphQLQuery(name = "orders", description="Get all orders.")
	public List<Order> getOrders() {
		return orderRepository.findAll();
	}
}
