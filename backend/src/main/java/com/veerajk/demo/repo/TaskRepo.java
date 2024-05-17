package com.veerajk.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veerajk.demo.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
//    public Task findById(long id);
//    public List<Task> findByColumn_id(long id);
}
