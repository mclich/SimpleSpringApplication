package com.github.mclich.ssa.repository;

import com.github.mclich.ssa.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>
{
    @Query("SELECT t FROM Teacher t WHERE t.firstName=?1 AND t.lastName=?2")
    Optional<Teacher> findByFullName(String firstName, String lastName);
}