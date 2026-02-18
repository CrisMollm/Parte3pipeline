package com.example.dockerpipeline.repository;

import com.example.dockerpipeline.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
