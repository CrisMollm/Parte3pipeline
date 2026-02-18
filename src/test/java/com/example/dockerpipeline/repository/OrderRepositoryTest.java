package com.example.dockerpipeline.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.example.dockerpipeline.model.OrderEntity;

@SpringBootTest
@Sql("/db/init.sql")
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository repo;

    @Test
    void countOrders() {
        List<OrderEntity> all = repo.findAll();
        assertEquals(10, all.size());
    }

    @Test
    void findOrderById() {
        OrderEntity o = repo.findById(1L).orElse(null);
        assertNotNull(o);
        assertTrue(o.getQuantity() > 0);
    }

    @Test
    void saveOrder() {
        // create minimal related entities via ids is complex; just test saving a blank order via constructor
        OrderEntity o = new OrderEntity();
        // quantity only test
        o.setQuantity(5);
        OrderEntity saved = repo.save(o);
        assertNotNull(saved.getId());
        repo.delete(saved);
    }

    @Test
    void deleteOrder() {
        OrderEntity o = repo.save(new OrderEntity());
        Long id = o.getId();
        repo.deleteById(id);
        assertTrue(repo.findById(id).isEmpty());
    }

    @Test
    void updateOrder() {
        OrderEntity o = repo.findById(2L).orElseThrow();
        int old = o.getQuantity();
        o.setQuantity(old + 1);
        repo.save(o);
        OrderEntity updated = repo.findById(2L).orElseThrow();
        assertEquals(old + 1, updated.getQuantity());
    }

    @Test
    void findAllNotEmpty() {
        assertFalse(repo.findAll().isEmpty());
    }

    @Test
    void multipleSaves() {
        OrderEntity a = repo.save(new OrderEntity());
        OrderEntity b = repo.save(new OrderEntity());
        assertTrue(a.getId() > 0 && b.getId() > 0);
        repo.delete(a);
        repo.delete(b);
    }

    @Test
    void idGeneration() {
        OrderEntity o = repo.save(new OrderEntity());
        assertNotNull(o.getId());
        repo.delete(o);
    }

    @Test
    void repoExists() {
        assertNotNull(repo);
    }

    @Test
    void sampleQueryWorks() {
        List<OrderEntity> found = repo.findAll();
        assertEquals(10, found.size());
    }
}
