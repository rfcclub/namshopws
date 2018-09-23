package com.test.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.demo.data.LineItem;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Long> {

}
