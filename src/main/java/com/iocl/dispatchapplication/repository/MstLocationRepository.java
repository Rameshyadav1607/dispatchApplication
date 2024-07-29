package com.iocl.dispatchapplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iocl.dispatchapplication.model.MstLocation;


@Repository
public interface MstLocationRepository extends JpaRepository<MstLocation, String> {

	Optional<MstLocation> findByLocCode(String locCode);
}