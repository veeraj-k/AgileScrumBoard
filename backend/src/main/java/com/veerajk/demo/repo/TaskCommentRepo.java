package com.veerajk.demo.repo;

import com.veerajk.demo.model.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCommentRepo extends JpaRepository<TaskComment,Long> {
    @Modifying
    @Query("delete from TaskComment t where t.id=:id")
    public void deleteById(Long id);
}
