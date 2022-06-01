package com.github.mclich.ssa.repository;

import com.github.mclich.ssa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    @Query("SELECT u FROM User u WHERE u.login=?1")
    Optional<User> findByLogin(String login);
}
