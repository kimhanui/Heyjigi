package com.jigi.domain.Post;

import com.jigi.domain.Category.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.categoryEnum = :category order by p.endDate DESC, p.id DESC ")
    List<Post> findAllByCategoryEnumDesc(@Param("category") CategoryEnum category);
}
