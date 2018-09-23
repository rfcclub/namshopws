package com.test.demo.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Product {
	@GraphQLQuery(name = "id", description = "product id")
	@Id
	@GeneratedValue
	private Long id;
	@GraphQLQuery(name = "name", description = "name of product")
	private @NotNull String name;
	@GraphQLQuery(name = "price", description = "price of product")
	private Double price;

	public Product() {
		super();
	}

	public Product(String name, Double price) {
		this();
		this.name = name;
		this.price = price;
	}

	/**
	 * TODO: This field is not include because it has problem with bi-directional link in
	 * Hibernate and business logic. 
	 * Since both Order and Product refer to different lines in LineItem,
	 * maybe a NoSQL db is more suitable for this field.
	 */
//	@GraphQLQuery(name = "lineItems", description = "line items of product")
//	@Transient
//	private List<LineItem> lineItems;
}
