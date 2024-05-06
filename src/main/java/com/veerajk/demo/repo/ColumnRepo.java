package com.veerajk.demo.repo;

import com.veerajk.demo.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

import com.veerajk.demo.model.Column;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColumnRepo  extends JpaRepository<Column, Long>{
    public Column findByTitle(String title);
//    public  Column findById(long id);
    @Query("SELECT MAX(c.location) FROM Column c WHERE c.sprint.id = :boardid")
    public Integer findMaxLocation(Long boardid);
    public List<Column> findAllBySprintOrderByLocationDesc(Sprint sprint);
//    public List<Column> findAllBySprintOrderByLocationDesc(Sprint sprint);
}
