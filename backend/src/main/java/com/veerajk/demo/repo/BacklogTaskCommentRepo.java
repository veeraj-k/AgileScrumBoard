package com.veerajk.demo.repo;

import com.veerajk.demo.model.BacklogTaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogTaskCommentRepo extends JpaRepository<BacklogTaskComment,Long> {
}
