package com.cosodi.pos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "description", length = 255)
	private String description;

	@Column(name = "sale_price", precision = 10, scale = 2, nullable = false)
	private BigDecimal salePrice;

	@Column(name = "purchase_price", precision = 10, scale = 2, nullable = false)
	private BigDecimal purchasePrice;

	@Column(name = "current_stock", nullable = false)
	private Integer currentStock;

	@Column(name = "min_stock", nullable = false)
	private Integer minStock;

	@Column(name = "max_stock", nullable = false)
	private Integer maxStock;

	@Column(name = "registration_date", nullable = false, updatable = false)
	private LocalDateTime registrationDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "provider_id",
			nullable = false,
			foreignKey = @ForeignKey(name = "FK_PRODUCT_PROVIDER")
	)
	private Provider provider;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "category_id",
			nullable = false,
			foreignKey = @ForeignKey(name = "FK_PRODUCT_CATEGORY")
	)
	private Category category;

	@PrePersist
	public void assignRegistrationDate() {
		this.registrationDate = LocalDateTime.now();
	}

	/**
	 * Ganancia unitaria por producto.
	 */
	@Transient
	public BigDecimal getProfitPerUnit() {

		if (salePrice == null || purchasePrice == null) {
			return BigDecimal.ZERO;
		}

		return salePrice.subtract(purchasePrice);
	}

	/**
	 * Indica si el producto tiene inventario bajo.
	 */
	@Transient
	public boolean isLowStock() {

		return currentStock != null
				&& minStock != null
				&& currentStock <= minStock;
	}

	/**
	 * Indica si el producto está agotado.
	 */
	@Transient
	public boolean isOutOfStock() {

		return currentStock != null
				&& currentStock == 0;
	}
}