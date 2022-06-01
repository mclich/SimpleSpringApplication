package com.github.mclich.ssa.repository;

import com.github.mclich.ssa.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long>{}
