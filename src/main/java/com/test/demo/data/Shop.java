package com.test.demo.data;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Shop {
	@GraphQLQuery(name = "id", description = "shop id")
	@Id
	@GeneratedValue
	private Long id;
	@GraphQLQuery(name = "name", description = "shop name")
	private @NotNull String name;

	@GraphQLQuery(name = "orders", description = "orders of shop")
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn
	private Set<Order> orders;

	@GraphQLQuery(name = "products", description = "products of shop")
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn
	private Set<Product> products;

	/**
	 * Constructor with name of the shop.
	 * 
	 * @param name name of the shop.
	 */
	public Shop(String name) {
		this.name = name;
	}
}
