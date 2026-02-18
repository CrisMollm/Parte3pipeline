package com.example.dockerpipeline.repository;

import com.example.dockerpipeline.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
