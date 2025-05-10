package com.certifdoc.repository;

import com.certifdoc.entity.FormationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormationRepository extends JpaRepository<FormationEntity, Long> {
}