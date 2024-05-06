package com.veerajk.demo.repo;

import com.veerajk.demo.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepo extends JpaRepository<Sprint,Long> {

}
