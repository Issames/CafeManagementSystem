package com.inn.cafe.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inn.cafe.POJO.Bill;

public interface BillDao extends JpaRepository<Bill, Integer>{

}
