package com.veerajk.demo.repo;

import com.veerajk.demo.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepo extends JpaRepository<Board,Long> {

}
