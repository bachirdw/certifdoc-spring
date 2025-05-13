package com.certifdoc.repository;

import com.certifdoc.entity.DocumentEntity;
import com.certifdoc.entity.DocumentKeywordEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentKeywordRepository extends JpaRepository<DocumentKeywordEntity, Long> {

    //Récupérer tous les documents d’une catégorie
    List<DocumentEntity> findByCategorie_IdCategorie(Long idCategorie);

    //Récupérer tous les documents associés à un mot-clé.
    @Query("SELECT d FROM DocumentEntity d JOIN d.keywords k WHERE k.idKeyword = :idKeyword")
    List<DocumentEntity> findByKeywordId(@Param("idKeyword") Long idKeyword);

}