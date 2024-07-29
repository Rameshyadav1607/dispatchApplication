package com.iocl.dispatchapplication.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iocl.dispatchapplication.model.CaptchaEntity;

@Repository
public interface CaptchaRepository extends JpaRepository<CaptchaEntity, UUID> {

	Optional<CaptchaEntity> findByValue(String captcha_value);

}
