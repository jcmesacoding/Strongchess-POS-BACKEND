package com.cosodi.pos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "providers")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Provider {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "company_name", nullable = false, length = 100)
	private String companyName;

	@Column(name = "contact_name", nullable = false, length = 100)
	private String contactName;

	@Column(name = "phone", nullable = false, length = 20)
	private String phone;

	@Column(name = "email", nullable = false, length = 100)
	private String email;

	@Column(name = "address", nullable = false, length = 255)
	private String address;

	@Column(name = "registration_date", nullable = false)
	private LocalDateTime registrationDate;

	@PrePersist
	public void assignRegistrationDate() {
		this.registrationDate = LocalDateTime.now();
	}
}