package com.test.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.test.demo.data.Shop;
import com.test.demo.repositories.ShopRepository;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;

/**
 * GraphQL service for shop table
 * 
 * @author Nam
 *
 */
@Service
public class ShopService {
	private final ShopRepository shopRepository;

	public ShopService(ShopRepository ShopRepository) {
		this.shopRepository = ShopRepository;
	}

	/**
	 * Get shop by id
	 * Usage: Query shop has id = 1 and return shop with predefined details.
	 * { 	shop(id:1) {
	 * 		id
	 * 		name
	 * 		...<< more details >> ...
	 * 	}
	 * }
	 *  
	 * @param id id of shop
	 * @return shop if found, or null.
	 */
	@GraphQLQuery(name = "shop", description="Get shop by id")
	public Optional<Shop> getShopById(@GraphQLArgument(name = "id") Long id) {
		return shopRepository.findById(id);
	}

	/**
	 * Save shop to database.
	 * Usage: Save new shop and return new shop information
	 * 		mutation { 
	 *   		saveShop(shop: { ....<<shop object>>... }) {
	 *           	...<< shop details >>...
	 *      }   
	 * @param shop shop to be saved.
	 * @return Persisted shop.
	 */
	@GraphQLMutation(name = "saveShop", description="Save shop to database.")
	public Shop saveShop(@GraphQLArgument(name = "shop", description="Argument under JSON format") Shop shop) {
		return shopRepository.save(shop);
	}

	/**
	 * Delete shop by id.
	 * Usage: Delete shop with id = 1.
	 * 		mutation { 
	 *   		deleteShop(id: 1) 
	 *      } 
	 * @param id id of the shop.
	 */
	@GraphQLMutation(name = "deleteShop", description="Delete shop by id.")
	public void deleteShop(@GraphQLArgument(name = "id") Long id) {
		shopRepository.deleteById(id);
	}

	/**
	 * Get all shops in database.
	 * Usage: Get all shops and show details.
	 *  {
	 *  	shops {
	 *  		...<<extra details>>
	 *  	}
	 *  } 
	 * @return all shops in database.
	 */
	@GraphQLQuery(name = "shops", description="Get all shops in database.")
	public List<Shop> getShops() {
		return shopRepository.findAll();
	}
}
