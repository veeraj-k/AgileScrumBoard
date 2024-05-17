package com.veerajk.demo.repo;

import com.veerajk.demo.model.Backlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepo extends JpaRepository<Backlog,Long> {
}
