package com.iocl.dispatchapplication.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iocl.dispatchapplication.model.Unit;
@Repository
public interface UnitRepository extends JpaRepository<Unit, String> {

}

