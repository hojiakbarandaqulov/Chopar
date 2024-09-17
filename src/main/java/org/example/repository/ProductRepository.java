package org.example.repository;

import org.example.entity.ProductEntity;
import org.example.mapper.RegionMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    @Query(value = "select id," +
            " case   :language " +
            " when 'UZ' then name_uz " +
            " when 'RU' then name_ru " +
            " when 'EN' then name_en " +
            " end as name " +
            " from region order by order_number asc;",nativeQuery = true)
    List<RegionMapper> findAllByLanguage(@Param("language") String language);

    List<ProductEntity> findByCategoryId(Integer categoryId);
}
