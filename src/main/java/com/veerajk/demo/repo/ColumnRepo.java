package com.veerajk.demo.repo;

import com.veerajk.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import com.veerajk.demo.model.Column;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColumnRepo  extends JpaRepository<Column, Long>{
    public Column findByTitle(String title);
    public  Column findById(long id);
//    public Column findByBoard(long id);
//    public List<Column> findAllByBoardOrderByLocationAsc(long boardid);
//    public List<Column> fin
    @Query("SELECT MAX(c.location) FROM Column c WHERE c.board.id = :boardid")
    public Integer findMaxLocation(Long boardid);
}
