package com.veerajk.demo.repo;

import com.veerajk.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import com.veerajk.demo.model.Column;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ColumnRepo  extends JpaRepository<Column, Long>{
    public Column findByTitle(String title);
    public  Column findById(long id);

    public List<Column> findAllByOrderByLocationAsc();
//    public List<Column> fin

}
