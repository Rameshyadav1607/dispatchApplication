package com.iocl.dispatchapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iocl.dispatchapplication.model.MstCourier;

@Repository
public interface MstCourierRepository extends JpaRepository<MstCourier,String> {

}
