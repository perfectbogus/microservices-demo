package com.microservices.demo.analytics.service.dataaccess.entity.repository;

import com.microservices.demo.analytics.service.dataaccess.entity.impl.AnalyticsEntity;
import com.microservices.demo.analytics.service.dataaccess.entity.repository.AnalyticsCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public interface AnalyticsRepository extends JpaRepository<AnalyticsEntity, UUID>,
        AnalyticsCustomRepository<AnalyticsEntity, UUID> {
    @Query(value = "select e from AnalyticsEntity e where e.word=:word order by e.recordDate desc")
    List<AnalyticsEntity> getAnalyticsEntitiesByWord(@Param("word ") String word, Pageable pageable);
}
