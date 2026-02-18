package com.example.dockerpipeline.controller;

import com.example.dockerpipeline.model.OrderEntity;
import com.example.dockerpipeline.model.Product;
import com.example.dockerpipeline.model.User;
import com.example.dockerpipeline.repository.OrderRepository;
import com.example.dockerpipeline.repository.ProductRepository;
import com.example.dockerpipeline.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public ApiController(UserRepository userRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> users() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> products() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderEntity>> orders() {
        return ResponseEntity.ok(orderRepository.findAll());
    }
}
