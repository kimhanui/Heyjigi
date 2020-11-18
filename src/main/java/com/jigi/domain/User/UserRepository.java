package com.jigi.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.studentId = :studentId")
    Optional<User> findByStudentId(@Param("studentId") Long studentId);

    @Query("select u from User u order by  u.studentId asc ")
    List<User> findAllBystudentIdAsc();
}
