package com.auth.repository;

import com.auth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // TODO: Check SQL injection attack
    @Query(
            value = "SELECT * FROM user_details WHERE user_name = :userName",
            nativeQuery = true
    )
    User findByUsername(@Param("userName") String userName);

    @Query(
            value = "SELECT authority FROM user_role role JOIN user_authority authority ON " +
                    "role.authority_id = authority.id WHERE role.user_id = :userId",
            nativeQuery = true
    )
    List<String> getUserAuthorities(@Param("userId") int userId);
}
