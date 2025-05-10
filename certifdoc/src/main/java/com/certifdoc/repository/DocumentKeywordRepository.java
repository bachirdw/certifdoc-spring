package com.certifdoc.repository;

import com.certifdoc.entity.DocumentKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentKeywordRepository extends JpaRepository<DocumentKeyword, Long> {
}