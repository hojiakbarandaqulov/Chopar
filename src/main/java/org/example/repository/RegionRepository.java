package org.example.repository;

import org.example.entity.RegionEntity;
import org.example.enums.LanguageEnum;
import org.example.mapper.RegionMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegionRepository extends JpaRepository<RegionEntity, Integer> {
    @Query(value = "select id," +
            " case   :language " +
            " when 'UZ' then name_uz " +
            " when 'RU' then name_ru " +
            " when 'EN' then name_en " +
            " end as name " +
            " from region order by order_number asc;",nativeQuery = true)
    List<RegionMapper> findAllByLanguage(@Param("language") String language);
}
