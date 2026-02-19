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

import com.example.dockerpipeline.model.User;

@SpringBootTest
@Sql("/db/init.sql")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryTest {

    @Autowired
    private UserRepository repo;

    @Test
    void countUsers() {
        List<User> all = repo.findAll();
        assertEquals(10, all.size());
    }

    @Test
    void findUserById() {
        User u = repo.findById(1L).orElse(null);
        assertNotNull(u);
        assertEquals("Alice", u.getName());
    }

    @Test
    void saveUser() {
        User u = new User("TestUser","t@example.com");
        User saved = repo.save(u);
        assertNotNull(saved.getId());
        repo.delete(saved);
    }

    @Test
    void deleteUser() {
        User u = new User("ToDelete","d@example.com");
        User saved = repo.save(u);
        Long id = saved.getId();
        repo.deleteById(id);
        assertTrue(repo.findById(id).isEmpty());
    }

    @Test
    void updateUser() {
        User u = repo.findById(2L).orElseThrow();
        u.setEmail("bob+updated@example.com");
        repo.save(u);
        User updated = repo.findById(2L).orElseThrow();
        assertEquals("bob+updated@example.com", updated.getEmail());
    }

    @Test
    void findAllNotEmpty() {
        assertFalse(repo.findAll().isEmpty());
    }

    @Test
    void multipleSaves() {
        User a = repo.save(new User("A","a@x.com"));
        User b = repo.save(new User("B","b@x.com"));
        assertTrue(a.getId() > 0 && b.getId() > 0);
        repo.delete(a);
        repo.delete(b);
    }

    @Test
    void idGeneration() {
        User u = repo.save(new User("G","g@example.com"));
        assertNotNull(u.getId());
        repo.delete(u);
    }

    @Test
    void repoExists() {
        assertNotNull(repo);
    }

    @Test
    void sampleQueryWorks() {
        List<User> found = repo.findAll();
        assertEquals(10 + 0, found.size());
    }
}
