package com.codesprint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.codesprint.model.Task;

/**
 * Repository interface for Task entity.
 * Provides CRUD operations and database access methods for Task objects.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // JpaRepository provides default methods:
    // - save(Task)
    // - findById(Long)
    // - findAll()
    // - delete(Task)
    // - deleteById(Long)
    // - count()
    // - exists(Long)
    
    // Add custom query methods here as needed, for example:
    // List<Task> findByStatus(String status);
    // List<Task> findByTitleContaining(String title);
    
}
