package com.cosodi.pos.repository;

import com.cosodi.pos.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICustomerRepository extends IGenericRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE LOWER(c.firstname) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(c.lastname) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(c.email) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Customer> searchByName(@Param("query") String query);

}
