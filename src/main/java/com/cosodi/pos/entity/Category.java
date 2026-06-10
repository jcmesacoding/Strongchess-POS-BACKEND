package com.cosodi.pos.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(
			name = "name",
			nullable = false,
			length = 100,
			unique = true
	)
	private String name;

	@Column(
			name = "description",
			length = 255
	)
	private String description;
}