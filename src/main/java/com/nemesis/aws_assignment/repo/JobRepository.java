package com.nemesis.aws_assignment.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nemesis.aws_assignment.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
  
}