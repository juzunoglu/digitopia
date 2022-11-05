package com.example.demo.repo;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepo extends JpaRepository<User, String> {

    @Query(value = "SELECT * FROM users u where u.id = :userId AND u.status = 'ACTIVE'", nativeQuery = true)
    Optional<User> findNonDeletedUser(@Param("userId") String userId);

    Optional<User> findByEmail(String email);

    List<User> findByNormalizedNameContainsIgnoreCase(String normalizedName);


}
