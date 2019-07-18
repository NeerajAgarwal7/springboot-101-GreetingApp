package com.mindtree.springbootWeb.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindtree.springbootWeb.model.Greeting;

@Repository
public interface GreetingRepository extends JpaRepository<Greeting, BigInteger> {

}
