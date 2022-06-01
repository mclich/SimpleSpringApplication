package com.github.mclich.ssa.repository;

import com.github.mclich.ssa.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>
{
    @Query("SELECT s FROM Student s WHERE s.firstName=?1 AND s.lastName=?2")
    Optional<Student> findByFullName(String firstName, String lastName);
}