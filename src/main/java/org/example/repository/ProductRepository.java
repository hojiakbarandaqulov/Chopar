package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.entity.ProductEntity;
import org.example.mapper.ProductMapper;
import org.example.mapper.RegionMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    @Query(value = "select id," +
            " case   :language " +
            " when 'UZ' then name_uz " +
            " when 'RU' then name_ru " +
            " end as name " +
            " from region order by order_number asc;", nativeQuery = true)
    List<ProductMapper> findAllByLanguage(@Param("language") String language);


    @Query(value = "select p from ProductEntity as p" +
            " where p.visible = true " +
            " order by p.id  desc  limit 5")
    List<ProductEntity> findByFiveProduct();

    @Query(value = "select p from ProductEntity as p" +
            " where p.viewCount > 0 ")
    List<ProductEntity> findByToSeeProduct();

    @Query(value = "select p from ProductEntity as p" +
            " where p.id=?1")
    ProductEntity findByIdProduct(Integer id);
}
