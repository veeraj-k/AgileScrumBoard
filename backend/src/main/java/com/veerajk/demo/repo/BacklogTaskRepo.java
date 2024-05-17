package com.veerajk.demo.repo;

import com.veerajk.demo.model.BacklogTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogTaskRepo extends JpaRepository<BacklogTask,Long> {
}
