package com.example.dockerpipeline.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;

import com.example.dockerpipeline.model.Product;

@SpringBootTest
@Sql("/db/init.sql")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repo;

    @Test
    void countProducts() {
        List<Product> all = repo.findAll();
        assertEquals(10, all.size());
    }

    @Test
    void findProductById() {
        Product p = repo.findById(1L).orElse(null);
        assertNotNull(p);
        assertEquals("Widget A", p.getName());
    }

    @Test
    void saveProduct() {
        Product p = new Product("TProd", 1.23);
        Product saved = repo.save(p);
        assertNotNull(saved.getId());
        repo.delete(saved);
    }

    @Test
    void deleteProduct() {
        Product p = new Product("TD","d@example.com".hashCode());
        Product saved = repo.save(p);
        Long id = saved.getId();
        repo.deleteById(id);
        assertTrue(repo.findById(id).isEmpty());
    }

    @Test
    void updateProduct() {
        Product p = repo.findById(2L).orElseThrow();
        p.setPrice(123.45);
        repo.save(p);
        Product updated = repo.findById(2L).orElseThrow();
        assertEquals(123.45, updated.getPrice());
    }

    @Test
    void findAllNotEmpty() {
        assertFalse(repo.findAll().isEmpty());
    }

    @Test
    void multipleSaves() {
        Product a = repo.save(new Product("A", 1.0));
        Product b = repo.save(new Product("B", 2.0));
        assertTrue(a.getId() > 0 && b.getId() > 0);
        repo.delete(a);
        repo.delete(b);
    }

    @Test
    void idGeneration() {
        Product p = repo.save(new Product("G", 9.9));
        assertNotNull(p.getId());
        repo.delete(p);
    }

    @Test
    void repoExists() {
        assertNotNull(repo);
    }

    @Test
    void sampleQueryWorks() {
        List<Product> found = repo.findAll();
        assertEquals(10, found.size());
    }
}
