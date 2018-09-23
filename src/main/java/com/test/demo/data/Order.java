package com.test.demo.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "purchase_order")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Order {
	@Id
	@GeneratedValue
	@GraphQLQuery(name = "id", description = "order id")
	private Long id;
	private String note;
	@GraphQLQuery(name = "totalPrice", description = "total price of order")
	private Double totalPrice;

	@GraphQLQuery(name = "lineItems", description = "List of items")
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private List<LineItem> lineItems;

	/**
	 * Calculate total price base on line items.
	 */
	public void calculate() {
		totalPrice = 0.00;
		for (LineItem item : lineItems) {
			totalPrice += item.getQuantity() * item.getPrice();
		}
	}
}
