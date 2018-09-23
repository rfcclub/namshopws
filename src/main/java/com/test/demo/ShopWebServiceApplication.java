package com.test.demo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.test.demo.data.LineItem;
import com.test.demo.data.Order;
import com.test.demo.data.Product;
import com.test.demo.data.Shop;
import com.test.demo.repositories.LineItemRepository;
import com.test.demo.service.OrderService;
import com.test.demo.service.ProductService;
import com.test.demo.service.ShopService;

@SpringBootApplication
public class ShopWebServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShopWebServiceApplication.class, args);
	}

	@Bean
	ApplicationRunner init(ProductService productService, OrderService orderService, ShopService shopService,
			LineItemRepository lir) {
		return args -> {
			Shop shop = new Shop("Testing shop");
			shop.setOrders(new HashSet<>());
			shop.setProducts(new HashSet<>());

			// create test data for web service
			Stream.of("Ferrari", "Jaguar", "Porsche", "Lamborghini", "Bugatti", "AMC Gremlin", "Triumph Stag",
					"Ford Pinto", "Yugo GV").forEach(name -> {
						Product product = new Product(name, 123.45);
						product = productService.saveProduct(product);
						LineItem item = new LineItem(product, 1);
						Order order = new Order();
						List<LineItem> items = new ArrayList<LineItem>();
						items.add(item);
						order.setLineItems(items);
						order.calculate();
						order = orderService.saveOrder(order);
						shop.getOrders().add(order);
						shop.getProducts().add(product);
					});

			shopService.saveShop(shop);
			productService.getProducts().forEach(System.out::println);
			shopService.getShops().forEach(System.out::println);
			orderService.getOrders().forEach(System.out::println);
		};
	}

}
