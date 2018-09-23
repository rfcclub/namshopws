package com.test.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.test.demo.data.LineItem;
import com.test.demo.data.Product;
import com.test.demo.repositories.LineItemRepository;
import com.test.demo.repositories.ProductRepository;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;

/**
 * GraphQL Service for product table.
 * 
 * @author Nam
 *
 */
@Service
public class ProductService {
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	/**
	 * Get product by id of that product.
	 * Usage: Query product has id = 1 and return id, name.
	 * { 	product(id:1) {
	 * 		id
	 * 		name
	 * 	}
	 * }
	 * 
	 * @param id id of product
	 * @return found product or null.
	 */
	@GraphQLQuery(name = "product", description ="Get product by id of that product.")
	public Optional<Product> getProductById(@GraphQLArgument(name = "id") Long id) {
		return productRepository.findById(id);
	}

	/**
	 * Save product to database.
	 * Usage: Save new product and return id and name of new product
	 * 		mutation { 
	 *   		saveProduct(product: { name: "Name", price: 10.30 }) {
	 *           	id
	 *           	name
	 *      }      
	 * @param Product product we want to save.
	 * @return Persisted product.
	 */
	@GraphQLMutation(name = "saveProduct", description="Save product to database.")
	public Product saveProduct(@GraphQLArgument(name = "product", description="Argument under JSON format") Product Product) {
		return productRepository.save(Product);
	}

	/**
	 * Delete product by passing id.
	 * Usage: Delete product with id = 1.
	 * 		mutation { 
	 *   		deleteProduct(id: 1) 
	 *      }
	 * @param id id of product.
	 */
	@GraphQLMutation(name = "deleteProduct", description="Delete product by passing id")
	public void deleteProduct(@GraphQLArgument(name = "id") Long id) {
		productRepository.deleteById(id);
	}

	/**
	 * Get all products in database.
	 * Usage: Get all products and show only id and name.
	 *  {
	 *  	products {
	 *  		id
	 *  		name
	 *  	}
	 *  }
	 * 
	 * @return all products in database.
	 */
	@GraphQLQuery(name = "products", description="Get all products in database.")
	public List<Product> getProducts() {
		return productRepository.findAll();
	}
}
