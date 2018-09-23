package com.test.demo.data;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class LineItem {
	@GraphQLQuery(name = "id", description = "line item id")
	@Id
	@GeneratedValue
	private Long id;

	@GraphQLQuery(name = "product", description = "order id")
	@ManyToOne(cascade = { CascadeType.MERGE })
	private @NotNull Product product;
	@GraphQLQuery(name = "quantity", description = "quantity")
	private Integer quantity;

	@GraphQLQuery(name = "value", description = "value")
	private Double price;

	@GraphQLQuery(name = "createDate", description = "create date")
	private Timestamp createDate;
	@GraphQLQuery(name = "updateDate", description = "update date")
	private Timestamp updateDate;

	/**
	 * Constructor with product and quantity
	 * 
	 * @param product  product
	 * @param quantity quantity
	 */
	public LineItem(Product product, Integer quantity) {
		this.product = product;
		this.quantity = quantity;
		this.price = product.getPrice();
		createDate = Timestamp.from(new Date().toInstant());
		updateDate = Timestamp.from(new Date().toInstant());
	}

	/**
	 * Create new line item with quantity is 1
	 * 
	 * @return new line item for the same product but has quantity is 1
	 */
	public LineItem createNewLine() {
		LineItem lineItem = new LineItem(product, 1);
		return lineItem;
	}
}
