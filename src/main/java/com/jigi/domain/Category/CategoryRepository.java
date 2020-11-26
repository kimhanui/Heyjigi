package com.jigi.domain.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.categoryEnum= :categoryEnum")
    Category findByCategoryEnum(@Param("categoryEnum") CategoryEnum categoryEnum);
}
