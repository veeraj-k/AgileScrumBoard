package com.veerajk.demo.repo;

import com.veerajk.demo.model.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCommentRepo extends JpaRepository<TaskComment,Long> {
}
