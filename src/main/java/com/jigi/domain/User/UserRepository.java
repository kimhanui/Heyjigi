package com.jigi.domain.User;

import com.jigi.domain.Category.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.oauthId = :oauthId")
    Optional<User> findByOauthId(@Param("oauthId") String oauthId);

    @Query("select u from User u order by  u.studentId asc ")
    List<User> findAllBystudentIdAsc();

    @Query("select u from User u Join u.categories c where c.categoryEnum = :categoryEnum")
    List<User> findByCategoryEnum(@Param("categoryEnum")CategoryEnum categoryEnum);

}
