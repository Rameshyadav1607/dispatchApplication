package com.iocl.dispatchapplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iocl.dispatchapplication.model.RefSequence;

public interface RefSequenceRepository extends JpaRepository<RefSequence, String> {

	Optional<RefSequence> findByLocCode(String locCode);
}
