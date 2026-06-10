package com.cosodi.pos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sale_details")
public class SaleDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(
			name = "sale_id",
			nullable = false,
			foreignKey = @ForeignKey(name = "FK_SALE_DETAIL_SALE")
	)
	private Sale sale;

	@ManyToOne
	@JoinColumn(
			name = "product_id",
			nullable = false,
			foreignKey = @ForeignKey(name = "FK_SALE_DETAIL_PRODUCT")
	)
	private Product product;

	@Column(nullable = false)
	private Integer units;

	@Column(name = "sale_price", nullable = false)
	private BigDecimal salePrice;

	@Column(nullable = false)
	private BigDecimal discount;
}