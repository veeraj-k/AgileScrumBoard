package com.veerajk.demo.repo;

import com.veerajk.demo.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepo extends JpaRepository<Sprint,Long> {
    @Modifying
    @Query("delete from Sprint s where s.id=:id")
    void deleteSprint(@Param("id") Long id);
}
