package com.loan.microservice.app.accounts.repository;

import com.loan.microservice.app.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

     Optional<Customer> findByMobileNumber(String mobileNumber);


     // Fetch single user details using JPQL + projection
     @Query("SELECT c FROM Customer c WHERE c.mobileNumber = :mobileNumber")
     Optional<Customer> findUserDetailsByMobileNumber(@Param("mobileNumber") String mobileNumber);
}